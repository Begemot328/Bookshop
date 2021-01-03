package by.epam.bookshop.service.position_action;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.dao.impl.position.MySQLPositionDAO;
import by.epam.bookshop.dao.impl.position_action.MySQLPositionActionDAO;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.entity.position_action.PositionAction;
import by.epam.bookshop.entity.position_action.PositionActionFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.service.position.PositionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class PositionActionService implements EntityService<PositionAction> {

    private static final String SQL_CONNECTION_EXCEPTION = "SQL Exception: ";
    private static final String DAO_EXCEPTION = "DAO Exception: ";
    private static final String FACTORY_EXCEPTION = "User factory Exception: ";
    private static final String NO_RIGHTS_EXCEPTION
            = "Only admin or seller can manage books";
    private static final String WRONG_INPUT_EXCEPTION
            = "Wrong data input!";
    private final static PositionActionService INSTANCE = new PositionActionService();

    public static PositionActionService getInstance() {
        return INSTANCE;
    }

    @Override
    public PositionAction create(Object... args) throws ServiceException {
        try (Connection connection = getConnection()) {
            PositionAction positionAction = new PositionActionFactory().create(args);
            new MySQLPositionActionDAO(connection).create(positionAction);
            return positionAction;
        } catch (SQLException | FactoryException | DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PositionAction read(int id) throws ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLPositionActionDAO(connection).read(id);
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(PositionAction positionAction) throws ServiceException {
        try (Connection connection = getConnection()) {
            new MySQLPositionActionDAO(connection).update(positionAction);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(PositionAction positionAction) throws ServiceException {
        try (Connection connection = getConnection()) {
            new MySQLPositionActionDAO(connection).delete(positionAction.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<PositionAction> findBy(EntityFinder<PositionAction> finder) throws ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLPositionActionDAO(connection).findBy(finder);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<PositionAction> findAll() throws DAOException, ServiceException {
        try (Connection connection = getConnection()) {
            return new MySQLPositionActionDAO(connection).findAll();
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }
}
