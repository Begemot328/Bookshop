package by.epam.bookshop.service.book;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.EntityService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class BookService implements EntityService<Book> {
    private static final String SQL_CONNECTION_EXCEPTION = "SQL Exception: ";
    private static final String DAO_EXCEPTION = "User DAO Exception: ";
    private static final String FACTORY_EXCEPTION = "User factory Exception: ";

    private static final BookService INSTANCE = new BookService();

    private BookService() {
    }


    public static EntityService<Book> getInstance() {
        return INSTANCE;
    }

    @Override
    public Book create(Object... args) throws ServiceException {
        try (Connection connection = getConnection()) {
            Book book = new BookFactory().create(args);
            new MySQLBookDAO(connection).create(book);
            return book;
        } catch (SQLException e) {
            throw new ServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (FactoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Book read(int id) throws ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLBookDAO(connection).read(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Book book) throws ServiceException {
        try (Connection connection = getConnection()) {
            new MySQLBookDAO(connection).update(book);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Book book) throws ServiceException {
        try (Connection connection = getConnection()) {
            new MySQLBookDAO(connection).delete(book.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<Book> findBy(EntityFinder<Book> finder) throws ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLBookDAO(connection).findBy(finder);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<Book> findAll() throws DAOException, ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLBookDAO(connection).findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
