package com.vorobyev.fwb.controller.command;

import com.vorobyev.fwb.model.entity.UserAccessLevel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface Command {
    String preform(HttpServletRequest request, HttpServletResponse response);

    default List<UserAccessLevel> getAllowedAccessLevels() {
        return List.of(UserAccessLevel.USER, UserAccessLevel.GUEST);
    }
}
