package by.epam.bookshop.service.book;

import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.service.AbstractEntityService;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.validator.impl.BookValidator;
import by.epam.bookshop.validator.EntityValidator;

import java.sql.Connection;

public class BookService extends AbstractEntityService<Book> {
    private static final String SQL_CONNECTION_EXCEPTION = "SQL Exception: ";
    private static final String DAO_EXCEPTION = "User DAO Exception: ";
    private static final String FACTORY_EXCEPTION = "User factory Exception: ";

    private static final BookService INSTANCE = new BookService();

    private BookService() {
    }


    public static EntityService<Book> getInstance() {
        return INSTANCE;
    }

    public MySQLBookDAO getDAO(Connection connection) {
        return new MySQLBookDAO(connection);
    }

    public BookFactory getFactory() {
        return new BookFactory();
    }

    @Override
    public EntityValidator<Book> getValidator() {
        return new BookValidator();
    }
}
