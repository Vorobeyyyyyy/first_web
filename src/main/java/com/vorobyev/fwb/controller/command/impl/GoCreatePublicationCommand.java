package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.model.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GoCreatePublicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String USER = "requestedUser";

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        return WebPagePath.CREATE_PUBLICATION;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.PUBLISHER, UserRole.ADMIN);
    }
}
