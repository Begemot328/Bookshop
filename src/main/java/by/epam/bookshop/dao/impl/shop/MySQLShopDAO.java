package by.epam.bookshop.dao.impl.shop;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.dao.impl.position_action.PositionActionFinder;
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
    public String getTableName() {
        return TABLE;
    }

    @Override
    public String getSchemaName() {
        return SCHEMA;
    }

    @Override
    public ShopFinder getFinder() {
        return new ShopFinder();
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
