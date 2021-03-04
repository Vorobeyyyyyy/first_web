package com.vorobyev.fwb.command.impl;

import com.vorobyev.fwb.command.Command;
import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.entity.Commend;
import com.vorobyev.fwb.entity.User;
import com.vorobyev.fwb.entity.UserAccessLevel;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.service.CommendService;
import com.vorobyev.fwb.service.UserService;
import com.vorobyev.fwb.service.impl.CommendServiceImpl;
import com.vorobyev.fwb.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public enum ShowProfileCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final String USERNAME = "username";
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final CommendService commendService = CommendServiceImpl.INSTANCE;
    private static final String USER = "requestedUser";
    private static final String COMMENDS = "commends";
    private static final String IS_PUBLISHER = "isPublisher";
    private static final String CAN_EDIT = "canEdit";

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter(USERNAME);
        HttpSession session = request.getSession();
        Boolean canEdit = (Boolean) session.getAttribute(SessionAttributeName.IS_LOGIN) && ((User) session.getAttribute(SessionAttributeName.USER)).getLogin().equals(username);
        String path;
        User user;
        try {
            user = userService.userByLogin(username);
            Boolean isPublisher = List.of(UserAccessLevel.PUBLISHER, UserAccessLevel.ADMIN).contains(user.getLevel());
            List<Commend> commends = commendService.findByUsername(username);
            request.setAttribute(USER, user);
            request.setAttribute(COMMENDS, commends);
            request.setAttribute(CAN_EDIT, canEdit);
            request.setAttribute(IS_PUBLISHER, isPublisher);
            path = WebPagePath.PROFILE;
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            path = WebPagePath.ERROR;
        }
        return path;
    }
}
