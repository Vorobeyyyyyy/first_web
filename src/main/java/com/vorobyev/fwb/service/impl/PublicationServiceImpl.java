package com.vorobyev.fwb.service.impl;

import com.vorobyev.fwb.dao.PublicationDao;
import com.vorobyev.fwb.dao.impl.PublicationDaoImpl;
import com.vorobyev.fwb.entity.Publication;
import com.vorobyev.fwb.exception.DaoException;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.service.PublicationService;

import java.util.List;
import java.util.Optional;

public enum PublicationServiceImpl implements PublicationService {
    INSTANCE;

    private final static PublicationDao publicationDao = PublicationDaoImpl.getInstance();

    @Override
    public List<Publication> findPublications(int startIndex, int count) throws ServiceException {
        List<Publication> result;
        try {
            result = publicationDao.findPublications(startIndex, count);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public Optional<Publication> findPublicationById(long id) throws ServiceException {
        try {
            return publicationDao.findPublicationById(id);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
    }
}
