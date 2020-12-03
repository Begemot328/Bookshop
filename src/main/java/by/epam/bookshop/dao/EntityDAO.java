package by.epam.bookshop.dao;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.FactoryException;

import java.sql.SQLException;
import java.util.Collection;

public interface EntityDAO<T extends Entity> {

    boolean create(T t) throws SQLException;

    T read(int id) throws FactoryException, SQLException;

    boolean update(T t) throws SQLException;

    boolean delete(int id) throws SQLException;

    Collection<T> findAll() throws SQLException, FactoryException;

    T findById(int id) throws SQLException, FactoryException;

    Collection<T> findBy(EntityFinder<T> finder) throws SQLException, FactoryException;
}
