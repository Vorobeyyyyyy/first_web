package com.vorobyev.fwb.dao.impl;

import com.vorobyev.fwb.dao.CommendDao;
import com.vorobyev.fwb.entity.Commend;
import com.vorobyev.fwb.exception.ConnectionPoolException;
import com.vorobyev.fwb.exception.DaoException;
import com.vorobyev.fwb.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public enum CommendDaoImpl implements CommendDao {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String TAKE_COMMEND_BY_PUBLICATION_ID = "SELECT c.commendId, " +
            "c.publicationId, " +
            "c.body, " +
            "c.date, " +
            "c.publisherId, " +
            "u.avatar_image_path, " +
            "COUNT(l.userId), " +
            "(SELECT IF(count(l.userId) != 0, TRUE, FALSE) FROM likes l WHERE l.userId = ? AND l.commendId = c.commendId) " +
            "FROM commends c " +
            "JOIN users u ON c.publisherId = u.login " +
            "LEFT JOIN likes l ON c.commendId = l.commendId " +
            "WHERE c.publicationId = ? "+
            "GROUP BY c.commendId";
    private final static String TAKE_COMMEND_BY_USERNAME = "SELECT c.commendId, c.body, c.publicationId, p.title " +
            "FROM commends c " +
            "JOIN publications p on c.publicationId = p.publicationId " +
            "WHERE c.publisherId = ? " +
            "GROUP BY c.commendId";
    private final static String INSERT_COMMEND = "INSERT INTO commends (body, publisherId, publicationId, date) VALUES (?, ?, ?, ?)";
    private final static String LIKE_COMMEND = "INSERT INTO likes (userId, commendId) VALUES (?, ?)";
    private final static String UNLIKE_COMMEND = "DELETE FROM likes WHERE userId = ? AND commendId = ?";

    @Override
    public void addCommend(Commend commend) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_COMMEND, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, commend.getBody());
            statement.setString(2, commend.getPublisherName());
            statement.setLong(3, commend.getPublicationId());
            statement.setLong(4, commend.getDate().getTimeInMillis());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                commend.setId(resultSet.getLong(1));
            }
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Commend> findByPublicationId(long publicationId, String requestingUsername) throws DaoException {
        List<Commend> result = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(TAKE_COMMEND_BY_PUBLICATION_ID)) {
            statement.setString(1, requestingUsername);
            statement.setLong(2, publicationId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(commendFromResultSet(resultSet));
            }
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public List<Commend> findByUsername(String username) throws DaoException {
        List<Commend> result = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(TAKE_COMMEND_BY_USERNAME)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Commend commend = new Commend();
                commend.setId(resultSet.getLong(1));
                commend.setBody(resultSet.getString(2));
                commend.setPublicationId(resultSet.getLong(3));
                commend.setPublicationTitle(resultSet.getString(4));
                result.add(commend);
            }
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public void likeCommend(long commendId, String username) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(LIKE_COMMEND)) {
            statement.setString(1, username);
            statement.setLong(2, commendId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void unlikeCommend(long commendId, String username) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UNLIKE_COMMEND)) {
            statement.setString(1, username);
            statement.setLong(2, commendId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
    }

    private Commend commendFromResultSet(ResultSet resultSet) throws SQLException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(resultSet.getLong(4));
        return new Commend(resultSet.getLong(1),
                resultSet.getLong(2),
                resultSet.getString(3),
                calendar,
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getInt(7),
                resultSet.getBoolean(8));
    }


}
