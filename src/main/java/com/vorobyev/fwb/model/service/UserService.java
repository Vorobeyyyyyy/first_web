package com.vorobyev.fwb.model.service;

import com.vorobyev.fwb.model.entity.User;
import com.vorobyev.fwb.exception.ServiceException;

public interface UserService  {
    User register(String login, String password, String firstName, String secondName, String phoneNumber, String email) throws ServiceException;

    User login(String login, String password) throws ServiceException;

    void changeAvatar(String userName, String newAvatarPath) throws ServiceException;

    User userByLogin(String login) throws ServiceException;
}
