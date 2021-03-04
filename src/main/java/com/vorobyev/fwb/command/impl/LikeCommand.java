package com.vorobyev.fwb.command.impl;

import com.vorobyev.fwb.command.Command;
import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.entity.User;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.service.CommendService;
import com.vorobyev.fwb.service.impl.CommendServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public enum LikeCommand implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final String COMMEND_ID = "commend_id";
    private static final CommendService commendService = CommendServiceImpl.INSTANCE;

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        long commendId;
        String username = ((User) request.getSession().getAttribute(SessionAttributeName.USER)).getLogin();
        String publicationIdString = request.getParameter(COMMEND_ID);
        try {
            commendId = Long.parseLong(publicationIdString);
            try {
                commendService.likeCommend(commendId, username);
            } catch (ServiceException exception) {
                logger.log(Level.ERROR, exception.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            }
        } catch (NumberFormatException exception) {
            logger.log(Level.ERROR, "Cant parse commendId {}", publicationIdString);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
        return WebPagePath.EMPTY;
    }
}
