package by.epam.bookshop.dao.impl.author;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MySQLAuthorDAO extends MySQLEntityDAO<Author> {
    private static final String ID = "ID";
    private static final String FIRSTNAME = "first_name";
    private static final String LASTNAME = "last_name";
    private static final String SQL_EXCEPTION = "SQL Exception: ";
    private static final String FACTORY_EXCEPTION = "Factory Exception: ";
    private static final String SCHEMA = "BOOKSHOP";
    private static final String TABLE = "AUTHORS";
    private static final String PHOTO_LINK = "PHOTO_LINK";

    public MySQLAuthorDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Author author) throws DAOException {
        return create(author, SCHEMA, TABLE, mapEntity(author));
    }

    @Override
    public Author read(int id) throws DAOException {
        Collection<Author> result = findBy((new AuthorFinder()).findByID(id));
        if (result == null || result.size() != 1) {
            return null;
        } else {
            return result.toArray(Author[]::new)[0];
        }
    }

    @Override
    public void update(Author author) throws DAOException {
        update(author, SCHEMA, TABLE, mapEntity(author));
    }

    @Override
    public void delete(int id) throws DAOException {
        delete(id, SCHEMA, TABLE);
    }

    @Override
    public Collection<Author> findAll() throws DAOException {
        return findBy(new AuthorFinder());
    }

    @Override
    public Collection<Author> findBy(EntityFinder<Author> finder) throws DAOException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(finder.getQuery())) {
                return mapToList(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Collection<Author> mapToList(ResultSet resultSet) throws DAOException {
        EntityFactory<Author> factory = new AuthorFactory();
        ArrayList<Author> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(factory.createWithID(resultSet.getInt(ID),
                        resultSet.getString(FIRSTNAME),
                        resultSet.getString(LASTNAME),
                        resultSet.getString(PHOTO_LINK)));
            }
            return result;
        } catch (SQLException | FactoryException e) {
            throw new DAOException(e);
        }
    }

    public Map<String, Object> mapEntity(Author author) {
        Map<String, Object> map = new HashMap<>();
        map.put(FIRSTNAME, author.getFirstName());
        map.put(LASTNAME, author.getLastName());
        if (author.getPhotoLink() != null) {
            map.put(PHOTO_LINK, author.getPhotoLink());
        }
        return map;
    }
}
