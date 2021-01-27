package by.epam.bookshop.service;


import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.ConnectionPoolException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.pool.ConnectionPool;

import java.sql.Connection;
import java.util.Collection;

public interface EntityService<T extends Entity> {

    default Connection getConnection() throws ServiceException {
        try {
            return ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException e) {
            throw new ServiceException(e);
        }
    }

    T create(Object... args) throws ServiceException, ValidationException;

    T read(int id) throws ServiceException;

    void update(T t) throws ServiceException, ValidationException;

    void delete(T t) throws ServiceException;

    Collection<T> findBy(EntityFinder<T> finder) throws ServiceException;

    Collection<T> findAll() throws DAOException, ServiceException;

    int countBy(EntityFinder<T> finder) throws ServiceException;

    int countAll() throws DAOException, ServiceException;

    Collection<T> findBy(EntityFinder<T> finder, int first, int last) throws ServiceException;

    Collection<T> findAll(int first, int last) throws DAOException, ServiceException;
}
