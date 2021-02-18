package com.vorobyev.fwb.service;

import com.vorobyev.fwb.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;

public interface FileService {

    void writeFile(FileItem fileItem, String fileName) throws ServiceException;

    byte[] readFile(String path) throws ServiceException;
}
