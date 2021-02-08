package com.vorobyev.fwb.command.impl;

import com.vorobyev.fwb.command.Command;
import com.vorobyev.fwb.controller.WebPagePath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToRegisterCommand implements Command {
    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        return WebPagePath.REGISTER;
    }
}
