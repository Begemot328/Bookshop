package by.epam.bookshop.dao.impl.book_action;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.dao.impl.shop.MySQLShopDAO;
import by.epam.bookshop.dao.impl.user.MySQLUserDAO;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.book_action.BookAction;
import by.epam.bookshop.entity.book_action.BookActionFactory;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MySQLBookActionDAO extends MySQLEntityDAO<BookAction> {
    private static final String ID = "ID";
    private static final String BOOK_ID = "BOOK_ID";
    private static final String BUYER_ID = "BUYER_ID";
    private static final String SELLER_ID = "SELLER_ID";
    private static final String SHOP_ID = "SELLER_ID";
    private static final String QUANTITY = "QUANTITY";
    private static final String INITIAL_STATUS = "INITIAL_STATUS";
    private static final String FINAL_STATUS = "FINAL_STATUS";
    private static final String DATE_TIME = "DATE_TIME";
    private static final String CURRENT_PRICE = "CURRENT_PRICE";

    private static final String TIMESTAMP_FORMAT = "yyyy-mm-dd hh:mm:ss";
    private static final String SQL_EXCEPTION = "SQL Exception: ";
    private static final String FACTORY_EXCEPTION = "Factory Exception: ";
    private static final String UNKNOWN_ENTITY_EXCEPTION = "Unknown entity Exception: ";
    private static final String SCHEMA = "BOOKSHOP";
    private static final String TABLE = "BOOK_ACTIONS";
    
    
    public MySQLBookActionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(BookAction bookAction) throws DAOException {
        return create(bookAction, SCHEMA, TABLE, mapEntity(bookAction));
    }

    @Override
    public BookAction read(int id) throws DAOException {
        Collection<BookAction> result = findBy((new BookActionFinder()).findByID(id));
        if (result == null || result.isEmpty() || result.size() > 1) {
            return null;
        } else {
            return result.stream().toArray(BookAction[]::new)[0];
        }
    }

    @Override
    public void update(BookAction bookAction) throws DAOException {
        update(bookAction, SCHEMA, TABLE, mapEntity(bookAction));
    }

    @Override
    public void delete(int id) throws DAOException {
        delete(id, SCHEMA, TABLE);
    }

    @Override
    public Collection findAll() throws DAOException {
        return findBy(new BookActionFinder());
    }

    @Override
    public Collection<BookAction> findBy(EntityFinder<BookAction> finder) throws DAOException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(finder.getQuery())) {
                return mapToList(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + e.getLocalizedMessage());
        }
    }

    public Map<String, Object> mapEntity(BookAction bookAction) {
        Map<String, Object> map = new HashMap<>();
        map.put(BOOK_ID, bookAction.getBook().getId());
        map.put(BUYER_ID, bookAction.getBuyer().getId());
        map.put(SELLER_ID, bookAction.getSeller());
        map.put(SHOP_ID, bookAction.getShop().getId());
        map.put(INITIAL_STATUS, bookAction.getInitialStatus());
        map.put(FINAL_STATUS, bookAction.getFinalStatus());
        map.put(QUANTITY, bookAction.getQuantity());
        map.put(DATE_TIME, Timestamp.valueOf(bookAction.getDate()));
        map.put(CURRENT_PRICE, bookAction.getCurrentPrice());
        return map;
    }

    @Override
    public Collection<BookAction> mapToList(ResultSet resultSet) throws DAOException {
        EntityFactory<BookAction> factory = new BookActionFactory();
        ArrayList<BookAction> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(factory.createWithID(resultSet.getInt(ID),
                        new MySQLBookDAO(connection).read(resultSet.getInt(BOOK_ID)),
                        new MySQLUserDAO(connection).read(resultSet.getInt(BUYER_ID)),
                        new MySQLUserDAO(connection).read(resultSet.getInt(SELLER_ID)),
                        resultSet.getTimestamp(DATE_TIME).toLocalDateTime(),
                        resultSet.getInt(QUANTITY),
                        PositionStatus.valueOf(Integer.toString(resultSet.getInt(INITIAL_STATUS))),
                        PositionStatus.valueOf(Integer.toString(resultSet.getInt(FINAL_STATUS))),
                        new MySQLShopDAO(connection).read(resultSet.getInt(SHOP_ID)),
                        resultSet.getFloat(CURRENT_PRICE)));
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + e.getLocalizedMessage());
        } catch (FactoryException e) {
            throw new DAOException(FACTORY_EXCEPTION, e);
        }
    }

}
