package com.vorobyev.fwb.controller.command;

import com.vorobyev.fwb.model.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface Command {
    String preform(HttpServletRequest request, HttpServletResponse response);

    default List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.values());
    }
}
