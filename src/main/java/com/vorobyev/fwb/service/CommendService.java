package com.vorobyev.fwb.service;

import com.vorobyev.fwb.entity.Commend;
import com.vorobyev.fwb.exception.ServiceException;

import java.util.List;

public interface CommendService {
    List<Commend> findByPublicationId(long publicationId, String username) throws ServiceException;

    Commend addCommend(String body, String publisherNickname, long publicationId) throws ServiceException;

    void likeCommend(long commendId, String username) throws ServiceException;

    void unlikeCommend(long commendId, String username) throws ServiceException;

    List<Commend> findByUsername(String username) throws ServiceException;
}
