package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.entity.Commend;
import com.vorobyev.fwb.model.entity.UserRole;
import com.vorobyev.fwb.model.service.CommendService;
import com.vorobyev.fwb.model.service.impl.CommendServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAdminPanelCommendsCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String COMMENDS = "commends";
    private static final int COMMENDS_PER_PAGE = 50;
    private static final CommendService COMMENDS_SERVICE = CommendServiceImpl.INSTANCE;

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        try {
            List<Commend> commends = COMMENDS_SERVICE.findAll(0, COMMENDS_PER_PAGE);
            request.setAttribute(COMMENDS, commends);
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
