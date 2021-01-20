package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.genre.GenreFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.genre.Genre;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.genre.GenreService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditBookMenuCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        try {
            Book book = BookService.getInstance().read(Integer.parseInt(
                    request.getParameter(RequestParameters.BOOK_ID)));

            Genre[] bookGenres = GenreService.getInstance().findBy(new GenreFinder()
                    .findByBook(book.getId())).toArray(Genre[]::new);
            request.setAttribute(RequestParameters.BOOK_GENRES, bookGenres);
            if (request.getServletContext().getAttribute(RequestParameters.GENRES) instanceof Genre[]) {
                List<Genre> genres = new ArrayList<>(Arrays.asList(
                        (Genre[]) request.getServletContext().getAttribute(RequestParameters.GENRES)));
                genres.removeAll(Arrays.asList(bookGenres.clone()));
                request.setAttribute(RequestParameters.GENRES, genres.toArray(Genre[]::new));
            }
            request.setAttribute(RequestParameters.BOOK, book);
            Author[] authors = AuthorService.getInstance().findAll().toArray(Author[]::new);
            request.setAttribute(RequestParameters.AUTHORS, authors);

        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.EDIT_BOOK_PAGE);
    }
}
