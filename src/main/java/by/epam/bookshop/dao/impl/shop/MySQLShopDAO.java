package by.epam.bookshop.dao.impl.shop;

import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.shop.ShopFactory;
import by.epam.bookshop.exceptions.AddressException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.util.AddressObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MySQLShopDAO extends MySQLEntityDAO<Shop> {
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String ADDRESS = "ADDRESS";
    private static final String POSITION = "POSITION";

    private static final String PHOTO_LINK = "PHOTO_LINK";
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
        map.put(ADDRESS, shop.getAddress().getFormattedAddress());
        map.put(PHOTO_LINK, shop.getPhotoLink().toString());
        return map;
    }

    @Override
    public Collection<Shop> mapToList(ResultSet resultSet) throws DAOException {
        EntityFactory<Shop> factory = new ShopFactory();
        ArrayList<Shop> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                URL link = (resultSet.getString(PHOTO_LINK) == null || resultSet.getString(PHOTO_LINK).isEmpty()
                        ? null : new URL(resultSet.getString(PHOTO_LINK)));
                result.add(factory.createWithID(resultSet.getInt(ID),
                        resultSet.getString(NAME),
                        new AddressObject(resultSet.getString(ADDRESS)),
                        link));
            }
            return result;
        } catch (SQLException | MalformedURLException | AddressException | FactoryException e) {
            throw new DAOException(e);
        }
    }
}
