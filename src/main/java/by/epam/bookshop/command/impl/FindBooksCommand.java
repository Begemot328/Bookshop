package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.dao.impl.genre.GenreFinder;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.genre.Genre;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.genre.GenreService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindBooksCommand implements Command {

    private static final int ELEMENTS_PER_PAGE = 12;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BookFinder finder = new BookFinder();
        int page;
        try {
            page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        if (isNotEmpty(request.getParameter(RequestParameters.TITLE))) {
            finder = finder.findByTitle(request.getParameter(RequestParameters.TITLE));
            request.setAttribute(RequestParameters.TITLE, request.getParameter(RequestParameters.TITLE));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR))) {
            finder = finder.findByAuthor(Integer.parseInt(request.getParameter(RequestParameters.AUTHOR)));
            request.setAttribute(RequestParameters.AUTHOR, request.getParameter(RequestParameters.AUTHOR));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.MAX_PRICE))) {
            finder = finder.findByPriceLess(Float.parseFloat(request.getParameter(RequestParameters.MAX_PRICE)));
            request.setAttribute(RequestParameters.MAX_PRICE, request.getParameter(RequestParameters.MAX_PRICE));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.MIN_PRICE))) {
            finder = finder.findByPriceMore(Float.parseFloat(request.getParameter(RequestParameters.MIN_PRICE)));
            request.setAttribute(RequestParameters.MIN_PRICE, request.getParameter(RequestParameters.MIN_PRICE));
        }
        try {
            List<Genre> bookGenres = new ArrayList<>();
            if (request.getParameterValues(RequestParameters.GENRE_ID) != null
                    && request.getParameterValues(RequestParameters.GENRE_ID).length > 0) {


                for (String id :
                        request.getParameterValues(RequestParameters.GENRE_ID)) {
                    finder = finder.findByGenre(Integer.parseInt(id));
                    bookGenres.add(GenreService.getInstance().read(Integer.parseInt(id)));
                }
                request.setAttribute(RequestParameters.GENRE_ID, request.getParameterValues(RequestParameters.GENRE_ID));
            }


            if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_FIRSTNAME))
                    || isNotEmpty(request.getParameter(RequestParameters.AUTHOR_LASTNAME))) {

                AuthorFinder authorFinder = new AuthorFinder();

                if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_FIRSTNAME))) {
                    authorFinder = authorFinder.findByFirstName(
                            request.getParameter(RequestParameters.AUTHOR_FIRSTNAME));
                    if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_LASTNAME))) {
                        authorFinder = authorFinder.findByLastName(
                                request.getParameter(RequestParameters.AUTHOR_LASTNAME));
                    }
                } else {
                    authorFinder = authorFinder.findByLastName(
                            request.getParameter(RequestParameters.AUTHOR_LASTNAME));
                }

                finder = finder.findByAuthors(AuthorService.getInstance().findBy(authorFinder));
            }

            int pageQuantity = CommandUtil.pageQuantity(BookService.getInstance().countBy(finder), ELEMENTS_PER_PAGE);

            Book[] books = BookService.getInstance().findBy(
                    finder, (page - 1) * ELEMENTS_PER_PAGE, ELEMENTS_PER_PAGE)
                    .toArray(Book[]::new);

            request.setAttribute(RequestParameters.BOOKS, books);
            request.setAttribute(RequestParameters.PAGE_QUANTITY, pageQuantity);
            request.setAttribute(RequestParameters.CURRENT_PAGE, page);

            request.setAttribute(RequestParameters.BOOK_GENRES, bookGenres.toArray(Genre[]::new));
            if (request.getServletContext().getAttribute(RequestParameters.GENRES) instanceof Genre[]) {
                List<Genre> genres = Arrays.asList(
                        (Genre[]) request.getServletContext().getAttribute(RequestParameters.GENRES));
                genres.removeAll(bookGenres);
                request.setAttribute(RequestParameters.GENRES, genres.toArray(Genre[]::new));
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.SEARCH_BOOKS_PAGE);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }

}
