package com.vorobyev.first_web.util;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PasswordCoderTest {

    @Test
    public void testCode() {
        String password = "7";
        System.out.println(PasswordCoder.code(password).length());
    }
}