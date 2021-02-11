package com.vorobyev.fwb.pool;

import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;

public class ConnectionCreatorTest {
    @Test
    public void createConnectionTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, Calendar.MARCH, 9, 15, 43);
        System.out.println( calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("ru")));
    }
}