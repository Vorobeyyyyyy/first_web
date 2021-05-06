package com.vorobyev.fwb.model.service.impl;

import com.vorobyev.fwb.model.dao.CommendDao;
import com.vorobyev.fwb.model.dao.impl.CommendDaoImpl;
import com.vorobyev.fwb.model.entity.Commend;
import com.vorobyev.fwb.exception.DaoException;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.service.CommendService;
import com.vorobyev.fwb.model.validator.CommendValidator;
import com.vorobyev.fwb.model.validator.UserValidator;

import java.util.Calendar;
import java.util.List;

public enum CommendServiceImpl implements CommendService {
    INSTANCE;

    private final static CommendDao commendDao = CommendDaoImpl.INSTANCE;

    @Override
    public List<Commend> findByPublicationId(long publicationId, String username) throws ServiceException {
        List<Commend> result;
        try {
            result = commendDao.findByPublicationId(publicationId, username);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public Commend addCommend(String body, String publisherNickname, long publicationId) throws ServiceException {
        if (!CommendValidator.isBodyValid(body)) {
            throw new ServiceException("Commend body " + body + " is invalid");
        }

        Commend commend = new Commend(publicationId, body, publisherNickname, Calendar.getInstance());
        try {
            commendDao.addCommend(commend);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
        return commend;
    }

    @Override
    public void likeCommend(long commendId, String username) throws ServiceException {
        try {
            commendDao.likeCommend(commendId, username);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
    }

    @Override
    public void unlikeCommend(long commendId, String username) throws ServiceException {
        try {
            commendDao.unlikeCommend(commendId, username);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
    }

    @Override
    public List<Commend> findByUsername(String username) throws ServiceException {
        if (!UserValidator.isLoginValid(username)) {
            throw new ServiceException("Username " + username + " not valid");
        }
        List<Commend> commends;
        try {
            commends = commendDao.findByUsername(username);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
        return commends;
    }

    @Override
    public List<Commend> findAll(int startIndex, int count) throws ServiceException {
        try {
            return commendDao.findAll(startIndex, count);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
    }

    @Override
    public void removeById(Long id) throws ServiceException {
        try {
            commendDao.removeById(id);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
    }


}
