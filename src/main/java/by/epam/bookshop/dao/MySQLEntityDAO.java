package by.epam.bookshop.dao;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.DAOException;

import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class MySQLEntityDAO<T extends Entity> implements EntityDAO {
    private static final String INSERT_QUERY =
            "INSERT INTO [SCHEMA].[TABLE]([PARAMETERS]) VALUES ([VALUES]);";
    private static final String SCHEMA = "[SCHEMA]";
    private static final String TABLE = "[TABLE]";
    private static final String PARAMETERS = "[PARAMETERS]";
    private static final String VALUES = "[VALUES]";
    private static final String DELIMETER = ", ";
    private static final String SQL_EXCEPTION = "SQL Exception: ";


    private static final String WRONG_INPUT = "Wrong data, no null or empty " +
            "strings, valuea are String or Number only!";
    private  Connection connection;

    public MySQLEntityDAO (Connection connection) {
        this.connection = connection;
    }

    public abstract Collection<T> mapToList(ResultSet resultSet);

    public abstract T mapToObject(ResultSet resultSet);

    private String getInsertQuery(String schemaName, String tableName, Map<String, Object> map) throws SQLException {
        String result = INSERT_QUERY.replace(SCHEMA, schemaName).replace(TABLE, tableName);
        String values = new String();
        String parameters = new String();
        Set<String> keySet = map.keySet();

        for (String key: keySet) {
            if (key == null || key.isEmpty() || (!(map.get(key) instanceof  String)
            || !(map.get(key) instanceof  Number)
                    || !(map.get(key) instanceof Date))) {
                throw new SQLException(WRONG_INPUT);
            }
            parameters += map.get(key) + DELIMETER;
            values +="?" + DELIMETER;
        }
        parameters = parameters.substring(0, parameters.length() - 3);
        values = values.substring(0, values.length() - 3);
        result = result.replace(PARAMETERS, parameters).replace(VALUES, values);

        return result;
    }

    public boolean create(T t, String schemaName, String tableName, Map<String, Object> map) throws DAOException {

        try (PreparedStatement statement = connection.prepareStatement(
                getInsertQuery(schemaName, tableName, map),
                PreparedStatement.RETURN_GENERATED_KEYS);) {


            Set<String> keySet = map.keySet();
            int i = 0;
            for (String key: keySet) {
                statement.setObject( ++i,map.get(key));
            }
            statement.executeUpdate();

            if (statement.getGeneratedKeys().next()) {
                t.setId(statement.getGeneratedKeys().getInt(0));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + e.getLocalizedMessage());
        }
    }
}
