package com.vorobyev.fwb.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String preform(HttpServletRequest request, HttpServletResponse response);
}
