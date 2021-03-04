package com.vorobyev.fwb.command.impl;

import com.vorobyev.fwb.command.Command;
import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.entity.User;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.service.FileService;
import com.vorobyev.fwb.service.UserService;
import com.vorobyev.fwb.service.impl.FileServiceImpl;
import com.vorobyev.fwb.service.impl.UserServiceImpl;
import com.vorobyev.fwb.util.MultipartParser;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Optional;

public enum ChangeAvatarImage implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final String AVATAR_PATH = "avatar_path";
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        String path;
        String newPath = request.getParameter(AVATAR_PATH);
        User user = (User) request.getSession().getAttribute(SessionAttributeName.USER);
        try {
            userService.changeAvatar(user.getLogin(), newPath);
            user.setAvatarPath(newPath);
            path = WebPagePath.PROFILE;
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            path = WebPagePath.ERROR;
        }
        return path;
    }
}
