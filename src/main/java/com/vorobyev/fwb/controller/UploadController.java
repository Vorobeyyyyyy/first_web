package com.vorobyev.fwb.controller;

import com.vorobyev.fwb.entity.User;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.service.FileService;
import com.vorobyev.fwb.service.impl.FileServiceImpl;
import com.vorobyev.fwb.util.FileName;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "uploadServlet", urlPatterns = {"*.up"})
public class UploadController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private final static ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
    private final static FileService fileService = FileServiceImpl.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "New UPLOAD POST (Url = {})", request.getRequestURL() + "?" + request.getQueryString());
        boolean isLogin = (boolean) request.getSession().getAttribute(SessionAttributeName.IS_LOGIN);
        if (!isLogin) {
            response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            return;
        }

        User user = (User) request.getSession().getAttribute(SessionAttributeName.USER);
        try (PrintWriter responseWriter = response.getWriter()) {
            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            if (fileItems.size() != 1 || fileItems.get(0).isFormField()) {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                return;
            }

            FileItem fileItem = fileItems.get(0);
            String filePath = FileName.generate(user.getLogin() + File.separator, FileName.getExtension(fileItem.getName()));
            fileService.writeFile(fileItem, filePath);
            responseWriter.write(filePath.replace('\\', '/'));
        } catch (FileUploadException | ServiceException exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
