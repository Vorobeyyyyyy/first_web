package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.controller.WebPagePathPrepared;
import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.entity.UserRole;
import com.vorobyev.fwb.model.service.PublicationService;
import com.vorobyev.fwb.model.service.impl.PublicationServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RemovePublicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final static String ID = "id";
    private final static PublicationService publicationService = PublicationServiceImpl.INSTANCE;

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        Long id = Long.parseLong(request.getParameter(ID));
        try {
            publicationService.removeById(id);
            path = WebPagePath.REDIRECT + WebPagePathPrepared.ADMIN_PUBLICATIONS;
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            path = WebPagePath.ERROR;
        }
        return path;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.ADMIN);
    }
}
