package by.epam.bookshop.dao.impl.author;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;

import java.net.MalformedURLException;
import java.net.URL;
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
    private static final String DESCRIPTION = "DESCRIPTION";

    public MySQLAuthorDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public String getSchemaName() {
        return SCHEMA;
    }

    public AuthorFinder getFinder() {
        return new AuthorFinder();
    }



    @Override
    public Collection<Author> mapToList(ResultSet resultSet) throws DAOException {
        EntityFactory<Author> factory = new AuthorFactory();
        ArrayList<Author> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                URL link = (resultSet.getString(PHOTO_LINK) == null || resultSet.getString(PHOTO_LINK).isEmpty()
                        ? null : new URL(resultSet.getString(PHOTO_LINK)));
                result.add(factory.createWithID(resultSet.getInt(ID),
                        resultSet.getString(FIRSTNAME),
                        resultSet.getString(LASTNAME),
                        resultSet.getString(DESCRIPTION),
                        link));
            }
            return result;
        } catch (SQLException | FactoryException | MalformedURLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Map<String, Object> mapEntity(Author author) {
        Map<String, Object> map = new HashMap<>();
        map.put(FIRSTNAME, author.getFirstName());
        map.put(LASTNAME, author.getLastName());
        if (author.getPhotoLink() != null) {
            map.put(PHOTO_LINK, author.getPhotoLink().toString());
        }
        if (author.getPhotoLink() != null) {
            map.put(DESCRIPTION, author.getDescription());
        }
        return map;
    }
}
