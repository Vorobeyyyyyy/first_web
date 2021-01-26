package com.vorobyev.first_web.command.impl;

import com.vorobyev.first_web.command.Command;
import com.vorobyev.first_web.web.UserParameter;
import com.vorobyev.first_web.web.WebPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute(UserParameter.IS_LOGIN, false);
        session.setAttribute(UserParameter.USER, null);
        return WebPage.LOGIN;
    }
}
