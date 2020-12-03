package by.epam.bookshop.dao;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;

import java.sql.SQLException;
import java.util.Collection;

public interface EntityDAO<T extends Entity> {

    boolean create(T t) throws DAOException;

    T read(int id) throws DAOException;

    void update(T t) throws DAOException;

    void delete(int id) throws DAOException;

    Collection<T> findAll() throws DAOException;

    Collection<T> findBy(EntityFinder<T> finder) throws DAOException;
}
