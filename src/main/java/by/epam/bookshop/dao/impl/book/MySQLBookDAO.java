package by.epam.bookshop.dao.impl.book;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.dao.impl.author.MySQLAuthorDAO;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserFactory;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.UnknownEntityException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MySQLBookDAO extends MySQLEntityDAO<Book> {
    private static final String ID = "ID";
    private static final String TITLE = "TITLE";
    private static final String AUTHOR_ID = "AUTHOR_ID";
    private static final String PRICE = "PRICE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String PHOTO_LINK = "PHOTO_LINK";
    private static final String SCHEMA = "BOOKSHOP";
    private static final String TABLE = "BOOKS";
    
    
    public MySQLBookDAO(Connection connection) {
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

    public BookFinder getFinder() {
        return new BookFinder();
    }


    @Override
    public Map<String, Object> mapEntity(Book book) {
        Map<String, Object> map = new HashMap<>();
        map.put(TITLE, book.getTitle());
        map.put(AUTHOR_ID, book.getAuthor().getId());
        map.put(PRICE, book.getPrice());
        map.put(DESCRIPTION, book.getDescription());
        if (book.getPhotoLink() != null) {
            map.put(PHOTO_LINK, book.getPhotoLink());
        }
        return map;
    }

    @Override
    public Collection<Book> mapToList(ResultSet resultSet) throws DAOException {
        EntityFactory<Book> factory = new BookFactory();
        ArrayList<Book> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(factory.createWithID(resultSet.getInt(ID),
                        resultSet.getString(TITLE),
                        new MySQLAuthorDAO(connection)
                                .read(resultSet.getInt(AUTHOR_ID)),
                        resultSet.getString(DESCRIPTION),
                        resultSet.getFloat(PRICE),
                resultSet.getString(PHOTO_LINK)));
            }
            return result;
        } catch (SQLException | FactoryException e) {
            throw new DAOException(e);
        }
    }

}
