package com.vorobyev.first_web.command.impl;

import com.vorobyev.first_web.command.Command;
import com.vorobyev.first_web.entity.User;
import com.vorobyev.first_web.web.UserParameter;
import com.vorobyev.first_web.web.WebPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToProfileCommand implements Command {

    private static final String USERNAME = "username";


    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute(UserParameter.IS_LOGIN);
        String resultPage;
        if (isLogin) {
            resultPage = WebPage.SUCCESSFULLY_LOGIN;
        } else {
            resultPage = WebPage.LOGIN;
        }
        return resultPage;
    }
}
