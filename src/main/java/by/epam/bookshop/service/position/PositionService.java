package by.epam.bookshop.service.position;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.EntityService;

import java.util.Collection;

public class PositionService implements EntityService<Position> {

    @Override
    public EntityService<Position> getInstance() {
        return null;
    }

    @Override
    public Position create(Object... args) throws ServiceException {
        return null;
    }

    @Override
    public Position read(int i) throws ServiceException {
        return null;
    }

    @Override
    public void update(Position position) throws ServiceException {

    }

    @Override
    public void delete(Position position) throws ServiceException {

    }

    @Override
    public Collection<Position> findBy(EntityFinder<Position> finder) throws ServiceException {
        return null;
    }

    @Override
    public Collection<Position> findAll() throws DAOException, ServiceException {
        return null;
    }
}
