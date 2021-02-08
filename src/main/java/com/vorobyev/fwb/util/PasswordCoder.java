package com.vorobyev.fwb.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class PasswordCoder {
    private final static Logger logger = LogManager.getLogger();

    private final static String ALGORITHM = "SHA-1";

    private PasswordCoder(){}

    public static Optional<String> code(String password) {
        Optional<String> optionalEncodedPassword;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            byte[] encoded = messageDigest.digest(password.getBytes());
            optionalEncodedPassword = Optional.of(new String(encoded));
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.ERROR, e.getMessage());
            optionalEncodedPassword = Optional.empty();
        }
        return optionalEncodedPassword;
    }
}
