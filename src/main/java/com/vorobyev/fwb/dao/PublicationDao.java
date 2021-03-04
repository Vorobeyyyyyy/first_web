package com.vorobyev.fwb.dao;

import com.vorobyev.fwb.entity.Publication;
import com.vorobyev.fwb.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface PublicationDao {
    List<Publication> find(int startIndex, int count) throws DaoException;

    List<Publication> findByPublisher(int startIndex, int count, String publisher) throws DaoException;

    Optional<Publication> findPublicationById(long id) throws DaoException;

    void addPublication(Publication publication) throws DaoException;
}
