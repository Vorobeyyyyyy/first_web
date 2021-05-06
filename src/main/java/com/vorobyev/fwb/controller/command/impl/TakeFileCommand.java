package com.vorobyev.fwb.controller.command.impl;

import com.vorobyev.fwb.controller.command.Command;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.service.FileService;
import com.vorobyev.fwb.model.service.impl.FileServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TakeFileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final String FILE_NAME = "file_name";
    private static final FileService fileService = FileServiceImpl.INSTANCE;
    private static final String CAUSE_HEADER = "cause";

    @Override
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter(FILE_NAME);
        if (fileName.isEmpty()) {
            return "";
        }
        try (ServletOutputStream outputStream = response.getOutputStream();) {
            outputStream.write(fileService.readFile(fileName));
        } catch (IOException | ServiceException exception) {
            logger.log(Level.ERROR, exception);
            response.addHeader(CAUSE_HEADER, exception.getMessage());
        }
        return "";
    }
}
