package com.vorobyev.fwb.model.pool;

import com.vorobyev.fwb.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    //Pool size range
    private static final int MAX_POOL_SIZE = 32;
    private static final int MIN_POOL_SIZE = 8;
    //Initializer parameters
    private static final int MAX_RETRY_COUNT = 5;
    //Auto-cleaner parameters
    private static final int CLEAR_CONNECTIONS_REQUESTS_COUNT = 100;
    private static final int CLEAR_PERIOD_IN_SECONDS = 60;
    private static final int CLEAR_DELIMITER = 2;
    //Singleton
    private static final ConnectionPool INSTANCE = new ConnectionPool();

    private final BlockingQueue<ProxyConnection> freeConnections = new LinkedBlockingQueue<>(MAX_POOL_SIZE);
    private final Queue<ProxyConnection> givenConnections = new ArrayDeque<>(MAX_POOL_SIZE);
    private final AtomicInteger givenConnectionPerPeriod = new AtomicInteger(0);
    private final AtomicInteger totalConnectionsCount = new AtomicInteger(0);

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    private ConnectionPool() {
        int retryCounter = 0;
        int initializedCounter = 0;
        while (initializedCounter != MIN_POOL_SIZE) {
            try {
                ProxyConnection proxyConnection = new ProxyConnection(ConnectionCreator.createConnection());
                freeConnections.add(proxyConnection);
                totalConnectionsCount.incrementAndGet();
                initializedCounter++;
            } catch (SQLException exception) {
                retryCounter++;
            }
            if (retryCounter >= MAX_RETRY_COUNT) {
                logger.log(Level.FATAL, "Cant create {} connections after {} attempts", MIN_POOL_SIZE, retryCounter);
                throw new RuntimeException("Cant initialize connection pool");
            }
        }
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(ConnectionPoolCleaner.INSTANCE, 0, CLEAR_PERIOD_IN_SECONDS, TimeUnit.SECONDS);
    }

    public Connection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        if (!freeConnections.isEmpty() || totalConnectionsCount.get() >= MAX_POOL_SIZE) {
            try {
                connection = freeConnections.take();
                givenConnections.offer(connection);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new ConnectionPoolException(exception);
            }
        } else {
            try {
                ProxyConnection newProxyConnection = new ProxyConnection(ConnectionCreator.createConnection());
                givenConnections.offer(newProxyConnection);
                totalConnectionsCount.incrementAndGet();
                connection = newProxyConnection;
                logger.log(Level.INFO, "Connection pool extended, current size: {}", totalConnectionsCount.get());
            } catch (SQLException exception) {
                throw new ConnectionPoolException(exception);
            }
        }
        logger.log(Level.INFO, "Connection has been given");
        givenConnectionPerPeriod.incrementAndGet();
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection && givenConnections.remove(connection)) {
            freeConnections.offer((ProxyConnection) connection);
            logger.log(Level.INFO, "Connections has been released");
        } else {
            logger.log(Level.ERROR, "Cant release connection");
        }
    }

    void removeUnnecessaryConnections() {
        int removedConnections = 0;
        if (givenConnectionPerPeriod.get() < CLEAR_CONNECTIONS_REQUESTS_COUNT) {
            int connectionsToRemoveCount = (totalConnectionsCount.get() - MIN_POOL_SIZE) / CLEAR_DELIMITER;
            while (connectionsToRemoveCount-- != 0 && !freeConnections.isEmpty()) {
                try {
                    ProxyConnection proxyConnection = freeConnections.take();
                    proxyConnection.reallyClose();
                    totalConnectionsCount.decrementAndGet();
                    removedConnections++;
                } catch (InterruptedException | SQLException exception) {
                    logger.log(Level.ERROR, exception.getMessage());
                }
            }
        }
        givenConnectionPerPeriod.set(0);
        logger.log(Level.INFO, "Removed {} connections", removedConnections);
    }
}
