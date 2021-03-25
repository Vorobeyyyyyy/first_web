package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAdminPanelCommand implements Command {
    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
