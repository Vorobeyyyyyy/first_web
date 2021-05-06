package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.controller.WebPagePathPrepared;
import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.entity.UserRole;
import com.vorobyev.fwb.model.service.CommendService;
import com.vorobyev.fwb.model.service.impl.CommendServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RemoveCommendCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final static String ID = "id";
    private final static CommendService commendService = CommendServiceImpl.INSTANCE;

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        Long id = Long.parseLong(request.getParameter(ID));
        try {
            commendService.removeById(id);
            path = WebPagePath.REDIRECT + WebPagePathPrepared.ADMIN_COMMENDS;
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
