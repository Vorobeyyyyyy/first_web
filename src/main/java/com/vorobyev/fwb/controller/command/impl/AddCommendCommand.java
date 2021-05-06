package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.controller.WebPagePathPrepared;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.service.CommendService;
import com.vorobyev.fwb.model.service.impl.CommendServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCommendCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String PUBLICATION_ID = "publicationId";
    private static final String BODY = "body";
    private static final CommendService commendService = CommendServiceImpl.INSTANCE;

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        String body = request.getParameter(BODY);
        String publicationId = request.getParameter(PUBLICATION_ID);
        String nickname = ((User)request.getSession().getAttribute(SessionAttributeName.USER)).getLogin();
        try {
            commendService.addCommend(body, nickname, Long.parseLong(publicationId));
            path = WebPagePath.REDIRECT + String.format(WebPagePathPrepared.PUBLICATION_WITH_ID, publicationId);
        } catch (ServiceException | NumberFormatException exception) {
            path = WebPagePath.ERROR;
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.log(Level.ERROR, exception);
        }
        return path;
    }
}
