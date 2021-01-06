package by.epam.bookshop.service.user;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.dao.impl.user.MySQLUserDAO;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserFactory;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.AbstractEntityService;
import by.epam.bookshop.service.EntityService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

public class UserService extends AbstractEntityService<User> {
    private static final String SQL_CONNECTION_EXCEPTION = "SQL Exception: ";
    private static final String DAO_EXCEPTION = "User DAO Exception: ";
    private static final String FACTORY_EXCEPTION = "User factory Exception: ";
    private static final String WRONG_INPUT_EXCEPTION
            = "Wrong data input!";

    private static final UserService INSTANCE = new UserService();

    private UserService() {};


    public static EntityService<User> getInstance() {
        return INSTANCE;
    }

    public MySQLUserDAO getDAO(Connection connection) {
        return new MySQLUserDAO(connection);
    }

    public UserFactory getFactory() {
        return new UserFactory();
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
            throw new ServiceException(WRONG_INPUT_EXCEPTION);
        }

        try {
            User user = new UserFactory().create(
                    firstName, lastName, login, password, adress, photoLink, status);
        } catch (FactoryException e) {
            throw new ServiceException(e);
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
