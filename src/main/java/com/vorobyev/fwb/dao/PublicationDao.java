package com.vorobyev.fwb.dao;

import com.vorobyev.fwb.entity.Publication;
import com.vorobyev.fwb.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface PublicationDao {
    List<Publication> findPublications(int startIndex, int count) throws DaoException;

    Optional<Publication> findPublicationById(long id) throws DaoException;
}
