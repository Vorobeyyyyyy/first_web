package com.vorobyev.first_web.command.impl;

import com.vorobyev.first_web.command.Command;
import com.vorobyev.first_web.web.WebPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToRegisterCommand implements Command {
    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        return WebPage.REGISTER;
    }
}
