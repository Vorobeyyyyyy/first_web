package com.vorobyev.fwb.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommendValidator {
    private final static String COMMEND_REGEX = ".{10,200}";

    public static boolean isBodyValid(String commendBody) {
        Pattern pattern = Pattern.compile(COMMEND_REGEX);
        Matcher matcher = pattern.matcher(commendBody);
        return matcher.matches();
    }
}
