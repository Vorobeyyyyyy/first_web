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
    private final FileService fileService = FileServiceImpl.INSTANCE;
    private final UserService userService = UserServiceImpl.getInstance();
    private final static int CORRECT_FILES_COUNT = 1;
    private final static String USERNAME = "username";
    private final static String AVATAR_FOLDER_PATH = "avatars";
    private final static char EXTENSION_DELIMITER = '.';
    private final static ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());

    @Override
    public String preform(HttpServletRequest request, HttpServletResponse response) {
        List<FileItem> fields;
        try {
            fields = servletFileUpload.parseRequest(request);
        } catch (FileUploadException exception) {
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return WebPagePath.ERROR;
        }

        HttpSession session = request.getSession();
        boolean isLogin = (boolean) session.getAttribute(SessionAttributeName.IS_LOGIN);
        Optional<String> optionalUsername = MultipartParser.getParameter(fields, USERNAME);
        String username;

        if (!isLogin) {
            logger.log(Level.INFO, "Not logged in");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return WebPagePath.ERROR;
        }

        if (optionalUsername.isPresent()) {
            username = optionalUsername.get();
        } else {
            logger.log(Level.INFO, "Username not defined");
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return WebPagePath.ERROR;
        }

        User user = (User) session.getAttribute(SessionAttributeName.USER);
        if (!userService.haveRightsToChangeProfile(user, username)) {
            logger.log(Level.INFO, "Not enough rights");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return WebPagePath.ERROR;
        }

        try {
            List<FileItem> fileItems = MultipartParser.takeFiles(fields);
            if (fileItems.size() != CORRECT_FILES_COUNT) {
                logger.log(Level.WARN, "Wrong files count {}", fileItems.size());
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                return WebPagePath.ERROR;
            }
            FileItem newAvatar = fileItems.get(0);
            String newAvatarFileName = username + newAvatar.getName().substring(newAvatar.getName().lastIndexOf(EXTENSION_DELIMITER));
            String newAvatarPath = AVATAR_FOLDER_PATH + File.separator + newAvatarFileName;
            fileService.writeFile(newAvatar, newAvatarPath);
            userService.changeAvatar(user, username, newAvatarPath);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
        }
        return WebPagePath.PROFILE;
    }
}
