package com.vorobyev.fwb.service;

import com.vorobyev.fwb.entity.Publication;
import com.vorobyev.fwb.entity.User;
import com.vorobyev.fwb.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface PublicationService {
    List<Publication> findPublications(int startIndex, int count) throws ServiceException;

    List<Publication> findPublicationsByPublisher(int startIndex, int count, String publisher) throws ServiceException;

    Optional<Publication> findPublicationById(long id) throws ServiceException;

    Publication addPublication(User user, String title, String mainImagePath, String summary, String content) throws ServiceException;
}
