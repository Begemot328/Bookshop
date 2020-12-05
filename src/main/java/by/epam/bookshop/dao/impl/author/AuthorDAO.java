package by.epam.bookshop.dao.impl.author;

import by.epam.bookshop.dao.EntityDAO;
import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
// Can be deleted
public class AuthorDAO implements EntityDAO<Author> {
    private static final String ID = "ID";
    private static final String FIRSTNAME = "first_name";
    private static final String LASTNAME = "last_name";

    private static final String INSERT_QUERY =
            "INSERT INTO BOOKSHOP.authors(FIRST_NAME, LAST_NAME) VALUES (?,?);";
    private static final String DELETE_QUERY =
            "DELETE FROM BOOKSHOP.authors where ID = ?;";
    private static final String UPDATE_QUERY =
            "UPDATE BOOKSHOP.authors SET FIRST_NAME = ?, LAST_NAME = ? WHERE ID = ?;";

    private static final String SQL_EXCEPTION = "SQL Exception: ";
    private static final String FACTORY_EXCEPTION = "Factory Exception: ";

    private Connection connection;

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Author author) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(
                INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());

            statement.executeUpdate();

            if (statement.getGeneratedKeys().next()) {
                author.setId(statement.getGeneratedKeys().getInt(0));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + e.getLocalizedMessage());
        }
    }

    @Override
    public Author read(int id) throws DAOException {
        Collection<Author> result = findBy((new AuthorFinder()).findByID(id));
        if (result == null || result.isEmpty() || result.size() > 1) {
            return null;
        } else {
            return result.stream().toArray(Author[]::new)[0];
        }
    }

    @Override
    public void update(Author author) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(
                UPDATE_QUERY)) {
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());
            statement.setInt(3, author.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(
                DELETE_QUERY)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + e.getLocalizedMessage());
        }
    }

    @Override
    public Collection<Author> findAll() throws DAOException {
            return findBy(new AuthorFinder());
    }

    @Override
    public Collection<Author> findBy(EntityFinder<Author> finder) throws DAOException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(finder.getQuery())) {
                return mapToList(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + e.getLocalizedMessage());
        }
    }

    private Collection<Author> mapToList(ResultSet resultSet) throws DAOException {
        EntityFactory<Author> factory = new AuthorFactory();
        ArrayList<Author> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(factory.createWithID( resultSet.getInt(ID),
                        resultSet.getString(FIRSTNAME),
                        resultSet.getString(LASTNAME)));
            }
            return result;
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + e.getLocalizedMessage());
        } catch (FactoryException e) {
            throw new DAOException(FACTORY_EXCEPTION, e);
        }
    }
}
