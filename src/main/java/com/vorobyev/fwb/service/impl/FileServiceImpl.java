package com.vorobyev.fwb.service.impl;

import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.service.FileService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public enum FileServiceImpl implements FileService {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    private static final String PROPERTIES_PATH = "config/upload.properties";
    private static final String PATH_PROPERTY = "path";
    private static final String UPLOAD_PATH;

    static {
        try {
            Properties properties = new Properties();
            properties.load(FileServiceImpl.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH));
            UPLOAD_PATH = properties.getProperty(PATH_PROPERTY);
        } catch (IOException | NullPointerException exception) {
            logger.log(Level.FATAL, "Error while initializing upload service", exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void writeFile(FileItem fileItem, String fileName) throws ServiceException {
        try {
            String fullPath = UPLOAD_PATH + File.separator + fileName;
            if (Files.exists(Path.of(fullPath))) {
                Files.delete(Path.of(fullPath));
            }
            fileItem.write(new File(fullPath));
            logger.log(Level.INFO, "New file {}", fullPath);
        } catch (Exception exception) {
            throw new ServiceException("Cant write file", exception);
        }
    }

    @Override
    public byte[] readFile(String fileName) throws ServiceException {
        byte[] result;
        String fileUri = UPLOAD_PATH + File.separator + fileName;
        Path filePath = Path.of(fileUri);
        if (Files.exists(filePath)) {
            try {
                result = Files.readAllBytes(filePath);
            } catch (IOException exception) {
                throw new ServiceException("Cant read file", exception);
            }
        } else {
            throw new ServiceException("File not exists");
        }
        return result;
    }

    String getUploadPath() {
        return UPLOAD_PATH;
    }


}
