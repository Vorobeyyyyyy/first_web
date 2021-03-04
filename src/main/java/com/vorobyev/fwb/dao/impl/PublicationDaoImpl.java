package com.vorobyev.fwb.dao.impl;

import com.vorobyev.fwb.dao.PublicationDao;
import com.vorobyev.fwb.entity.Publication;
import com.vorobyev.fwb.exception.ConnectionPoolException;
import com.vorobyev.fwb.exception.DaoException;
import com.vorobyev.fwb.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class PublicationDaoImpl implements PublicationDao {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final PublicationDaoImpl INSTANCE = new PublicationDaoImpl();
    private static final String PUBLICATIONS = "SELECT publicationId, title, summary, content, preview_img_path, publisher, date FROM publications ORDER BY date DESC LIMIT ? OFFSET ?";
    private static final String PUBLICATIONS_BY_PUBLISHER = "SELECT publicationId, title, summary, content, preview_img_path, publisher, date FROM publications WHERE publisher = ? ORDER BY date DESC LIMIT ? OFFSET ?";
    private static final String PUBLICATION_BY_ID = "SELECT publicationId, title, summary, content, preview_img_path, publisher, date FROM publications WHERE publicationId=?";
    private static final String ADD_PUBLICATION = "INSERT INTO publications (title, preview_img_path, summary, content, date, publisher) VALUES (?, ?, ?, ?, ?, ?)";

    public static PublicationDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Publication> find(int startIndex, int count) throws DaoException {
        List<Publication> result = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(PUBLICATIONS)) {
            statement.setInt(1, count);
            statement.setInt(2, startIndex);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(publicationFromResultSet(resultSet));
            }
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public List<Publication> findByPublisher(int startIndex, int count, String publisher) throws DaoException {
        List<Publication> result = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(PUBLICATIONS_BY_PUBLISHER)) {
            statement.setString(1, publisher);
            statement.setInt(2, count);
            statement.setInt(3, startIndex);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(publicationFromResultSet(resultSet));
            }
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public Optional<Publication> findPublicationById(long id) throws DaoException {
        Optional<Publication> result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(PUBLICATION_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(publicationFromResultSet(resultSet));
            } else {
                result = Optional.empty();
            }
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
        return result;
    }

    @Override
    public void addPublication(Publication publication) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_PUBLICATION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, publication.getTitle());
            statement.setString(2, publication.getPreviewImagePath());
            statement.setString(3, publication.getSummary());
            statement.setString(4, publication.getContent());
            statement.setLong(5, publication.getCalendar().getTimeInMillis());
            statement.setString(6, publication.getPublisherNickname());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                publication.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException | ConnectionPoolException exception) {
            throw new DaoException(exception);
        }
    }

    private Publication publicationFromResultSet(ResultSet resultSet) throws SQLException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(resultSet.getLong(7));
        return new Publication(resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                calendar);
    }
}
