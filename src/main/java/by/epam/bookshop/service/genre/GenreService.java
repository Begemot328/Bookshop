package by.epam.bookshop.service.genre;

import by.epam.bookshop.dao.MySQLEntityDAO;
import by.epam.bookshop.dao.MySQLGenreBookDAO;
import by.epam.bookshop.dao.impl.genre.MySQLGenreDAO;
import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.genre.Genre;
import by.epam.bookshop.entity.genre.GenreFactory;
import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.AbstractEntityService;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.validator.impl.GenreValidator;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;


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

    public void changeBooks(Genre genre, List<Integer> bookList) throws ServiceException {
        MySQLGenreBookDAO genreBookDAO = new MySQLGenreBookDAO(getConnection());
        List<Integer> list = null;
        try {
            list = genreBookDAO.findByGenre(genre.getId());

            for (Integer book: bookList) {
                if (!list.contains(book)) {
                    genreBookDAO.create(genre.getId(), book);
                }
            }
            for (Integer book: list) {
                if (!bookList.contains(book)) {
                    genreBookDAO.delete(genre.getId(), book);
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
