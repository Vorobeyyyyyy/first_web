package com.vorobyev.fwb.service;

import com.vorobyev.fwb.entity.User;
import com.vorobyev.fwb.exception.ServiceException;

public interface UserService  {
    User register(String login, String password, String firstName, String secondName, String phoneNumber, String email) throws ServiceException;

    User login(String login, String password) throws ServiceException;
}
