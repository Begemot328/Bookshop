package by.epam.bookshop.service.position;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.book_action.MySQLBookActionDAO;
import by.epam.bookshop.dao.impl.position.MySQLPositionDAO;
import by.epam.bookshop.entity.book_action.BookAction;
import by.epam.bookshop.entity.book_action.BookActionFactory;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionFactory;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.EntityService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class PositionService implements EntityService<Position> {
    private static final String SQL_CONNECTION_EXCEPTION = "SQL Exception: ";
    private static final String DAO_EXCEPTION = "User DAO Exception: ";
    private static final String FACTORY_EXCEPTION = "User factory Exception: ";

    @Override
    public EntityService<Position> getInstance() {
        return null;
    }


    public Position create(Object... args) throws ServiceException {
        try (Connection connection = getConnection()) {
            Position position = new PositionFactory().create(args);
            new MySQLPositionDAO(connection).create(position);
            BookAction action = new BookActionFactory().create(args);
            new MySQLBookActionDAO(connection).create(action);

            return position;
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION, e);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION, e);
        } catch (FactoryException e) {
            throw new ServiceException(FACTORY_EXCEPTION, e);
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