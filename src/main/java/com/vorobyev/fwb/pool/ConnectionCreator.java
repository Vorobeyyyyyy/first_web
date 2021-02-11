package com.vorobyev.fwb.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionCreator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_PATH = "config/database.properties";
    private static final String DRIVER_PROPERTY = "db.driver";
    private static final String URL_PROPERTY = "db.url";


    static {
        try {
            PROPERTIES.load(ConnectionCreator.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH));
            String driver = PROPERTIES.getProperty(DRIVER_PROPERTY);
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException exception) {
            LOGGER.log(Level.FATAL, "Error during connection creation", exception);
            throw new RuntimeException(exception);
        }
    }

    private ConnectionCreator() {
    }

    static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(PROPERTIES.getProperty(URL_PROPERTY), PROPERTIES);
    }
}