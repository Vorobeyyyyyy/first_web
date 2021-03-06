package com.vorobyev.fwb.model.service.impl;

import com.vorobyev.fwb.model.dao.PublicationDao;
import com.vorobyev.fwb.model.dao.impl.PublicationDaoImpl;
import com.vorobyev.fwb.model.entity.Publication;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.exception.DaoException;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.service.PublicationService;
import com.vorobyev.fwb.model.validator.UserValidator;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public enum PublicationServiceImpl implements PublicationService {
    INSTANCE;

    private final static PublicationDao publicationDao = PublicationDaoImpl.getInstance();

    @Override
    public List<Publication> findAll(int startIndex, int count) throws ServiceException {
        List<Publication> result;
        try {
            result = publicationDao.findAll(startIndex, count);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public List<Publication> findPublicationsByPublisher(int startIndex, int count, String publisher) throws ServiceException {
        List<Publication> result;
        if (!UserValidator.isLoginValid(publisher)) {
            throw new ServiceException("Invalid username " + publisher);
        }
        try {
            result = publicationDao.findByPublisher(startIndex, count, publisher);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public List<Publication> findPublicationsByText(int startIndex, int count, String text) throws ServiceException {
        List<Publication> result;
        try {
            result = publicationDao.findByText(startIndex, count, text);
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

    @Override
    public Publication createPublication(User user, String title, String mainImagePath, String summary, String content) throws ServiceException {
        Publication publication = new Publication(0, title, summary, content, mainImagePath, user.getLogin(), Calendar.getInstance());
        try {
            publicationDao.createPublication(publication);
        } catch (DaoException exception) {
            throw new ServiceException("when adding publication", exception);
        }
        return publication;
    }

    @Override
    public Publication updatePublication(Long id, User user, String title, String mainImagePath, String summary, String content) throws ServiceException {
        Publication publication = new Publication(id, title, summary, content, mainImagePath, user.getLogin(), Calendar.getInstance());
        try {
            publicationDao.updatePublication(publication);
        } catch (DaoException exception) {
            throw new ServiceException("when adding publication", exception);
        }
        return publication;
    }


    @Override
    public long findPublicationCount() throws ServiceException {
        try {
            return publicationDao.findPublicationsCount();
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
    }

    @Override
    public void removeById(Long id) throws ServiceException {
        try {
            publicationDao.removeById(id);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
    }
}
