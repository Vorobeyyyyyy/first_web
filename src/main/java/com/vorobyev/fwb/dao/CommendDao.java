package com.vorobyev.fwb.dao;

import com.vorobyev.fwb.entity.Commend;
import com.vorobyev.fwb.exception.DaoException;

import java.util.List;

public interface CommendDao {
    void addCommend(Commend commend) throws DaoException;

    List<Commend> findByPublicationId(long publicationId, String username) throws DaoException;

    List<Commend> findByUsername(String username) throws DaoException;

    void likeCommend(long commendId, String username) throws DaoException;

    void unlikeCommend(long commendId, String username) throws DaoException;
}
