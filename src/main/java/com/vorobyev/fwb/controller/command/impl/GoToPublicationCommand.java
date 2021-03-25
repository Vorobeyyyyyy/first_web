package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.model.entity.Commend;
import com.vorobyev.fwb.model.entity.Publication;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.service.CommendService;
import com.vorobyev.fwb.model.service.PublicationService;
import com.vorobyev.fwb.model.service.impl.CommendServiceImpl;
import com.vorobyev.fwb.model.service.impl.PublicationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class GoToPublicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final PublicationService publicationService = PublicationServiceImpl.INSTANCE;
    private static final CommendService commendService = CommendServiceImpl.INSTANCE;
    private static final String PUBLICATION_ID = "publication_id";
    private static final String PUBLICATION = "publication";
    private static final String COMMENDS = "commends";
    private static final String EMPTY = "";

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        long publicationId;
        String path;
        String username;
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute(SessionAttributeName.IS_LOGIN);
        if (isLogin) {
            username = ((User) session.getAttribute(SessionAttributeName.USER)).getLogin();
        } else {
            username = EMPTY;
        }

        try {
            publicationId = Long.parseLong(request.getParameter(PUBLICATION_ID));
            try {
                Optional<Publication> optionalPublication = publicationService.findPublicationById(publicationId);
                if (optionalPublication.isPresent()) {
                    List<Commend> commends = commendService.findByPublicationId(publicationId, username);
                    request.setAttribute(PUBLICATION, optionalPublication.get());
                    request.setAttribute(COMMENDS, commends);
                    path = WebPagePath.PUBLICATION;
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
        } catch (NumberFormatException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            path = WebPagePath.ERROR;
        }
        return path;
    }
}
