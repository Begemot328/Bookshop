package by.epam.bookshop.service.user;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.user.MySQLUserDAO;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserFactory;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.EntityService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

public class UserService implements EntityService<User> {
    private static final String SQL_CONNECTION_EXCEPTION = "SQL Exception: ";
    private static final String DAO_EXCEPTION = "User DAO Exception: ";
    private static final String FACTORY_EXCEPTION = "User factory Exception: ";

    private static final UserService INSTANCE = new UserService();

    private UserService() {};

    @Override
    public EntityService<User> getInstance() {
        return INSTANCE;
    }

    @Override
    public User create(Object... args) throws ServiceException {
        try  (Connection connection = getConnection()) {
            User user = new UserFactory().create(args);
            new MySQLUserDAO(connection).create(user);
            return user;
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION,e);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION,e);
        } catch (FactoryException e) {
            throw new ServiceException(FACTORY_EXCEPTION,e);
        }
    }

    @Override
    public User read(int id) throws ServiceException {
        try  (Connection connection = getConnection()) {
            return new MySQLUserDAO(connection).read(id);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION,e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION,e);
        }
    }

    @Override
    public void update(User user) throws ServiceException {
        try  (Connection connection = getConnection()) {
            new MySQLUserDAO(connection).update(user);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION,e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION,e);
        }
    }

    @Override
    public void delete(User user) throws ServiceException {
        try  (Connection connection = getConnection()) {
            new MySQLUserDAO(connection).delete(user.getId());
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION,e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION,e);
        }
    }

    @Override
    public Collection<User> findBy(EntityFinder<User> finder) throws ServiceException {
        try  (Connection connection = getConnection()) {
            return new MySQLUserDAO(connection).findBy(finder);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION,e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION,e);
        }
    }

    @Override
    public Collection<User> findAll() throws ServiceException {
        try  (Connection connection = getConnection()) {
            return new MySQLUserDAO(connection).findAll();
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION,e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION,e);
        }
    }


    public User createUser(UserStatus status, Object... args) throws ServiceException {
        if (args.length == 5) {
            args = Arrays.copyOf(args,6);
            args[6] = status;
        } else {
            args[6] = status;
        }
        return create(args);
    }
}
