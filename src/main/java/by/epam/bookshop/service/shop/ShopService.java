package by.epam.bookshop.service.shop;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.shop.MySQLShopDAO;
import by.epam.bookshop.dao.impl.user.MySQLUserDAO;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.shop.ShopFactory;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.service.user.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class ShopService implements EntityService<Shop> {
    private static final String SQL_CONNECTION_EXCEPTION = "SQL Exception: ";
    private static final String DAO_EXCEPTION = "User DAO Exception: ";
    private static final String FACTORY_EXCEPTION = "User factory Exception: ";
    private static final String WRONG_INPUT_EXCEPTION
            = "Wrong data input!";

    private static final ShopService INSTANCE = new ShopService();

    private ShopService() {};

    public static EntityService<Shop> getInstance() {
        return INSTANCE;
    }

    @Override
    public Shop create(Object... args) throws ServiceException {
        try  (Connection connection = getConnection()) {
            Shop shop = new ShopFactory().create(args);
            new MySQLShopDAO(connection).create(shop);
            return shop;
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION,e);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION,e);
        } catch (FactoryException e) {
            throw new ServiceException(FACTORY_EXCEPTION,e);
        }
    }

    @Override
    public Shop read(int id) throws ServiceException {
        try  (Connection connection = getConnection()) {
            return new MySQLShopDAO(connection).read(id);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION,e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION,e);
        }
    }

    @Override
    public void update(Shop shop) throws ServiceException {
        try  (Connection connection = getConnection()) {
            new MySQLShopDAO(connection).update(shop);
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION,e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION,e);
        }
    }

    @Override
    public void delete(Shop shop) throws ServiceException {
        try  (Connection connection = getConnection()) {
            new MySQLShopDAO(connection).delete(shop.getId());
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION,e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION,e);
        }
    }

    @Override
    public Collection<Shop> findBy(EntityFinder<Shop> finder) throws ServiceException {
        try  (Connection connection = getConnection()) {
            return new MySQLShopDAO(connection).findAll();
        } catch (DAOException e) {
            throw new ServiceException(DAO_EXCEPTION,e);
        } catch (SQLException e) {
            throw new ServiceException(SQL_CONNECTION_EXCEPTION,e);
        }
    }

    @Override
    public Collection<Shop> findAll() throws DAOException, ServiceException {
        return null;
    }
}
