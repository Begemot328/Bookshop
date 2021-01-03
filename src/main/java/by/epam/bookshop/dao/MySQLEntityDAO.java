package by.epam.bookshop.dao;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.DAOException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class MySQLEntityDAO<T  extends Entity> implements EntityDAO<T> {
    private static final String INSERT_QUERY =
            "INSERT INTO [SCHEMA].[TABLE]([PARAMETERS]) VALUES ([VALUES]);";
    private static final String UPDATE_QUERY =
            "UPDATE [SCHEMA].[TABLE] SET [PARAMETERS] WHERE ID = ?;";

    private static final String DELETE_QUERY =
            "DELETE FROM [SCHEMA].[TABLE] where ID = ?;";

    private static final String SCHEMA = "[SCHEMA]";
    private static final String TABLE = "[TABLE]";
    private static final String PARAMETERS = "[PARAMETERS]";
    private static final String VALUES = "[VALUES]";
    private static final String DELIMETER = ", ";
    private static final String SQL_EXCEPTION = "SQL Exception: ";
    private static final String WRONG_INPUT = "Wrong data, no null or empty " +
            "strings, values are String or Number only!";

    protected   Connection connection;

    public MySQLEntityDAO (Connection connection) {
        this.connection = connection;
    }

    public abstract Collection<T> mapToList(ResultSet resultSet) throws DAOException;

    private String getInsertQuery(String schemaName, String tableName, Map<String, Object> map) throws DAOException {
        String result = INSERT_QUERY.replace(SCHEMA, schemaName).replace(TABLE, tableName);
        StringBuilder values = new StringBuilder();
        StringBuilder parameters = new StringBuilder();
        Set<String> keySet = map.keySet();

        for (String key: keySet) {
            if (key == null || key.isEmpty() || isInvalidInput(map.get(key))) {
                throw new DAOException(WRONG_INPUT);
            }
            if (map.get(key) == null) {
                continue;
            }
            parameters.append(key).append(DELIMETER);
            values.append("?" + DELIMETER);
        }
        parameters = new StringBuilder(parameters.substring(0, parameters.length() - 2));
        values = new StringBuilder(values.substring(0, values.length() - 2));
        result = result.replace(PARAMETERS, parameters.toString()).replace(VALUES, values.toString());

        return result;
    }

    private boolean isInvalidInput(Object object) {
        return  object != null
                && !(object instanceof String)
                && !(object instanceof  Number)
                && !(object instanceof Timestamp);
    }

    private String getUpdateQuery(String schemaName, String tableName, Map<String, Object> map) throws DAOException {
        String result = UPDATE_QUERY.replace(SCHEMA, schemaName).replace(TABLE, tableName);
        StringBuilder parameters = new StringBuilder();
        Set<String> keySet = map.keySet();

        for (String key: keySet) {
            if (key == null || key.isEmpty() || isInvalidInput(map.get(key))) {
                throw new DAOException(WRONG_INPUT);
            }
            if (map.get(key) == null) {
                continue;
            }
            parameters.append(key).append(" = ?").append(DELIMETER);
        }
        parameters = new StringBuilder(parameters.substring(0, parameters.length() - 2));
        result = result.replace(PARAMETERS, parameters.toString());
        return result;
    }

    public boolean create(T t, String schemaName, String tableName, Map<String, Object> map) throws DAOException {

        try (PreparedStatement statement = connection.prepareStatement(
                getInsertQuery(schemaName, tableName, map),
                PreparedStatement.RETURN_GENERATED_KEYS);) {

            Set<String> keySet = map.keySet();
            int i = 0;
            for (String key: keySet) {
                if (map.get(key) == null) {
                    continue;
                }
                statement.setObject( ++i,map.get(key));

            }
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                t.setId(resultSet.getInt(1));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void update(T t, String schemaName, String tableName, Map<String, Object> map) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(
                getUpdateQuery(schemaName, tableName, map))) {
            Set<String> keySet = map.keySet();
            int i = 0;
            for (String key: keySet) {
                if (map.get(key) == null) {
                    continue;
                }
                statement.setObject( ++i,map.get(key));
            }
            statement.setInt(++i, t.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void delete(int id, String schemaName,  String tableName) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(
                DELETE_QUERY.replace(SCHEMA, schemaName).replace(TABLE, tableName))) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + e.getLocalizedMessage());
        }
    }
}
