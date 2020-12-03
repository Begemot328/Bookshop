package by.epam.bookshop.dao.impl;

import by.epam.bookshop.dao.EntityDAO;
import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.exceptions.FactoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class AuthorDAO implements EntityDAO<Author> {
    private static final String ID = "ID";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";

    private static final String INSERT_QUERY =
            "INSERT INTO BOOKSHOP.authors(FIRST_NAME, LAST_NAME) VALUES (?,?);";

    private static final String DELETE_QUERY =
            "DELETE FROM BOOKSHOP.authors where ID = ?;";
    private static final String UPDATE_QUERY =
            "UPDATE BOOKSHOP.authors SET FIRST_NAME = ?, LAST_NAME = ? WHERE ID = ?;";

    private Connection connection;

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Author author) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());

            statement.executeUpdate();

            if (statement.getGeneratedKeys().next()) {
                author.setId(statement.getGeneratedKeys().getInt(0));
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Author read(int id) throws FactoryException, SQLException {
        return findById(id);
    }

    @Override
    public void update(Author author) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                UPDATE_QUERY)) {
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());
            statement.setInt(3, author.getId());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                DELETE_QUERY)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    @Override
    public Collection<Author> findAll() throws SQLException, FactoryException {
        return findBy(new AuthorFinder());
    }

    @Override
    public Author findById(int id) throws SQLException, FactoryException {
        Collection<Author> result = findBy(new AuthorFinder().findByID(id));
        if (result == null || result.isEmpty() || result.size() > 1) {
            return null;
        } else {
            return result.stream().toArray(Author[]::new)[0];
        }
    }

    @Override
    public Collection<Author> findBy(EntityFinder<Author> finder) throws SQLException, FactoryException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(finder.getQuery())) {
                return mapToList(resultSet);
            }
        }
    }

    private Collection<Author> mapToList(ResultSet resultSet) throws SQLException, FactoryException {
        EntityFactory<Author> factory = new AuthorFactory();
        ArrayList<Author> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(factory.create(resultSet.getInt(ID),
                    resultSet.getString(FIRSTNAME),
                    resultSet.getString(LASTNAME)));
        }
        return result;
    }
}
