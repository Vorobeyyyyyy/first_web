package com.vorobyev.fwb.controller.command;

import com.vorobyev.fwb.model.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface Command {
    /**
     *
     * @param request request a Processed Request
     * @param response request a Processed Request
     * @return path to forward or redirect
     */

    String perform(HttpServletRequest request, HttpServletResponse response);

    default List<UserRole> getAllowedAccessLevels() {
        return List.of(UserRole.values());
    }
}
