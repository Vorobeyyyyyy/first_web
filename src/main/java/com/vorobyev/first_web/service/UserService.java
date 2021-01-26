package com.vorobyev.first_web.service;

import com.vorobyev.first_web.entity.User;
import com.vorobyev.first_web.exception.ServiceException;

public interface UserService  {
    User register(String login, String password, String firstName, String secondName, String phoneNumber) throws ServiceException;

    User login(String login, String password) throws ServiceException;
}
