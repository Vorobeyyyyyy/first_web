package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.controller.WebPagePathPrepared;
import com.vorobyev.fwb.model.entity.Publication;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.model.entity.UserRole;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.service.PublicationService;
import com.vorobyev.fwb.model.service.impl.PublicationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UpdatePublicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final PublicationService publicationService = PublicationServiceImpl.INSTANCE;
    private final static String TITLE = "title";
    private final static String CONTENT = "content";
    private final static String SUMMARY = "summary";
    private final static String PREVIEW_IMAGE = "image_path";
    private final static String PUBLICATION_ID = "publication_id";

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        User user = (User) request.getSession().getAttribute(SessionAttributeName.USER);
        String title = request.getParameter(TITLE);
        String content = request.getParameter(CONTENT);
        String summary = request.getParameter(SUMMARY);
        String previewImage = request.getParameter(PREVIEW_IMAGE);
        Long publicationId;
        try {
            publicationId = Long.parseLong(request.getParameter(PUBLICATION_ID));
        } catch (Exception e) {
            publicationId = null;
        }

        try {
            Publication publication;
            if (publicationId == null) {
                publication = publicationService.createPublication(user, title, previewImage, summary, content);
            } else {
                publication = publicationService.updatePublication(publicationId, user, title, previewImage, summary, content);
            }
            path = WebPagePath.REDIRECT + String.format(WebPagePathPrepared.PUBLICATION_WITH_ID, publication.getId());
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
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.PUBLISHER, UserRole.ADMIN);
    }
}
