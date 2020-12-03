package by.epam.bookshop.dao.impl;

import by.epam.bookshop.dao.EntityDAO;
import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.exceptions.FactoryException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class AuthorDAO implements EntityDAO<Author> {
    private static final String ID = "ID";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";

    private Connection connection;

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Author author) {
        return false;
    }

    @Override
    public Author read(int id) {
        return null;
    }

    @Override
    public Author update(Author author) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
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
