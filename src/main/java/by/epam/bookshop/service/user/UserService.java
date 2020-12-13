package by.epam.bookshop.service.user;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.user.MySQLUserDAO;
import by.epam.bookshop.dao.impl.user.UserFinder;
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
    private static final String WRONG_INPUT_EXCEPTION
            = "Wrong data input!";

    private static final UserService INSTANCE = new UserService();

    private UserService() {};


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

    public User signIn(String login, String password) throws ServiceException {
        User user = findBy(new UserFinder().findByLogin(login)).stream().findAny().get();
        if (user == null) {
            return null;
        }
        if (password.hashCode() == user.getPassword()) {
            return user;
        } else {
            return null;
        }
    }

    private  User register(String firstName,
                         String lastName,
                         String login,
                         int password,
                         String adress,
                         String photoLink, UserStatus status) throws ServiceException {
        if (!findBy(new UserFinder().findByLogin(login)).isEmpty()) {
            return null;
        }

        try {
            User user = new UserFactory().create(
                    firstName, lastName, login, password, adress, photoLink, status);
        } catch (FactoryException e) {
            throw new ServiceException(FACTORY_EXCEPTION,e);
        }
        return create(firstName, lastName, login, password, adress, photoLink, status);
    }

    public User registerBuyer(String firstName,
                            String lastName,
                            String login,
                            int password,
                            String adress,
                            String photoLink) throws ServiceException {
        return register(firstName, lastName, login, password, adress, photoLink, UserStatus.BUYER);
    }

    public User registerWorker(User user, String firstName,
                               String lastName,
                               String login,
                               int password,
                               String adress,
                               String photoLink, UserStatus status) throws ServiceException {
        if ((status != UserStatus.SELLER && (status != UserStatus.COURIER))
                || user.getStatus() != UserStatus.ADMIN) {
            return null;
        }
        return  register(firstName, lastName, login, password, adress, photoLink, status);

    }
}
