package com.vorobyev.fwb.pool;

import com.vorobyev.fwb.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();

    private static final int MAX_POOL_SIZE = 32;
    private static final int MIN_POOL_SIZE = 8;

    private static final int MAX_RETRY_COUNT = 5;

    private static final int CLEAR_CONNECTIONS_REQUESTS_COUNT = 100;

    private static final int CLEAR_PERIOD_IN_SECONDS = 60;

    private static final ConnectionPool INSTANCE = new ConnectionPool();

    private final BlockingQueue<ProxyConnection> freeConnections = new LinkedBlockingQueue<>(MAX_POOL_SIZE);
    private final Queue<ProxyConnection> givenConnections = new ArrayDeque<>(MAX_POOL_SIZE);

    private int givenConnectionPerPeriod = 0;

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

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
                initializedCounter++;
            } catch (SQLException exception) {
                retryCounter++;
            }

            if (retryCounter >= MAX_RETRY_COUNT) {
                logger.log(Level.FATAL, "Cant create {} connections after {} attempts", MIN_POOL_SIZE, retryCounter);
                throw new RuntimeException("Cant initialize connection pool");
            }
        }
        executorService.scheduleAtFixedRate(ConnectionPoolCleaner.INSTANCE, 0, CLEAR_PERIOD_IN_SECONDS, TimeUnit.SECONDS);
    }

    public Connection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        if (freeConnections.isEmpty() && freeConnections.size() + givenConnections.size() < MAX_POOL_SIZE) { //todo: mb synchronized?
            try {
                ProxyConnection newProxyConnection = new ProxyConnection(ConnectionCreator.createConnection());
                givenConnections.offer(newProxyConnection);
                connection = newProxyConnection;
                logger.log(Level.INFO, "Connection pool extended, current size: {}", findTotalConnectionsCount());
            } catch (SQLException exception) {
                throw new ConnectionPoolException(exception);
            }
        } else {
            try {
                connection = freeConnections.take();
                givenConnections.offer(connection);
            } catch (InterruptedException exception) {
                throw new ConnectionPoolException(exception); //todo change to thread.interrupt
            }
        }
        logger.log(Level.INFO, "Connection has been given");
        givenConnectionPerPeriod++;
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
        if (givenConnectionPerPeriod < CLEAR_CONNECTIONS_REQUESTS_COUNT) {
            int connectionsToRemoveCount = (findTotalConnectionsCount() - MIN_POOL_SIZE) / 2;
            while (connectionsToRemoveCount-- != 0 && !freeConnections.isEmpty()) {
                try {
                    ProxyConnection proxyConnection = freeConnections.take();
                    proxyConnection.reallyClose();
                    removedConnections++;
                } catch (InterruptedException | SQLException exception) {
                    logger.log(Level.ERROR, exception.getMessage());
                }
            }
        }
        givenConnectionPerPeriod = 0;
        logger.log(Level.INFO, "Removed {} connections", removedConnections);
    }

    private int findTotalConnectionsCount() {
        return freeConnections.size() + givenConnections.size();
    }
}
