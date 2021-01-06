package by.epam.bookshop.service.author;

import by.epam.bookshop.dao.EntityDAO;
import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.dao.impl.author.MySQLAuthorDAO;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.AbstractEntityService;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.service.book.BookService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class AuthorService extends AbstractEntityService<Author> {

    private static final AuthorService INSTANCE = new AuthorService();

    private AuthorService() {
    }

    public static EntityService<Author> getInstance() {
        return INSTANCE;
    }

    public MySQLAuthorDAO getDAO(Connection connection) {
        return new MySQLAuthorDAO(connection);
    }

    public AuthorFactory getFactory() {
        return new AuthorFactory();
    }

}
