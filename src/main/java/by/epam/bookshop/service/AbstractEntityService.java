package by.epam.bookshop.service;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.validator.EntityValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public abstract class AbstractEntityService<T extends Entity> implements EntityService<T> {

    public abstract MySQLEntityDAO<T> getDAO(Connection connection);

    public abstract AbstractEntityFactory<T> getFactory();


    public abstract EntityValidator<T> getValidator();

    @Override
    public T create(Object... args) throws ServiceException, ValidationException {
        try (Connection connection = getConnection()) {
            T t = getFactory().create(args);
            getValidator().validate(t);
            getDAO(connection).create(t);
            return t;
        } catch (SQLException | FactoryException | DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public T read(int id) throws ServiceException {
        try (Connection connection = getConnection()) {
            return getDAO(connection).read(id);
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(T t) throws ServiceException, ValidationException {
        getValidator().validate(t);
        try (Connection connection = getConnection()) {
            getDAO(connection).update(t);
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(T t) throws ServiceException {
        try (Connection connection = getConnection()) {
            getDAO(connection).delete(t.getId());
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<T> findBy(EntityFinder<T> finder) throws ServiceException {
        try (Connection connection = getConnection()) {
            return getDAO(connection).findBy(finder);
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<T> findAll() throws ServiceException {
        try (Connection connection = getConnection()) {
            return getDAO(connection).findAll();
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countBy(EntityFinder<T> finder) throws ServiceException {
        try (Connection connection = getConnection()) {
            return getDAO(connection).countBy(finder);
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countAll() throws ServiceException {
        try (Connection connection = getConnection()) {
            return getDAO(connection).countAll();
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<T> findBy(EntityFinder<T> finder, int first, int last) throws ServiceException {
        try (Connection connection = getConnection()) {
            return getDAO(connection).findBy(finder, first, last);
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<T> findAll(int first, int last) throws DAOException, ServiceException {
        try (Connection connection = getConnection()) {
            return getDAO(connection).findAll(first, last);
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }
}
