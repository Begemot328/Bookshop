package by.epam.bookshop.dao.impl.user;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.dao.impl.position_action.PositionActionFinder;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserFactory;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.AddressException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.UnknownEntityException;
import by.epam.bookshop.util.AddressObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MySQLUserDAO extends MySQLEntityDAO<User> {
    private static final String ID = "ID";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String LOGIN = "LOGIN";
    private static final String PASSWORD = "PASSWORD";
    private static final String ADDRESS = "ADDRESS";
    private static final String PHOTO_LINK = "PHOTO_LINK";
    private static final String STATUS = "STATUS";

    private static final String SQL_EXCEPTION = "SQL Exception: ";
    private static final String FACTORY_EXCEPTION = "Factory Exception: ";
    private static final String UNKNOWN_ENTITY_EXCEPTION = "Unknown entity Exception: ";
    private static final String SCHEMA = "BOOKSHOP";
    private static final String TABLE = "USERS";
    
    
    public MySQLUserDAO(Connection connection) {
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
    public UserFinder getFinder() {
        return new UserFinder();
    }

    @Override
    public User read(int id) throws DAOException {
        Collection<User> result = findBy((new UserFinder()).findByID(id));
        if (result == null || result.isEmpty() || result.size() > 1) {
            return null;
        } else {
            return result.stream().toArray(User[]::new)[0];
        }
    }

    public Map<String, Object> mapEntity(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put(FIRST_NAME, user.getFirstName());
        map.put(LAST_NAME, user.getLastName());
        map.put(LOGIN, user.getLogin());
        map.put(PASSWORD, user.getPassword());
        map.put(ADDRESS, user.getAddress());
        map.put(PHOTO_LINK, user.getPhotoLink().toString());
        map.put(STATUS, user.getStatus().getId());
        return map;
    }

    @Override
    public Collection<User> mapToList(ResultSet resultSet) throws DAOException {
        EntityFactory<User> factory = new UserFactory();
        ArrayList<User> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                URL link = (resultSet.getString(PHOTO_LINK) == null || resultSet.getString(PHOTO_LINK).isEmpty()
                        ? null : new URL(resultSet.getString(PHOTO_LINK)));
                result.add(factory.createWithID(resultSet.getInt(ID),
                        resultSet.getString(FIRST_NAME),
                        resultSet.getString(LAST_NAME),
                        resultSet.getString(LOGIN),
                        resultSet.getInt(PASSWORD),
                        new AddressObject(resultSet.getString(ADDRESS)),
                        link,
                        UserStatus.resolveById(resultSet.getInt(STATUS))));
            }
            return result;
        } catch (SQLException | UnknownEntityException | FactoryException | MalformedURLException | AddressException e) {
            throw new DAOException(e);
        }
    }

}
