package by.epam.bookshop.service.shop;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.dao.impl.shop.MySQLShopDAO;
import by.epam.bookshop.dao.impl.user.MySQLUserDAO;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.shop.ShopFactory;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.AbstractEntityService;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.service.user.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class ShopService extends AbstractEntityService<Shop> {
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

    public MySQLShopDAO getDAO(Connection connection) {
        return new MySQLShopDAO(connection);
    }

    public ShopFactory getFactory() {
        return new ShopFactory();
    }
}
