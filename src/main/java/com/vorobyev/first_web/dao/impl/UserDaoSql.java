package com.vorobyev.first_web.dao.impl;

import com.vorobyev.first_web.dao.UserDao;
import com.vorobyev.first_web.entity.User;
import com.vorobyev.first_web.exception.DaoException;
import com.vorobyev.first_web.pool.ConnectionCreator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class UserDaoSql implements UserDao {
    private static final Logger logger = LogManager.getLogger();

    private final static UserDaoSql instance = new UserDaoSql();

    private final static String LOGIN_AND_PASS_STATEMENT="SELECT COUNT(id) FROM users WHERE login=? AND password=?";
    private final static String LOGIN_STATEMENT="SELECT COUNT(id) FROM users WHERE login=?";
    private final static String ADD_USER_STATEMENT = "INSERT INTO users (login, password, first_name, second_name, phone_number) VALUES (?, ?, ?, ?, ?)";
    private final static String USER_BY_LOGIN_STATEMENT = "SELECT password, first_name, second_name, phone_number FROM users WHERE login=?";

    private UserDaoSql() {
    }

    public static UserDaoSql getInstance() {
        return instance;
    }

    @Override
    public boolean isLoginAndPasswordCorrect(String login, String password) throws DaoException {
        boolean result;
        try {
            Connection connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(LOGIN_AND_PASS_STATEMENT);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next() && resultSet.getInt(1) != 0;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public boolean isLoginFree(String login) throws DaoException {
        boolean result;
        try {
            Connection connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(LOGIN_STATEMENT);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next() && resultSet.getInt(1) == 0;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public void register(User user) throws DaoException {
        try {
            Connection connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_USER_STATEMENT);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getSecondName());
            statement.setString(5, user.getPhoneNumber());
            statement.execute();
            statement.close();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login) throws DaoException {
        User user;
        try {
            Connection connection = ConnectionCreator.createConnection();
            PreparedStatement statement = connection.prepareStatement(USER_BY_LOGIN_STATEMENT);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user = new User(login,
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
        return Optional.of(user);
    }
}
