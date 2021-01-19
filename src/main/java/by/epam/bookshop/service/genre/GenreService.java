package by.epam.bookshop.service.genre;

import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.dao.impl.genre.MySQLGenreDAO;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.genre.Genre;
import by.epam.bookshop.entity.genre.GenreFactory;
import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.service.AbstractEntityService;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.validator.impl.GenreValidator;

import java.sql.Connection;


public class GenreService  extends AbstractEntityService<Genre> {
    private static final GenreService INSTANCE = new GenreService();

    private GenreService() {}


    @Override
    public MySQLEntityDAO<Genre> getDAO(Connection connection) {
        return new MySQLGenreDAO(connection);
    }

    public static EntityService<Genre> getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractEntityFactory<Genre> getFactory() {
        return new GenreFactory();
    }

    @Override
    public EntityValidator<Genre> getValidator() {
        return new GenreValidator();
    }
}
