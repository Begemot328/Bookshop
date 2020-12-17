package by.epam.bookshop.service.author;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.author.MySQLAuthorDAO;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.service.book.BookService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class AuthorService implements EntityService<Author> {
    private static final String SQL_CONNECTION_EXCEPTION = "SQL Exception: ";
    private static final String DAO_EXCEPTION = "User DAO Exception: ";
    private static final String FACTORY_EXCEPTION = "User factory Exception: ";

    private static final AuthorService INSTANCE = new AuthorService();

    private AuthorService() {
    }


    public static EntityService<Author> getInstance() {
        return INSTANCE;
    }

    @Override
    public Author create(Object... args) throws ServiceException {
        try (Connection connection = getConnection()) {
            Author author = new AuthorFactory().create(args);
            new MySQLAuthorDAO(connection).create(author);
            return author;
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (FactoryException e) {
            throw new ServiceException(FACTORY_EXCEPTION, e);
        }
    }

    @Override
    public Author read(int id) throws ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLAuthorDAO(connection).read(id);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }

    @Override
    public void update(Author author) throws ServiceException {
        try (Connection connection = getConnection()) {
            new MySQLAuthorDAO(connection).update(author);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }

    @Override
    public void delete(Author author) throws ServiceException {
        try (Connection connection = getConnection()) {
            new MySQLAuthorDAO(connection).delete(author.getId());
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }

    @Override
    public Collection<Author> findBy(EntityFinder<Author> finder) throws ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLAuthorDAO(connection).findBy(finder);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }

    @Override
    public Collection<Author> findAll() throws DAOException, ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLAuthorDAO(connection).findAll();
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }
}
