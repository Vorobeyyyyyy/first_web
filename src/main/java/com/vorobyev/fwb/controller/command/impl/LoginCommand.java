package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.WebPagePathPrepared;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.entity.UserRole;
import com.vorobyev.fwb.model.service.impl.UserServiceImpl;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ERROR_MESSAGE = "error_message";
    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute(SessionAttributeName.IS_LOGIN);
        String resultPage;
        if (!isLogin) {
            try {
                User user = userService.login(login, password);
                session.setAttribute(SessionAttributeName.IS_LOGIN, true);
                session.setAttribute(SessionAttributeName.USER, user);
                resultPage = WebPagePath.REDIRECT + String.format(WebPagePathPrepared.SHOW_PROFILE, login);
            } catch (ServiceException exception) {
                logger.log(Level.ERROR, exception.getMessage());
                request.setAttribute(ERROR_MESSAGE, exception.getMessage());
                resultPage = WebPagePath.LOGIN;
            }
        } else {
            logger.log(Level.WARN, "Already logged in");
            resultPage = WebPagePath.PROFILE;
        }
        return resultPage;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.GUEST);
    }
}