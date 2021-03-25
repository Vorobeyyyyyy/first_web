package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.controller.WebPagePathPrepared;
import com.vorobyev.fwb.model.entity.Publication;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.model.entity.UserAccessLevel;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.service.PublicationService;
import com.vorobyev.fwb.model.service.impl.PublicationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreatePublicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final PublicationService publicationService = PublicationServiceImpl.INSTANCE;
    private final static String TITLE = "title";
    private final static String CONTENT = "content";
    private final static String SUMMARY = "summary";
    private final static String PREVIEW_IMAGE = "image_path";

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        User user = (User) request.getSession().getAttribute(SessionAttributeName.USER);
        String title = request.getParameter(TITLE);
        String content = request.getParameter(CONTENT);
        String summary = request.getParameter(SUMMARY);
        String previewImage = request.getParameter(PREVIEW_IMAGE);

        try {
            Publication publication = publicationService.addPublication(user, title, previewImage, summary, content);
            path = request.getContextPath() + String.format(WebPagePathPrepared.PUBLICATION_WITH_ID, publication.getId());
            logger.log(Level.INFO, publication);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            path =  WebPagePath.ERROR;
        }

        return path;
    }

    @Override
    public List<UserAccessLevel> getAllowedAccessLevels() {
        return List.of(UserAccessLevel.PUBLISHER, UserAccessLevel.ADMIN);
    }
}
