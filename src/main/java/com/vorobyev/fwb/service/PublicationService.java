package com.vorobyev.fwb.service;

import com.vorobyev.fwb.entity.Publication;
import com.vorobyev.fwb.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface PublicationService {
    List<Publication> findPublications(int startIndex, int count) throws ServiceException;

    Optional<Publication> findPublicationById(long id) throws ServiceException;
}
