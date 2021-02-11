package com.vorobyev.fwb.command.impl;

import com.vorobyev.fwb.command.Command;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.entity.Publication;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.service.PublicationService;
import com.vorobyev.fwb.service.impl.PublicationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class GoToPublicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final PublicationService publicationService = PublicationServiceImpl.INSTANCE;

    private static final String PUBLICATION_ID = "publication_id";

    private static final String PUBLICATION = "publication";

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        long publicationId;
        String path;
        try {
            publicationId = Long.parseLong(request.getParameter(PUBLICATION_ID));
            try {
                Optional<Publication> optionalPublication = publicationService.findPublicationById(publicationId);
                if (optionalPublication.isPresent()) {  //fixme orElse
                    request.setAttribute(PUBLICATION, optionalPublication.get());
                    path = WebPagePath.PUBLICATION;
                } else {
                    path = WebPagePath.ERROR404;
                }
            } catch (ServiceException exception) {
                logger.log(Level.ERROR, exception.getMessage());
                path = WebPagePath.ERROR404;
            }
        } catch (NumberFormatException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            path = WebPagePath.ERROR404;
        }
        return path;
    }
}
