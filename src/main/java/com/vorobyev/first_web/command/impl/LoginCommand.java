package com.vorobyev.first_web.command.impl;

import com.vorobyev.first_web.command.Command;
import com.vorobyev.first_web.command.CommandProvider;
import com.vorobyev.first_web.entity.User;
import com.vorobyev.first_web.exception.ServiceException;
import com.vorobyev.first_web.service.impl.UserServiceImpl;
import com.vorobyev.first_web.web.UserParameter;
import com.vorobyev.first_web.web.WebPage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ERROR_MESSAGE = "error_message";

    private static final String ALREADY_LOGGED_IN = "Already logged in, logout first";

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute(UserParameter.IS_LOGIN);
        String resultPage;
        if (!isLogin) {
            try {
                User user = userService.login(login, password);
                session.setAttribute(UserParameter.IS_LOGIN, true);
                session.setAttribute(UserParameter.USER, user);
                resultPage = CommandProvider.GO_TO_PROFILE.preform(request, response);
            } catch (ServiceException exception) {
                logger.log(Level.ERROR, exception.getMessage());
                request.setAttribute(ERROR_MESSAGE, exception.getMessage());
                resultPage = WebPage.LOGIN;
            }
        } else {
            logger.log(Level.WARN, "Already logged in");
            resultPage = CommandProvider.GO_TO_PROFILE.preform(request, response);
        }

        int a = 5;
        switch (a) {
            case 3, 4, 5 -> {

            }
        }

        return resultPage;
    }
}