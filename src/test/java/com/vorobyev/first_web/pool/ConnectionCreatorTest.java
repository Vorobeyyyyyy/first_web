package com.vorobyev.first_web.pool;

import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.testng.Assert.*;

public class ConnectionCreatorTest {
    @Test
    public void createConnectionTest() {
        try {
            Connection connection = ConnectionCreator.createConnection();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}