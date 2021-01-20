package by.epam.bookshop.service.book;

import by.epam.bookshop.dao.MySQLGenreBookDAO;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.entity.genre.Genre;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.AbstractEntityService;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.validator.impl.BookValidator;
import by.epam.bookshop.validator.EntityValidator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookService extends AbstractEntityService<Book> {

    private static final BookService INSTANCE = new BookService();

    private BookService() {
    }


    public static EntityService<Book> getInstance() {
        return INSTANCE;
    }

    public MySQLBookDAO getDAO(Connection connection) {
        return new MySQLBookDAO(connection);
    }

    public BookFactory getFactory() {
        return new BookFactory();
    }

    @Override
    public EntityValidator<Book> getValidator() {
        return new BookValidator();
    }

    public void changeGenres(Book book, List<Genre> genres) throws ServiceException {
        MySQLGenreBookDAO genreBookDAO = new MySQLGenreBookDAO(getConnection());
        List<Integer> list = null;
        try {
            list = genreBookDAO.findByBook(book.getId());

        List<Integer> genreList = Arrays.asList(genres.stream().map(Entity::getId).toArray(Integer[]::new).clone());

        for (Integer genre: genreList) {
            if (!list.contains(genre)) {
                genreBookDAO.create(book.getId(), genre);
            }
        }
        for (Integer genre: list) {
            if (!genreList.contains(genre)) {
                genreBookDAO.delete(book.getId(), genre);
            }
        }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
 }
