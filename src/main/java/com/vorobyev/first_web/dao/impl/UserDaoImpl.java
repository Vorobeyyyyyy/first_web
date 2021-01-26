package com.vorobyev.first_web.dao.impl;

import com.vorobyev.first_web.dao.UserDao;
import com.vorobyev.first_web.entity.User;
import com.vorobyev.first_web.warehouse.UserWarehouse;

import java.util.Optional;

public class UserDaoImpl implements UserDao {
    UserWarehouse userWarehouse = UserWarehouse.getInstance();

    private static final UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isLoginAndPasswordCorrect(String login, String password) {
        return userWarehouse.stream().anyMatch(o -> o.getLogin().equals(login) && o.getPassword().equals(password));
    }

    @Override
    public boolean isLoginFree(String login) {
        return userWarehouse.stream().noneMatch(o -> o.getLogin().equals(login));
    }

    @Override
    public void register(User user) {
        userWarehouse.add(user);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        return userWarehouse.stream().filter(o -> o.getLogin().equals(login)).findFirst();
    }
}
