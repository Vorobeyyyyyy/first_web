package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.entity.Publication;
import com.vorobyev.fwb.model.entity.UserRole;
import com.vorobyev.fwb.model.service.PublicationService;
import com.vorobyev.fwb.model.service.impl.PublicationServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAdminPanelPublicationsCommand implements Command {
    private static final String PUBLICATIONS = "publications";
    private static final int PUBLICATION_PER_PAGE = 50;
    private static final PublicationService PUBLICATION_SERVICE = PublicationServiceImpl.INSTANCE;

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        try {
            List<Publication> publications = PUBLICATION_SERVICE.findAll(0, PUBLICATION_PER_PAGE);
            request.setAttribute(PUBLICATIONS, publications);
            path = WebPagePath.ADMIN_PANEL;
        } catch (ServiceException exception) {
            path = WebPagePath.ERROR;
        }
        return path;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.ADMIN);
    }
}
