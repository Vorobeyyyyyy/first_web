package com.vorobyev.fwb.dao;

import com.vorobyev.fwb.entity.User;
import com.vorobyev.fwb.exception.DaoException;

import java.util.Optional;

public interface UserDao {
    boolean isLoginFree(String login) throws DaoException;

    Optional<User> login(String login, String password) throws DaoException;

    void register(User user, String password) throws DaoException;

    void changeAvatar(String username, String newImagePath) throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;
}
