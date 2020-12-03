package by.epam.bookshop.dao;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.FactoryException;

import java.sql.SQLException;
import java.util.Collection;

public interface EntityDAO<T extends Entity> {

    boolean create(T t);

    T read(int id);

    T update(T t);

    boolean delete(int id);

    Collection<T> findAll() throws SQLException, FactoryException;

    T findById(int id) throws SQLException, FactoryException;

    Collection<T> findBy(EntityFinder<T> finder) throws SQLException, FactoryException;
}
