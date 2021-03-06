package com.vorobyev.fwb.model.dao;

import com.vorobyev.fwb.exception.DaoException;
import com.vorobyev.fwb.model.entity.Commend;

import java.util.List;

public interface CommendDao {
    void addCommend(Commend commend) throws DaoException;

    List<Commend> findByPublicationId(long publicationId, String username) throws DaoException;

    List<Commend> findByUsername(String username) throws DaoException;

    void likeCommend(long commendId, String username) throws DaoException;

    void unlikeCommend(long commendId, String username) throws DaoException;

    List<Commend> findAll(int startIndex, int count) throws DaoException;

    public void removeById(Long id) throws DaoException;
}
