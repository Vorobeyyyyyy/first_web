package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.WebPagePathPrepared;
import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.model.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LogoutCommand implements Command {
    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionAttributeName.IS_LOGIN, false);
        session.setAttribute(SessionAttributeName.USER, null);
        session.invalidate();
        return WebPagePath.REDIRECT + WebPagePathPrepared.MAIN;
    }

    @Override
    public List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.USER, UserRole.PUBLISHER, UserRole.ADMIN);
    }
}
