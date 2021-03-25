package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.model.entity.Publication;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.service.PublicationService;
import com.vorobyev.fwb.model.service.impl.PublicationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class GoEditPublicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String PUBLICATION_ID = "publication_id";
    private static final String PUBLICATION = "publication";
    private final PublicationService publicationService = PublicationServiceImpl.INSTANCE;

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        long publicationId = Long.parseLong(request.getParameter(PUBLICATION_ID));
        try {
            Optional<Publication> optionalPublication = publicationService.findPublicationById(publicationId);
            if (optionalPublication.isPresent()) {
                Publication publication = optionalPublication.get();
                request.setAttribute(PUBLICATION, publication);
                path = WebPagePath.EDIT_PUBLICATION;
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                path = WebPagePath.ERROR;
            }
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            path = WebPagePath.ERROR;
        }
        return path;
    }
}
