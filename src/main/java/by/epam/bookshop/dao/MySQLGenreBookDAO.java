package by.epam.bookshop.dao;

import by.epam.bookshop.controller.Controller;
import by.epam.bookshop.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLGenreBookDAO {
    private static final String SCHEMA_NAME = "BOOKSHOP";
    private static final String TABLE_NAME = "GENRE_BOOKS";
    private static final String SCHEMA = "[SCHEMA]";
    private static final String TABLE = "[TABLE]";
    private static final String GENRE_ID = "GENRE_ID";
    private static final String ID = "ID";
    private static final String BOOK_ID = "BOOK_ID";
    protected static final String WHERE = " WHERE [PARAMETER] = [VALUE]";
    protected static final String OR = " OR [PARAMETER] = ?";
    protected static final String AND = " AND [PARAMETER] = ?";
    protected static final String EQUAL = "=";
    private static final String PARAMETERS = "[PARAMETERS]";
    private static final String PARAMETER = "[PARAMETER]";
    private static final String VALUES = "[VALUES]";
    private static final String VALUE = "[VALUE]";
    private static final String DELIMETER = ", ";
    private static final String QUESTION_MARK = "?";
    private static final String CONDITION = "[CONDITION]";
    private static final String INSERT_QUERY =
            "INSERT INTO [SCHEMA].[TABLE]([PARAMETERS]) VALUES ([VALUES]);";
    private static final String UPDATE_QUERY =
            "UPDATE [SCHEMA].[TABLE] SET [PARAMETERS] WHERE ID = ?;";
    private static final String DELETE_QUERY =
            "DELETE FROM [SCHEMA].[TABLE] [CONDITION];";
    private static final String SELECT_QUERY =
            "SELECT * FROM [SCHEMA].[TABLE] [CONDITION];";

    private Connection connection;

    public MySQLGenreBookDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(int bookId, int genreId) throws DAOException {
        String query =
                INSERT_QUERY.replace(SCHEMA, SCHEMA_NAME)
                        .replace(TABLE, TABLE_NAME)
                        .replace(PARAMETERS, BOOK_ID + DELIMETER + GENRE_ID)
                        .replace(VALUES, QUESTION_MARK + DELIMETER + QUESTION_MARK);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            statement.setInt(2, genreId);

            Controller.getLoggerInstance().debug(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void delete(int bookId, int genreId) throws DAOException {
        String query =
                DELETE_QUERY.replace(SCHEMA, SCHEMA_NAME)
                        .replace(TABLE, TABLE_NAME)
                        .replace(CONDITION, WHERE.replace(PARAMETER, BOOK_ID)
                                .replace(VALUE, QUESTION_MARK)
                                + AND.replace(PARAMETER, GENRE_ID));
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            statement.setInt(2, genreId);

            Controller.getLoggerInstance().debug(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Integer> findByBook(int id) throws DAOException {
        List<Integer> result = new ArrayList<Integer>();
        String query = SELECT_QUERY.replace(SCHEMA, SCHEMA_NAME)
                .replace(TABLE, TABLE_NAME)
                .replace(CONDITION, WHERE)
                .replace(PARAMETER, BOOK_ID)
                .replace(VALUE, Integer.toString(id));

        Controller.getLoggerInstance().debug(query);
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    result.add(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

    public List<Integer> findByGenre(int id) throws DAOException {
        List<Integer> result = new ArrayList<Integer>();
        String query = SELECT_QUERY.replace(SCHEMA, SCHEMA_NAME)
                .replace(TABLE, TABLE_NAME)
                .replace(CONDITION, WHERE)
                .replace(PARAMETER, GENRE_ID)
                .replace(VALUE, Integer.toString(id));

        Controller.getLoggerInstance().debug(query);
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    result.add(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }
}
