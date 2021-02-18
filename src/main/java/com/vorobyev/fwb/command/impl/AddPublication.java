package com.vorobyev.fwb.command.impl;

import com.vorobyev.fwb.command.Command;
import com.vorobyev.fwb.controller.ErrorPageAttribute;
import com.vorobyev.fwb.controller.SessionAttributeName;
import com.vorobyev.fwb.controller.WebPagePath;
import com.vorobyev.fwb.entity.User;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.service.FileService;
import com.vorobyev.fwb.service.PublicationService;
import com.vorobyev.fwb.service.impl.FileServiceImpl;
import com.vorobyev.fwb.service.impl.PublicationServiceImpl;
import com.vorobyev.fwb.util.FileName;
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
import java.util.List;
import java.util.Optional;

public enum AddPublication implements Command {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private final PublicationService publicationService = PublicationServiceImpl.INSTANCE;
    private final FileService fileService = FileServiceImpl.INSTANCE;
    private final static ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
    private final static String TITLE = "title";
    private final static String CONTENT = "content";
    private final static String SUMMARY = "summary";
    private final static String PREVIEW_IMAGE = "image";

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
        User user = (User) session.getAttribute(SessionAttributeName.USER);
        Optional<String> optionalTitle = MultipartParser.getParameter(fields, TITLE);
        Optional<String> optionalContent = MultipartParser.getParameter(fields, CONTENT);
        Optional<String> optionalSummary = MultipartParser.getParameter(fields, SUMMARY);
        List<FileItem> fileItems = MultipartParser.takeFiles(fields);
        if (!isLogin) {
            logger.log(Level.INFO, "Not logged in");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return WebPagePath.ERROR;
        }
        if (optionalTitle.isEmpty() || optionalContent.isEmpty() || optionalSummary.isEmpty() || fileItems.size() != 1) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return WebPagePath.ERROR;
        }
        FileItem fileItem = fileItems.get(0);
        String imagePath = FileName.generate("", FileName.getExtension(fileItem.getName()));


        try {
            publicationService.addPublication(user, optionalTitle.get(), imagePath, optionalSummary.get(), optionalContent.get());
            fileService.writeFile(fileItems.get(0), imagePath);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            request.setAttribute(ErrorPageAttribute.EXCEPTION, exception);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return WebPagePath.ERROR;
        }

        return "";
    }
}
