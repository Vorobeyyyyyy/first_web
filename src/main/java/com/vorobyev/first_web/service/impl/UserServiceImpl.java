package com.vorobyev.first_web.service.impl;

import com.vorobyev.first_web.dao.UserDao;
import com.vorobyev.first_web.dao.impl.UserDaoImpl;
import com.vorobyev.first_web.dao.impl.UserDaoSql;
import com.vorobyev.first_web.entity.User;
import com.vorobyev.first_web.exception.DaoException;
import com.vorobyev.first_web.exception.ServiceException;
import com.vorobyev.first_web.service.UserService;
import com.vorobyev.first_web.util.PasswordCoder;
import com.vorobyev.first_web.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.NoSuchElementException;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();

    private static final UserServiceImpl instance = new UserServiceImpl();

    private final UserDao userDao = UserDaoSql.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    public User register(String login, String password, String firstName, String secondName, String phoneNumber) throws ServiceException {
        User user;
        password = PasswordCoder.code(password);
        try {
            if (!UserValidator.isLoginValid(login) || !UserValidator.isPasswordValid(password)) {
                throw new ServiceException("Login or password invalid");
            }
            if (userDao.isLoginFree(login)) {
                user = new User(login, password, firstName, secondName, phoneNumber);
                userDao.register(user);
                logger.log(Level.INFO, "New user {}", login);
            } else {
                throw new ServiceException("Login is already used");
            }
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
        return user;
    }

    @Override
    public User login(String login, String password) throws ServiceException {
        User user;
        password = PasswordCoder.code(password);
        try {
            if (userDao.isLoginAndPasswordCorrect(login, password)) {
                try {
                    user = userDao.getUserByLogin(login).orElseThrow();
                } catch (NoSuchElementException e) {
                    throw new ServiceException("Login or password incorrect");
                }
            } else {
                throw new ServiceException("Login or password incorrect");
            }
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
        return user;
    }
}
