package by.epam.bookshop.dao.impl.genre;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.genre.Genre;
import by.epam.bookshop.entity.genre.GenreFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MySQLGenreDAO extends MySQLEntityDAO<Genre> {

    private static final String SCHEMA = "BOOKSHOP";
    private static final String TABLE = "GENRES";
    private static final String ID = "ID";
    private static final String NAME = "NAME";

    public MySQLGenreDAO(Connection connection) {
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
    public Collection<Genre> mapToList(ResultSet resultSet) throws DAOException {
        EntityFactory<Genre> factory = new GenreFactory();
        ArrayList<Genre> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                result.add(factory.createWithID(resultSet.getInt(ID),
                        resultSet.getString(NAME)));
            }
            return result;
        } catch (SQLException | FactoryException e) {
            throw new DAOException(e);
        }
    }

        @Override
        public Map<String, Object> mapEntity (Genre genre) throws DAOException {
            Map<String, Object> map = new HashMap<>();
            map.put(NAME, genre.getName());
            return map;
        }

        @Override
        public EntityFinder<Genre> getFinder () throws DAOException {
            return new GenreFinder();
        }
    }
