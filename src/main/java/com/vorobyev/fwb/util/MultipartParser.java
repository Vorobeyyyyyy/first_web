package com.vorobyev.fwb.util;

import org.apache.commons.fileupload.FileItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MultipartParser {
    private final static Logger logger = LogManager.getLogger();

    public static Optional<String> getParameter(List<FileItem> items, String parameter) {
        return items.stream().filter(item -> item.isFormField() && item.getFieldName().equals(parameter)).findFirst().map(FileItem::getString);
    }

    public static List<FileItem> takeFiles(List<FileItem> fileItemList) {
        return fileItemList.stream().filter(fileItem -> !fileItem.isFormField()).collect(Collectors.toList());
    }
}
