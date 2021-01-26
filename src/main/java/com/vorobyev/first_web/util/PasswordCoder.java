package com.vorobyev.first_web.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCoder {
    private final static Logger logger = LogManager.getLogger();

    public static String code(String password) {
        String encodedPassword;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] encoded = messageDigest.digest(password.getBytes());
            encodedPassword = new String(encoded);
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.ERROR, e.getMessage());
            encodedPassword = "";
        }
        return encodedPassword;
    }
}
