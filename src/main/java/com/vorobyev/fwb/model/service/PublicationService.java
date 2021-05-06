package com.vorobyev.fwb.model.service;

import com.vorobyev.fwb.model.entity.Publication;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface PublicationService {
    List<Publication> findAll(int startIndex, int count) throws ServiceException;

    List<Publication> findPublicationsByPublisher(int startIndex, int count, String publisher) throws ServiceException;

    List<Publication> findPublicationsByText(int startIndex, int count, String text) throws ServiceException;

    Optional<Publication> findPublicationById(long id) throws ServiceException;

    Publication createPublication(User user, String title, String mainImagePath, String summary, String content) throws ServiceException;

    Publication updatePublication(Long id, User user, String title, String mainImagePath, String summary, String content) throws ServiceException;

    long findPublicationCount() throws ServiceException;

    void removeById(Long id) throws ServiceException;
}
