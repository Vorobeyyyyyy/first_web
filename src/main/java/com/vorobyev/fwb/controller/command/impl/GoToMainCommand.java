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
import java.util.List;

@Deprecated(forRemoval = true) //Replaced with ShowPublicationsCommand
public class GoToMainCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final PublicationService publicationService = PublicationServiceImpl.INSTANCE;
    private static final String PAGE_INDEX = "page_index";
    private static final String NEWS = "news";
    private static final int PUBLICATION_PER_PAGE = 10;

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        String pageIndexString = request.getParameter(PAGE_INDEX);
        int pageIndex;
        try {
            pageIndex = Integer.parseInt(pageIndexString);
        } catch (NumberFormatException exception) {
            pageIndex = 0;
        }
        try {
            List<Publication> publications = publicationService.findPublications(pageIndex * PUBLICATION_PER_PAGE, PUBLICATION_PER_PAGE);
            request.setAttribute(NEWS, publications);
            path = WebPagePath.MAIN;
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            path = WebPagePath.ERROR;
        }
        return path;
    }
}
