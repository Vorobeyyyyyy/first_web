package com.vorobyev.fwb.command.impl;

import com.vorobyev.fwb.command.Command;
import com.vorobyev.fwb.controller.WebPagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public enum GoCreatePublicationCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final String USER = "requestedUser";

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        return WebPagePath.CREATE_PUBLICATION;
    }
}
