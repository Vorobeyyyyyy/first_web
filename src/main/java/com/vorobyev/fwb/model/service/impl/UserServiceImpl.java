package com.vorobyev.fwb.model.service.impl;

import com.vorobyev.fwb.model.dao.UserDao;
import com.vorobyev.fwb.model.dao.impl.UserDaoImpl;
import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.exception.DaoException;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.model.service.UserService;
import com.vorobyev.fwb.model.validator.UserValidator;
import com.vorobyev.fwb.util.PasswordCoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();

    private static final UserServiceImpl instance = new UserServiceImpl();

    private final UserDao userDao = UserDaoImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public User register(String login, String password, String firstName, String secondName, String phoneNumber, String email) throws ServiceException {
        User user;
        Optional<String> optionalPassword = PasswordCoder.code(password);
        if (optionalPassword.isEmpty()) {
            throw new ServiceException("Password coder error - NO SUCH ALGORITHM");
        }
        password = optionalPassword.get();
        if (!UserValidator.isLoginValid(login)) {
            throw new ServiceException("Login " + login + " is invalid");
        }
        if (!UserValidator.isPasswordValid(password)) {
            throw new ServiceException("Password is invalid");
        }
        if (!UserValidator.isNameValid(firstName)) {
            throw new ServiceException("First name " + firstName + " is invalid");
        }
        if (!UserValidator.isNameValid(secondName)) {
            throw new ServiceException("Second name " + secondName + " is invalid");
        }
        if (!UserValidator.isEmailValid(email)) {
            throw new ServiceException("Email " + email + " is invalid");
        }

        try {
            if (userDao.isLoginFree(login)) {
                user = new User(login, firstName, secondName, email);
                userDao.register(user, password);
                logger.log(Level.INFO, "New user {}", login);
            } else {
                throw new ServiceException("Login " + login + " is already used");
            }
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
        return user;
    }

    @Override
    public User login(String login, String password) throws ServiceException {
        User user;
        Optional<String> optionalPassword = PasswordCoder.code(password);
        if (optionalPassword.isEmpty()) {
            throw new ServiceException("Password coder error - NO SUCH ALGORITHM");
        }
        password = optionalPassword.get();
        if (!UserValidator.isLoginValid(login)) {
            throw new ServiceException("Login " + login + " is invalid");
        }
        if (!UserValidator.isPasswordValid(password)) {
            throw new ServiceException("Password is invalid");
        }

        try {
            Optional<User> optionalUser = userDao.login(login, password);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                throw new ServiceException("Login or password incorrect");
            }
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
        return user;
    }

    @Override
    public void changeAvatar(String username, String newAvatarPath) throws ServiceException {
        if (!UserValidator.isLoginValid(username)) {
            throw new ServiceException("Login " + username + " is invalid");
        }

        try {
            userDao.changeAvatar(username, newAvatarPath);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
    }

    @Override
    public User userByLogin(String login) throws ServiceException {
        if (!UserValidator.isLoginValid(login)) {
            throw new ServiceException("Invalid login " + login);
        }
        User user;
        try {
            Optional<User> optionalUser = userDao.findByLogin(login);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                throw new ServiceException("Wrong login " + login);
            }
        } catch (DaoException exception) {
            throw new ServiceException("in userByLogin", exception);
        }
        return user;
    }

    @Override
    public List<User> findAll(int startIndex, int count) throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAll(startIndex, count);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
        return users;
    }

    @Override
    public void removeByLogin(String login) throws ServiceException {
        try {
            userDao.removeByLogin(login);
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
    }
}
