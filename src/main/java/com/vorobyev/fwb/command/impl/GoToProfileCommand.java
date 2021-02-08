package com.vorobyev.fwb.command.impl;

import com.vorobyev.fwb.command.Command;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToProfileCommand implements Command {
    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute(SessionAttributeName.IS_LOGIN);
        String resultPage;
        if (isLogin) {
            resultPage = WebPagePath.PROFILE;
        } else {
            resultPage = WebPagePath.LOGIN;
        }
        return resultPage;
    }
}
