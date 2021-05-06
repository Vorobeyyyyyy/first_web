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

public class ShowPublicationsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final PublicationService publicationService = PublicationServiceImpl.INSTANCE;
    private static final String PAGE_INDEX = "page_index";
    private static final String PAGE_COUNT = "page_count";
    private static final String CURRENT_PAGE = "current_page";
    private static final String PUBLISHER = "publisher";
    private static final String NEWS = "news";
    private static final String SEARCH = "search";
    private static final int PUBLICATION_PER_PAGE = 3;

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        String pageIndexString = request.getParameter(PAGE_INDEX);
        String publisher = request.getParameter(PUBLISHER);
        String searchText = request.getParameter(SEARCH);
        logger.log(Level.INFO, "Search text: {}", searchText);
        int pageIndex;
        try {
            pageIndex = Integer.parseInt(pageIndexString);
        } catch (NumberFormatException exception) {
            pageIndex = 0;
        }

        try {
            List<Publication> publications;
            int startIndex = pageIndex * PUBLICATION_PER_PAGE;
            if (publisher == null && searchText == null) {
                publications = publicationService.findAll(startIndex, PUBLICATION_PER_PAGE);
            } else {
                if (publisher != null) {
                    publications = publicationService.findPublicationsByPublisher(startIndex, PUBLICATION_PER_PAGE, publisher);
                    request.setAttribute(PUBLISHER, publisher);
                } else {
                    publications = publicationService.findPublicationsByText(startIndex, PUBLICATION_PER_PAGE, searchText);
                    request.setAttribute(SEARCH, searchText);
                }
            }
            long publicationCount = publicationService.findPublicationCount();
            long pageCount = (publicationCount / PUBLICATION_PER_PAGE) + (publicationCount % PUBLICATION_PER_PAGE == 0 ? 0 : 1);
            request.setAttribute(NEWS, publications);
            request.setAttribute(PAGE_COUNT, pageCount);
            request.setAttribute(CURRENT_PAGE, pageIndex);
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
