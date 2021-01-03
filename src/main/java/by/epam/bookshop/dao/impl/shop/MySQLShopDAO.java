package by.epam.bookshop.dao.impl.shop;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.shop.ShopFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MySQLShopDAO extends MySQLEntityDAO<Shop> {
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String ADDRESS = "ADDRESS";
    private static final String POSITION = "POSITION";

    private static final String SQL_EXCEPTION = "SQL Exception: ";
    private static final String FACTORY_EXCEPTION = "Factory Exception: ";
    private static final String SCHEMA = "BOOKSHOP";
    private static final String TABLE = "SHOPS";


    public MySQLShopDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Shop shop) throws DAOException {
        return create(shop, SCHEMA, TABLE, mapEntity(shop));
    }

    @Override
    public Shop read(int id) throws DAOException {
        Collection<Shop> result = findBy((new ShopFinder()).findByID(id));
        if (result == null || result.isEmpty() || result.size() > 1) {
            return null;
        } else {
            return result.stream().toArray(Shop[]::new)[0];
        }
    }

    @Override
    public void update(Shop shop) throws DAOException {
        update(shop, SCHEMA, TABLE, mapEntity(shop));
    }

    @Override
    public void delete(int id) throws DAOException {
        delete(id, SCHEMA, TABLE);
    }

    @Override
    public Collection findAll() throws DAOException {
        return findBy(new ShopFinder());
    }

    @Override
    public Collection<Shop> findBy(EntityFinder<Shop> finder) throws DAOException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(finder.getQuery())) {
                return mapToList(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + e.getLocalizedMessage());
        }
    }

    public Map<String, Object> mapEntity(Shop shop) {
        Map<String, Object> map = new HashMap<>();
        map.put(NAME, shop.getName());
        map.put(ADDRESS, shop.getAddress());
        map.put(POSITION, shop.getPosition());
        return map;
    }

    @Override
    public Collection<Shop> mapToList(ResultSet resultSet) throws DAOException {
        EntityFactory<Shop> factory = new ShopFactory();
        ArrayList<Shop> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(factory.createWithID(resultSet.getInt(ID),
                        resultSet.getString(NAME),
                        resultSet.getString(ADDRESS),
                        resultSet.getString(POSITION)));
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (FactoryException e) {
            throw new DAOException(e);
        }
    }

}
