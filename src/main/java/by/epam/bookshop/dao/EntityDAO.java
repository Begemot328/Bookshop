package by.epam.bookshop.dao;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.DAOException;

import java.util.Collection;

public interface EntityDAO<T extends Entity> {

    /**
     * Create method
     *
     * @param t entity to create
     *
     * @throws DAOException
     */
    boolean create(T t) throws DAOException;

    /**
     * Read method
     *
     * @param id of the entity
     *
     * @throws DAOException
     */
    T read(int id) throws DAOException;

    /**
     * Update method
     *
     * @param t entity to create
     *
     * @throws DAOException
     */
    void update(T t) throws DAOException;

    /**
     * Delete method
     *
     * @param id of the entity to delete
     *
     * @throws DAOException
     */
    void delete(int id) throws DAOException;

    /**
     * Find all method
     *
     * @throws DAOException
     */
    Collection<T> findAll() throws DAOException;

    /**
     * Find all method
     *
     * @param offset quantity of elements to skip
     * @param quantity quantity of elements to return
     * @throws DAOException
     */
    Collection<T> findAll(int offset, int quantity) throws DAOException;

    /**
     * Find by criteria method
     *
     * @param finder criteria to find
     * @throws DAOException
     */
    Collection<T> findBy(EntityFinder<T> finder) throws DAOException;

    /**
     * Find  a quantity of objects from offset by criteria method
     *
     * @param offset quantity of elements to skip
     * @param quantity quantity of elements to return
     * @param finder criteria to find
     * @throws DAOException
     */
    Collection<T> findBy(EntityFinder<T> finder, int offset, int quantity) throws DAOException;

    int countAll() throws DAOException;

    /**
     * Count by criteria method
     *
     * @param finder criteria to find
     * @throws DAOException
     */
    int countBy(EntityFinder<T> finder) throws DAOException;

    String getTableName();

    String getSchemaName();

}
