package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.service.UserService;
import com.vorobyev.fwb.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public enum ChangeAvatarImageCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final String AVATAR_PATH = "avatar_path";
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        String newPath = request.getParameter(AVATAR_PATH);
        User user = (User) request.getSession().getAttribute(SessionAttributeName.USER);
        try {
            userService.changeAvatar(user.getLogin(), newPath);
            user.setAvatarPath(newPath);
            path = WebPagePath.PROFILE;
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            path = WebPagePath.ERROR;
        }
        return path;
    }
}
