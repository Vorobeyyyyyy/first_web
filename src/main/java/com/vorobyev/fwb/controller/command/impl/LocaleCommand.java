package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LocaleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private final static String LANGUAGE = "language";
    private final static String LOCALE_ATTRIBUTE = "locale";
    private final static String PREV_PAGE = "prev_page";

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String locale = request.getParameter(LANGUAGE);
        String queryString = request.getQueryString();
        String prevPage = queryString.substring(queryString.lastIndexOf(PREV_PAGE) + PREV_PAGE.length());
        session.setAttribute(LOCALE_ATTRIBUTE, locale);
        return prevPage;
    }
}
