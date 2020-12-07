package by.epam.bookshop.service;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.dao.impl.pool.ConnectionPool;
import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;

import java.sql.Connection;
import java.util.Collection;

public interface EntityService<T extends Entity> {

    default Connection getConnection() {
        return ConnectionPool.getConnection();
    }

    public EntityService<T> getInstance();

    public T create(Object ... args) throws ServiceException;

    public T read(int id) throws ServiceException;

    public void update(T t) throws ServiceException;

    public void delete(T t) throws ServiceException;

    Collection<T> findBy(EntityFinder<T> finder) throws ServiceException;

    Collection<T> findAll() throws DAOException, ServiceException;
}
