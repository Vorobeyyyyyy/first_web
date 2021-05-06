package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.controller.WebPagePath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoRegisterCommand implements Command {
    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        return WebPagePath.REGISTER;
    }
}
