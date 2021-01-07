package by.epam.bookshop.dao;

import by.epam.bookshop.controller.Controller;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.DAOException;
import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class MySQLEntityDAO<T extends Entity> implements EntityDAO<T> {
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
    protected static final String COUNT = "COUNT(id)";
    protected static final String LIMIT = " LIMIT [FIRST], [LAST]";
    protected static final String LAST = "[LAST]";
    protected static final String FIRST = "[FIRST]";

    protected Connection connection;

    public MySQLEntityDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract Collection<T> mapToList(ResultSet resultSet) throws DAOException;

    public abstract Map<String, Object> mapEntity(T t) throws DAOException;

    public abstract EntityFinder<T> getFinder() throws DAOException;

    @Override
    public boolean create(T t) throws DAOException {
        return create(t, getSchemaName(), getTableName(), mapEntity(t));
    }

    @Override
    public T read(int id) throws DAOException {
        Collection<T> result = findBy(getFinder().findByID(id));
        if (result == null || result.size() != 1) {
            return null;
        } else {
            return (T) result.toArray()[0];
        }
    }

    @Override
    public void update(T t) throws DAOException {
        update(t, getSchemaName(), getTableName(), mapEntity(t));
    }

    @Override
    public void delete(int id) throws DAOException {
        delete(id, getSchemaName(), getTableName());
    }

    private String getInsertQuery(String schemaName, String tableName, Map<String, Object> map) throws DAOException {
        String result = INSERT_QUERY.replace(SCHEMA, schemaName).replace(TABLE, tableName);
        StringBuilder values = new StringBuilder();
        StringBuilder parameters = new StringBuilder();
        Set<String> keySet = map.keySet();

        for (String key : keySet) {
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
        return object != null
                && !(object instanceof String)
                && !(object instanceof Number)
                && !(object instanceof Timestamp);
    }

    private String getUpdateQuery(String schemaName, String tableName, Map<String, Object> map) throws DAOException {
        String result = UPDATE_QUERY.replace(SCHEMA, schemaName).replace(TABLE, tableName);
        StringBuilder parameters = new StringBuilder();
        Set<String> keySet = map.keySet();

        for (String key : keySet) {
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
                PreparedStatement.RETURN_GENERATED_KEYS)) {

            Set<String> keySet = map.keySet();
            int i = 0;
            for (String key : keySet) {
                if (map.get(key) == null) {
                    continue;
                }
                statement.setObject(++i, map.get(key));

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
            for (String key : keySet) {
                if (map.get(key) == null) {
                    continue;
                }
                statement.setObject(++i, map.get(key));
            }
            statement.setInt(++i, t.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int countBy(EntityFinder<T> finder) throws DAOException {
        try (Statement statement = connection.createStatement()) {
            Controller.getLoggerInstance().debug(finder.getQuery().replaceFirst("\\*", COUNT));
            try (ResultSet resultSet = statement.executeQuery(finder.getQuery().replaceFirst("\\*", COUNT))) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    protected int countAll(String table) throws DAOException {
        return countBy(new EntityFinder<T>(table));
    }

    protected Collection<T> findAll(String table) throws DAOException {
        return findBy(new EntityFinder<T>(table));
    }

    protected Collection<T> findAll(String table, int first, int last) throws DAOException {
        return findBy(new EntityFinder<T>(table), first, last);
    }

    public int countAll() throws DAOException {
        return countBy(new EntityFinder<T>(getTableName()));
    }

    public Collection<T> findAll() throws DAOException {
        return findBy(new EntityFinder<T>(getTableName()));
    }

    public Collection<T> findAll(int first, int last) throws DAOException {
        return findBy(new EntityFinder<T>(getTableName()), first, last);
    }


    @Override
    public Collection<T> findBy(EntityFinder<T> finder) throws DAOException {
        try (Statement statement = connection.createStatement()) {

            Controller.getLoggerInstance().debug(finder.getQuery());
            try (ResultSet resultSet = statement.executeQuery(finder.getQuery())) {
                return mapToList(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Collection<T> findBy(EntityFinder<T> finder, int first, int last) throws DAOException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                    finder.getQuery().concat(LIMIT
                            .replace(FIRST, Integer.toString(first))
                            .replace(LAST, Integer.toString(last))))) {
                Controller.getLoggerInstance().debug(
                        finder.getQuery().concat(LIMIT
                                .replace(FIRST, Integer.toString(first))
                                .replace(LAST, Integer.toString(last))));
                return mapToList(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void delete(int id, String schemaName, String tableName) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(
                DELETE_QUERY.replace(SCHEMA, schemaName).replace(TABLE, tableName))) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + e.getLocalizedMessage());
        }
    }
}
