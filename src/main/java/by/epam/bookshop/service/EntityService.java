package by.epam.bookshop.service;


import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.pool.ConnectionPool;
import by.epam.bookshop.exceptions.ConnectionPoolException;

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

    public T create(Object ... args) throws ServiceException;

    public T read(int id) throws ServiceException;

    public void update(T t) throws ServiceException;

    public void delete(T t) throws ServiceException;

    Collection<T> findBy(EntityFinder<T> finder) throws ServiceException;

    Collection<T> findAll() throws DAOException, ServiceException;
}
