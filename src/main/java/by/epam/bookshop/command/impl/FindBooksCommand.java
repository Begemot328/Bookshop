package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class FindBooksCommand implements Command {


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BookFinder finder = new BookFinder();
        Enumeration<String> stringEnum = request.getParameterNames();

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
            if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_FIRSTNAME))
                    || isNotEmpty(request.getParameter(RequestParameters.AUTHOR_LASTNAME))) {

                AuthorFinder authorFinder = new AuthorFinder();

                if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_FIRSTNAME))) {
                    authorFinder = authorFinder.findByFirstName(
                            request.getParameter(RequestParameters.AUTHOR_FIRSTNAME));

                    request.setAttribute(RequestParameters.AUTHOR_FIRSTNAME,
                            request.getParameter(RequestParameters.AUTHOR_FIRSTNAME));
                    if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_LASTNAME))) {
                        authorFinder = authorFinder.findByLastName(
                                request.getParameter(RequestParameters.AUTHOR_LASTNAME));
                        request.setAttribute(RequestParameters.AUTHOR_LASTNAME,
                                request.getParameter(RequestParameters.AUTHOR_LASTNAME));
                    }
                } else {
                    authorFinder = authorFinder.findByLastName(
                            request.getParameter(RequestParameters.AUTHOR_LASTNAME));
                    request.setAttribute(RequestParameters.AUTHOR_LASTNAME,
                            request.getParameter(RequestParameters.AUTHOR_LASTNAME));
                }

                finder = finder.findByAuthors(AuthorService.getInstance().findBy(authorFinder));
            }

            Book[] books = BookService.getInstance().findBy(finder).toArray(Book[]::new);

            request.getSession().setAttribute(SessionParameters.BOOKS, books);
            Paginator.paginate(request, books, 1);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.SEARCH_BOOKS_PAGE);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }

}
