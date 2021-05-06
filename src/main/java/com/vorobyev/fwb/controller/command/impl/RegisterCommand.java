package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.WebPagePathPrepared;
import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.entity.UserRole;
import com.vorobyev.fwb.model.service.impl.UserServiceImpl;
import com.vorobyev.fwb.controller.SessionAttributeName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "first_name";
    private static final String SECOND_NAME = "second_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String EMAIL = "email";
    private static final String ERROR_MESSAGE = "error_message";

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String firstName = request.getParameter(FIRST_NAME);
        String secondName = request.getParameter(SECOND_NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String email = request.getParameter(EMAIL);
        HttpSession session = request.getSession();
        String path;

        try {
            User user = userService.register(login, password, firstName, secondName, phoneNumber, email);
            session.setAttribute(SessionAttributeName.IS_LOGIN, true);
            session.setAttribute(SessionAttributeName.USER, user);
            path = WebPagePath.REDIRECT + String.format(WebPagePathPrepared.SHOW_PROFILE, user.getLogin());
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            path = WebPagePath.ERROR;
        }
        return path;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.GUEST);
    }
}
