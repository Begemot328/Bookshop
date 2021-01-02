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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class PositionService implements EntityService<Position> {
    private static final String SQL_CONNECTION_EXCEPTION = "SQL Connection Exception: ";
    private static final String SQL_EXCEPTION = "SQL Connection Exception: ";
    private static final String DAO_EXCEPTION = "DAO Exception: ";
    private static final String FACTORY_EXCEPTION = "Position factory Exception: ";
    private static final String NO_RIGHTS_EXCEPTION
            = "Only admin or seller can manage positions";
    private static final String WRONG_INPUT_EXCEPTION
            = "Wrong data input!";
    private final static PositionService INSTANCE = new PositionService();


    public static PositionService getInstance() {
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

    public boolean isSimilar(Position p1, Position p2) {
        boolean result = p1 != null && p2 != null
                && p1.getBook().equals(p2.getBook())
                && p1.getShop().equals(p2.getShop())
                && p1.getStatus().equals(p2.getStatus())
                && (p1.getNote() == p2.getNote() || p1.getNote() != null && p1.getNote().equals(p2.getNote()));
        return result;
    }

    public Position mergePositions(Position position, Position... args) throws FactoryException {
        int quantity = position.getQuantity();

        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                quantity += args[i].getQuantity();
            }
        }
        Position newPosition = clonePosition(position);
        newPosition.setQuantity(quantity);
        return newPosition;
    }

    private Position clonePosition(Position position) throws FactoryException {
        return new PositionFactory().createWithID(position.getId(), position.getBook(), position.getShop(),
                position.getStatus(), position.getNote(), position.getQuantity());
    }

    public Collection<Position> optimizePositions(Collection<Position> collection, User admin) throws ServiceException {
        Position[] newCollection = collection.toArray(Position[]::new);

        try (Connection connection = getConnection()) {
            try {
                connection.setAutoCommit(false);
                MySQLPositionDAO positionDAO = new MySQLPositionDAO(connection);
                MySQLPositionActionDAO positionActionDAO = new MySQLPositionActionDAO(connection);

                for (int i = 0; i < newCollection.length; i++) {
                    if (newCollection[i] == null || ((newCollection[i].getStatus() != PositionStatus.SOLD
                            && newCollection[i].getStatus() != PositionStatus.READY))) {
                        continue;
                    }
                    Position[] temp = new Position[(newCollection.length - 1)];
                    int k = 0;

                    for (int j = i + 1; j < newCollection.length; j++) {
                        if (isSimilar(newCollection[i], newCollection[j]))    {
                            temp[k] = newCollection[j];
                            newCollection[j] = null;
                            k++;
                        }
                    }
                    if (k != 0) {
                        Position tempPosition = clonePosition(newCollection[i]);
                        newCollection[i] = mergePositions(newCollection[i], temp);
                        positionDAO.create(newCollection[i]);
                        positionActionDAO.create(new PositionActionFactory().create(
                                tempPosition, newCollection[i], null, admin, LocalDateTime.now(),
                                tempPosition.getQuantity(),
                                tempPosition.getStatus(),
                                tempPosition.getStatus(),
                                tempPosition.getShop(),
                                tempPosition.getBook().getPrice()));
                        tempPosition.setStatus(PositionStatus.NON_EXISTENT);
                        positionDAO.update(tempPosition);

                        for (Position position : temp) {
                            if (position != null) {
                                positionActionDAO.create(new PositionActionFactory().create(
                                        position, newCollection[i], null, admin, LocalDateTime.now(),
                                        position.getQuantity(), position.getStatus(), position.getStatus(),
                                        position.getShop(), position.getBook().getPrice()));
                                position.setStatus(PositionStatus.NON_EXISTENT);
                                positionDAO.update(position);
                            }
                        }
                    } else if (newCollection[i].getQuantity() == 0) {
                        newCollection[i].setStatus(PositionStatus.NON_EXISTENT);
                        positionDAO.update(newCollection[i]);

                        positionActionDAO.create(new PositionActionFactory().create(
                                newCollection[i], newCollection[i], null, admin,
                                LocalDateTime.now(),
                                newCollection[i].getQuantity(),
                                newCollection[i].getStatus(),
                                PositionStatus.NON_EXISTENT,
                                newCollection[i].getShop(),
                                newCollection[i].getBook().getPrice()));
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new ServiceException(SQL_EXCEPTION, e);
            } catch (DAOException e) {
                connection.rollback();
                throw new ServiceException(DAO_EXCEPTION, e);
            } catch (FactoryException e) {
                connection.rollback();
                throw new ServiceException(FACTORY_EXCEPTION, e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        }
        return Arrays.asList(newCollection);
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
                positionActionDAO.create(new PositionActionFactory().create(
                        position, newPosition, user, librarian, LocalDateTime.now(),
                        quantity, PositionStatus.READY, status,
                        newPosition.getShop(), newPosition.getBook().getPrice()));
                connection.commit();
                return newPosition;
            } catch (SQLException e) {
                connection.rollback();
                throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
            } catch (DAOException e) {
                connection.rollback();
                throw new ServiceException(DAO_EXCEPTION, e);
            } catch (FactoryException e) {
                connection.rollback();
                throw new ServiceException(FACTORY_EXCEPTION, e);
            } finally {
                connection.setAutoCommit(true);
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
                && user.getStatus() != UserStatus.ADMIN) {
            throw new ServiceException(NO_RIGHTS_EXCEPTION);
        }
        try (Connection connection = getConnection()) {
            try {

                Position position = new PositionFactory().create(
                        book, shop, PositionStatus.READY, note, quantity);
                PositionAction action = new PositionActionFactory().create(
                        null, position, null,  user, LocalDateTime.now(), quantity,
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
