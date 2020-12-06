package by.epam.bookshop.service;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.DAOException;

import java.util.Collection;

public interface EntityService<T extends Entity> {


    public T create(Object ... args);

    public T read();

    public void update(T t);

    public void delete(T t);

    Collection<T> findBy(EntityFinder<T> finder);

    Collection<T> findAll() throws DAOException;
}
