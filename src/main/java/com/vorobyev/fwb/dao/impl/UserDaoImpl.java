package com.vorobyev.fwb.dao.impl;

import com.vorobyev.fwb.dao.UserDao;
import com.vorobyev.fwb.entity.User;
import com.vorobyev.fwb.entity.UserAccessLevel;
import com.vorobyev.fwb.exception.ConnectionPoolException;
import com.vorobyev.fwb.exception.DaoException;
import com.vorobyev.fwb.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Locale;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static UserDaoImpl instance = new UserDaoImpl();

    private final static String LOGIN_EXISTS = "SELECT COUNT(login) FROM users WHERE login=?";
    private final static String ADD_USER = "INSERT INTO users (login, password, first_name, second_name, email, user_level, avatar_image_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String USER_BY_LOGIN_AND_PASSWORD = "SELECT login, first_name, second_name, email, avatar_image_path, user_level FROM users WHERE login=? AND password=?";
    private final static String USER_BY_LOGIN = "SELECT login, first_name, second_name, email, avatar_image_path, user_level FROM users WHERE login=?";
    private final static String CHANGE_AVATAR = "UPDATE users SET avatar_image_path = ? WHERE login = ?";

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isLoginFree(String login) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(LOGIN_EXISTS)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next() && resultSet.getInt(1) == 0;
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public Optional<User> login(String login, String password) throws DaoException {
        Optional<User> optionalUser;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                optionalUser = Optional.of(userFromResultSet(resultSet));
            } else {
                optionalUser = Optional.empty();
            }
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
        return optionalUser;
    }

    @Override
    public void register(User user, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, password);
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getSecondName());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getLevel().toString());
            statement.setString(7, user.getAvatarPath());
            statement.execute();
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void changeAvatar(String username, String newImagePath) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHANGE_AVATAR)) {
            statement.setString(1, newImagePath);
            statement.setString(2, username);
            statement.execute();
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        Optional<User> optionalUser;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                optionalUser = Optional.of(userFromResultSet(resultSet));
            } else {
                optionalUser = Optional.empty();
            }
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
        return optionalUser;
    }

    private User userFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                UserAccessLevel.valueOf(resultSet.getString(6)));
    }
}
