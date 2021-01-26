package com.vorobyev.first_web.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionCreator {
    private static final Logger logger = LogManager.getLogger();
    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_PATH = "C:\\Users\\Alexander\\IdeaProjects\\first_web\\src\\main\\resources\\config.properties";
    private static final String DRIVER_PROPERTY = "db.driver";
    private static final String URL_PROPERTY = "db.url";


    static {
        try {
            PROPERTIES.load(new FileReader(PROPERTIES_PATH));
            String driver = PROPERTIES.getProperty(DRIVER_PROPERTY);
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.FATAL, "Error during connection creation", e);
        }
    }

    private ConnectionCreator() {
    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(PROPERTIES.getProperty(URL_PROPERTY), PROPERTIES);
    }
}