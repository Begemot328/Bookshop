package by.epam.bookshop.service.position;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.position_action.MySQLPositionActionDAO;
import by.epam.bookshop.dao.impl.position.MySQLPositionDAO;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position_action.PositionAction;
import by.epam.bookshop.entity.position_action.PositionActionFactory;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionFactory;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.EntityService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

public class PositionService implements EntityService<Position> {
    private static final String SQL_CONNECTION_EXCEPTION = "SQL Exception: ";
    private static final String DAO_EXCEPTION = "User DAO Exception: ";
    private static final String FACTORY_EXCEPTION = "User factory Exception: ";
    private static final String NO_RIGHTS_EXCEPTION
            = "Only admin or seller can manage books";
    private static final String WRONG_INPUT_EXCEPTION
            = "Wrong data input!";
    private final static PositionService INSTANCE = new PositionService();


    public static EntityService<Position> getInstance() {
        return INSTANCE;
    }


    public Position create(Object... args) throws ServiceException {
        if (args[0] instanceof User
        && args[1] instanceof Book
        && args[2] instanceof Shop
        && args[3] instanceof String
        && args[4] instanceof Integer
                && ((Integer) args[4]) > 0) {
            return createPosition(
                    (User) args[0],
                    (Book) args[1],
                    (Shop) args[2],
                    (String) args[3],
            (Integer) args[4]);
        } else {
            throw new ServiceException(WRONG_INPUT_EXCEPTION);
        }
    }

    public Position transferPosition(Position position, Shop destination)
            throws ServiceException {
        position.setShop(destination);
        update(position);
        return position;
    }

    public Position splitPosition(Position position, Integer quantity,
                                  User user, User librarian, String note, PositionStatus status)
            throws ServiceException {
        if (quantity > position.getQuantity()) {
            throw new ServiceException(WRONG_INPUT_EXCEPTION);
        }

        try (Connection connection = getConnection()) {
            try {

                Position newPosition = new PositionFactory().create(
                        position.getBook(),
                        position.getShop(),
                        status,
                        note,
                        quantity);
                position.setQuantity(position.getQuantity() - quantity);

                connection.setAutoCommit(false);
                MySQLPositionDAO positionDAO = new MySQLPositionDAO(connection);
                positionDAO.create(newPosition);
                positionDAO.update(position);
                MySQLPositionActionDAO positionActionDAO = new MySQLPositionActionDAO(connection);
                positionActionDAO.create (new PositionActionFactory().create(
                        position, newPosition, user, librarian, LocalDateTime.now(),
                        quantity, PositionStatus.READY, status,
                        newPosition.getShop(), newPosition.getBook().getPrice()));
                connection.commit();
                connection.setAutoCommit(true);
                return newPosition;
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
            } catch (DAOException e) {
                throw new ServiceException(DAO_EXCEPTION, e);
            } catch (FactoryException e) {
                throw new ServiceException(FACTORY_EXCEPTION, e);
            }
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }

    public Position createPosition(User user, Book book,
                           Shop shop,
                           String note,
                           int quantity) throws ServiceException {
        if (user.getStatus() != UserStatus.SELLER
        || user.getStatus() != UserStatus.ADMIN ) {
            throw new ServiceException(NO_RIGHTS_EXCEPTION);
        }
        try (Connection connection = getConnection()) {
            try {

                Position position = new PositionFactory().create(
                        book, shop, PositionStatus.READY, note, quantity);
                PositionAction action = new PositionActionFactory().create(
                        book, null, user, LocalDateTime.now(), quantity,
                        PositionStatus.NON_EXISTENT,
                        PositionStatus.READY,
                        shop, book.getPrice());
                connection.setAutoCommit(false);
                new MySQLPositionDAO(connection).create(position);
                new MySQLPositionActionDAO(connection).create(action);
                connection.commit();
                connection.setAutoCommit(true);
                return position;
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
            } catch (DAOException e) {
                throw new ServiceException(DAO_EXCEPTION, e);
            } catch (FactoryException e) {
                throw new ServiceException(FACTORY_EXCEPTION, e);
            }
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }

    @Override
    public Position read(int id) throws ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLPositionDAO(connection).read(id);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }

    @Override
    public void update(Position position) throws ServiceException {
        try (Connection connection = getConnection()) {
            new MySQLPositionDAO(connection).update(position);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }

    @Override
    public void delete(Position position) throws ServiceException {
        try (Connection connection = getConnection()) {
            new MySQLPositionDAO(connection).delete(position.getId());
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }

    @Override
    public Collection<Position> findBy(EntityFinder<Position> finder) throws ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLPositionDAO(connection).findBy(finder);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }

    @Override
    public Collection<Position> findAll() throws ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLPositionDAO(connection).findAll();
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
    }
}
