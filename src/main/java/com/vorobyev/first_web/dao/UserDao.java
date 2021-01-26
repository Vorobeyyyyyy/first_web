package com.vorobyev.first_web.dao;

import com.vorobyev.first_web.entity.User;
import com.vorobyev.first_web.exception.DaoException;

import java.util.Optional;

public interface UserDao {
    boolean isLoginAndPasswordCorrect(String login, String password) throws DaoException;

    boolean isLoginFree(String login) throws DaoException;

    void register(User user) throws DaoException;

    Optional<User> getUserByLogin(String login) throws DaoException;
}
