package com.vorobyev.fwb.model.dao;

import com.vorobyev.fwb.model.entity.Publication;
import com.vorobyev.fwb.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface PublicationDao {
    List<Publication> findAll(int startIndex, int count) throws DaoException;

    List<Publication> findByPublisher(int startIndex, int count, String publisher) throws DaoException;

    List<Publication> findByText(int startIndex, int count, String text) throws DaoException;

    Optional<Publication> findPublicationById(long id) throws DaoException;

    void createPublication(Publication publication) throws DaoException;

    void updatePublication(Publication publication) throws DaoException;

    long findPublicationsCount() throws DaoException;

    void removeById(Long id) throws DaoException;
}
