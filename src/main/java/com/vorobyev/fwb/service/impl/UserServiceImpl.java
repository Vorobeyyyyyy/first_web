package com.vorobyev.fwb.service.impl;

import com.vorobyev.fwb.dao.UserDao;
import com.vorobyev.fwb.dao.impl.UserDaoImpl;
import com.vorobyev.fwb.entity.User;
import com.vorobyev.fwb.entity.UserAccessLevel;
import com.vorobyev.fwb.exception.DaoException;
import com.vorobyev.fwb.exception.ServiceException;
import com.vorobyev.fwb.service.UserService;
import com.vorobyev.fwb.util.PasswordCoder;
import com.vorobyev.fwb.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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

        if (!UserValidator.isFirstNameValid(firstName)) {
            throw new ServiceException("First name " + firstName + " is invalid");
        }

        if (!UserValidator.isSecondNameValid(secondName)) {
            throw new ServiceException("Second name " + secondName + " is invalid");
        }

        if (!UserValidator.isPhoneValid(phoneNumber)) {
            throw new ServiceException("Phone number " + phoneNumber + " is invalid");
        }

        if (!UserValidator.isEmailValid(email)) {
            throw new ServiceException("Email " + email + " is invalid");
        }

        try {
            if (userDao.isLoginFree(login)) {
                user = new User(login, firstName, secondName, phoneNumber, email);
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
    public User login(String login, String password) throws ServiceException { //fixme add validation
        User user;
        Optional<String> optionalPassword = PasswordCoder.code(password);
        if (optionalPassword.isEmpty()) {
            throw new ServiceException("Password coder error - NO SUCH ALGORITHM");
        }
        password = optionalPassword.get();

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
    public void changeAvatar(User user, String username, String newAvatarPath) throws ServiceException {
        try {
            userDao.changeAvatar(username, newAvatarPath.replace('\\', '/'));
        } catch (DaoException exception) {
            throw new ServiceException(exception);
        }
    }

    @Override
    public boolean haveRightsToChangeProfile(User user, String username) {
        return user.getLogin().equals(username) || user.getLevel().equals(UserAccessLevel.ADMIN);
    }
}
