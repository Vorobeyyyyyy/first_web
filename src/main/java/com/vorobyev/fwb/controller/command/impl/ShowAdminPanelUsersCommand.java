package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.model.entity.UserRole;
import com.vorobyev.fwb.model.service.UserService;
import com.vorobyev.fwb.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAdminPanelUsersCommand implements Command {
    private static final String USERS = "users";
    private static final int USERS_PER_PAGE = 50;
    private static final UserService USER_SERVICE = UserServiceImpl.getInstance();

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        try {
            List<User> users = USER_SERVICE.findAll(0, USERS_PER_PAGE);
            request.setAttribute(USERS, users);
            path = WebPagePath.ADMIN_PANEL;
        } catch (ServiceException exception) {
            path = WebPagePath.ERROR;
        }
        return path;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.ADMIN);
    }
}
