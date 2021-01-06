package by.epam.bookshop.dao.impl.position;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.dao.impl.author.MySQLAuthorDAO;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.dao.impl.position.PositionFinder;
import by.epam.bookshop.dao.impl.shop.MySQLShopDAO;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionFactory;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.UnknownEntityException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MySQLPositionDAO extends MySQLEntityDAO<Position> {
    private static final String ID = "ID";
    private static final String BOOK_ID = "BOOK_ID";
    private static final String SHOP_ID = "SHOP_ID";
    private static final String POSITION_STATUS = "STATUS";
    private static final String NOTE = "NOTE";
    private static final String QUANTITY = "QUANTITY";

    private static final String SQL_EXCEPTION = "SQL Exception: ";
    private static final String FACTORY_EXCEPTION = "Factory Exception: ";
    private static final String UNKNOWN_ENTITY_EXCEPTION = "Unknown entity Exception: ";
    private static final String SCHEMA = "BOOKSHOP";
    private static final String TABLE = "POSITIONS";
    
    
    public MySQLPositionDAO(Connection connection) {
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
    public Position read(int id) throws DAOException {
        Collection<Position> result = findBy((new PositionFinder()).findByID(id));
        if (result == null || result.isEmpty() || result.size() > 1) {
            return null;
        } else {
            return result.stream().toArray(Position[]::new)[0];
        }
    }

    public Map<String, Object> mapEntity(Position position) {
        Map<String, Object> map = new HashMap<>();
        map.put(BOOK_ID, position.getBook().getId());
        map.put(SHOP_ID, position.getShop().getId());
        map.put(POSITION_STATUS, position.getStatus().getId());
        map.put(NOTE, position.getNote());
        map.put(QUANTITY, position.getQuantity());
        return map;
    }

    @Override
    public Collection<Position> mapToList(ResultSet resultSet) throws DAOException {
        EntityFactory<Position> factory = new PositionFactory();
        ArrayList<Position> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(factory.createWithID(resultSet.getInt(ID),
                        new MySQLBookDAO(connection).read(resultSet.getInt(BOOK_ID)),
                        new MySQLShopDAO(connection).read(resultSet.getInt(SHOP_ID)),
                        PositionStatus.resolveById(resultSet.getInt(POSITION_STATUS)),
                        resultSet.getString(NOTE),
                        Integer.valueOf(resultSet.getInt(QUANTITY))));
            }
            return result;
        } catch (SQLException | FactoryException | UnknownEntityException e) {
            throw new DAOException(e);
        }
    }

}
