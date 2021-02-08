package com.vorobyev.fwb.util;

import org.testng.annotations.Test;

public class PasswordCoderTest {

    @Test
    public void testCode() {
        String password = "7";
        System.out.println(PasswordCoder.code(password).get().length());
    }
}