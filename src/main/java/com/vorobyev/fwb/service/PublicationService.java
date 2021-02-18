package com.vorobyev.fwb.service;

import com.vorobyev.fwb.entity.Publication;
import com.vorobyev.fwb.entity.User;
import com.vorobyev.fwb.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;

import java.util.List;
import java.util.Optional;

public interface PublicationService {
    List<Publication> findPublications(int startIndex, int count) throws ServiceException;

    Optional<Publication> findPublicationById(long id) throws ServiceException;

    void addPublication(User user, String title, String mainImagePath, String summary, String content) throws ServiceException;
}
