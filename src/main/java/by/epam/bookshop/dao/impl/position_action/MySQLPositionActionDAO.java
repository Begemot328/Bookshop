package by.epam.bookshop.dao.impl.position_action;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.dao.impl.position.MySQLPositionDAO;
import by.epam.bookshop.dao.impl.shop.MySQLShopDAO;
import by.epam.bookshop.dao.impl.user.MySQLUserDAO;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.position_action.PositionAction;
import by.epam.bookshop.entity.position_action.PositionActionFactory;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.UnknownEntityException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MySQLPositionActionDAO extends MySQLEntityDAO<PositionAction> {
    private static final String ID = "ID";
    private static final String BOOK_ID = "BOOK_ID";
    private static final String INITIAL_POSITION_ID = "INITIAL_POSITION_ID";
    private static final String FINAL_POSITION_ID = "FINAL_POSITION_ID";
    private static final String BUYER_ID = "BUYER_ID";
    private static final String SELLER_ID = "SELLER_ID";
    private static final String SHOP_ID = "SHOP_ID";
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
    private static final String TABLE = "POSITION_ACTIONS";
    
    
    public MySQLPositionActionDAO(Connection connection) {
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
    public PositionAction read(int id) throws DAOException {
        Collection<PositionAction> result = findBy((new PositionActionFinder()).findByID(id));
        if (result == null || result.isEmpty() || result.size() > 1) {
            return null;
        } else {
            return result.stream().toArray(PositionAction[]::new)[0];
        }
    }

    public Map<String, Object> mapEntity(PositionAction positionAction) {
        Map<String, Object> map = new HashMap<>();
        if (positionAction.getInitialPosition() != null) {
            map.put(INITIAL_POSITION_ID, positionAction.getInitialPosition().getId());
        }
        map.put(FINAL_POSITION_ID, positionAction.getFinalPosition().getId());
        if (positionAction.getBuyer() != null) {
            map.put(BUYER_ID, positionAction.getBuyer().getId());
        }
        if (positionAction.getSeller() != null) {
            map.put(SELLER_ID, positionAction.getSeller().getId());
        }
        map.put(SHOP_ID, positionAction.getShop().getId());
        map.put(INITIAL_STATUS, positionAction.getInitialStatus().getId());
        map.put(FINAL_STATUS, positionAction.getFinalStatus().getId());
        map.put(QUANTITY, positionAction.getQuantity());
        map.put(DATE_TIME, Timestamp.valueOf(positionAction.getDate()));
        map.put(CURRENT_PRICE, positionAction.getCurrentPrice());
        return map;
    }

    @Override
    public Collection<PositionAction> mapToList(ResultSet resultSet) throws DAOException {
        EntityFactory<PositionAction> factory = new PositionActionFactory();
        ArrayList<PositionAction> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(factory.createWithID(resultSet.getInt(ID),
                        new MySQLPositionDAO(connection).read(resultSet.getInt(INITIAL_POSITION_ID)),
                        new MySQLPositionDAO(connection).read(resultSet.getInt(FINAL_POSITION_ID)),
                        new MySQLUserDAO(connection).read(resultSet.getInt(BUYER_ID)),
                        new MySQLUserDAO(connection).read(resultSet.getInt(SELLER_ID)),
                        resultSet.getTimestamp(DATE_TIME).toLocalDateTime(),
                        resultSet.getInt(QUANTITY),
                        PositionStatus.resolveById(resultSet.getInt(INITIAL_STATUS)),
                        PositionStatus.resolveById(resultSet.getInt(FINAL_STATUS)),
                        new MySQLShopDAO(connection).read(resultSet.getInt(SHOP_ID)),
                        resultSet.getFloat(CURRENT_PRICE)));
            }
            return result;
        } catch (SQLException | FactoryException | UnknownEntityException e) {
            throw new DAOException(e);
        }
    }

}
