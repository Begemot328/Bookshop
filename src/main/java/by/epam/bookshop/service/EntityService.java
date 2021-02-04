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

/**
 * Entity service interface.
 *
 * @author Yury Zmushko
 * @version 1.0
 */
public interface EntityService<T extends Entity> {

    /**
     * Getting connection from connection pool method
     *
     * @throws ServiceException when {@link ConnectionPoolException} spotted
     * @return {@link Connection} object
     */
    default Connection getConnection() throws ServiceException {
        try {
            return ConnectionPool.getInstance().getConnection();
        } catch (ConnectionPoolException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Create method
     *
     * @param args entity parameters to create
     *
     * @throws ServiceException
     * @throws ValidationException when input data is incorrect
     *
     */
    T create(Object... args) throws ServiceException, ValidationException;


    /**
     * Read method
     *
     * @param id of the entity
     *
     * @throws ServiceException when {@link Exception} spotted
     */
    T read(int id) throws ServiceException;

    /**
     * Update method
     *
     * @param t entity to create
     *
     * @throws ServiceException
     * @throws ValidationException when input data is incorrect
     */
    void update(T t) throws ServiceException, ValidationException;

    /**
     * Delete method
     *
     * @param t entity to delete
     *
     * @throws ServiceException
     */
    void delete(T t) throws ServiceException;

    /**
     * Find by criteria method
     *
     * @param finder criteria to find
     * @throws ServiceException
     */
    Collection<T> findBy(EntityFinder<T> finder) throws ServiceException;

    /**
     * Find all objects method
     *
     * @throws ServiceException
     */
    Collection<T> findAll() throws ServiceException;

    /**
     * Find  a quantity of objects from offset by criteria method
     *
     * @param finder criteria to find
     * @throws ServiceException
     */
    int countBy(EntityFinder<T> finder) throws ServiceException;

    /**
     * Count all objects method
     *
     * @throws ServiceException
     */
    int countAll() throws ServiceException;

    /**
     * Find  a quantity of objects from offset by criteria method
     *
     * @param first quantity of elements to skip
     * @param last quantity of elements to return
     * @param finder criteria to find
     * @throws ServiceException
     */
    Collection<T> findBy(EntityFinder<T> finder, int first, int last) throws ServiceException;

    /**
     * Find all method
     *
     * @param first quantity of elements to skip
     * @param last quantity of elements to return
     * @throws DAOException
     */
    Collection<T> findAll(int first, int last) throws DAOException, ServiceException;
}
