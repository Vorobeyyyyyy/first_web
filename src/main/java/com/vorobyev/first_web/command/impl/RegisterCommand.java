package com.vorobyev.first_web.command.impl;

import com.vorobyev.first_web.command.Command;
import com.vorobyev.first_web.command.CommandProvider;
import com.vorobyev.first_web.web.WebPage;
import com.vorobyev.first_web.entity.User;
import com.vorobyev.first_web.exception.ServiceException;
import com.vorobyev.first_web.service.impl.UserServiceImpl;
import com.vorobyev.first_web.web.UserParameter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "first_name";
    private static final String SECOND_NAME = "second_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String ERROR_MESSAGE = "error_message";
    private static final String ALREADY_LOGGED_IN = "Already logged in, logout first";

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String firstName = request.getParameter(FIRST_NAME);
        String secondName = request.getParameter(SECOND_NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute(UserParameter.IS_LOGIN);
        String resultPage;
        if (!isLogin) {
            try {
                User user = userService.register(login, password, firstName, secondName, phoneNumber);
                session.setAttribute(UserParameter.IS_LOGIN, true);
                session.setAttribute(UserParameter.USER, user);
                resultPage = CommandProvider.GO_TO_PROFILE.preform(request, response);
            } catch (ServiceException exception) {
                logger.log(Level.ERROR, exception.getMessage());
                request.setAttribute(ERROR_MESSAGE, exception.getMessage());
                resultPage = CommandProvider.GO_TO_PROFILE.preform(request, response);
            }
        } else {
            logger.log(Level.WARN, "Already logged in");
            resultPage = CommandProvider.GO_TO_PROFILE.preform(request, response);
        }
        return resultPage;
    }
}
