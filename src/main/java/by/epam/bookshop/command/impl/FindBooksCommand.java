package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Paginator;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.stream.Stream;

public class FindBooksCommand implements Command {


    @Override
    public Router execute(HttpServletRequest request) {
        BookFinder finder = new BookFinder();
        Enumeration<String> stringEnum = request.getParameterNames();

        while (stringEnum.hasMoreElements()) {
            String par = stringEnum.nextElement();
            System.out.println(par + " '" + request.getParameter(par) + "'");
        }

        if (isNotEmpty(request.getParameter(RequestParameters.TITLE))) {
            finder = finder.findByTitle(request.getParameter(RequestParameters.TITLE));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR))) {
            finder = finder.findByAuthor(Integer.parseInt(request.getParameter(RequestParameters.AUTHOR)));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.MAX_PRICE))) {
            finder = finder.findByPriceLess(Float.parseFloat(request.getParameter(RequestParameters.MAX_PRICE)));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.MIN_PRICE))) {
            finder = finder.findByPriceMore(Float.parseFloat(request.getParameter(RequestParameters.MIN_PRICE)));
        }

        try {
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

            Position[] books = BookService.getInstance().findBy(finder).toArray(Position[]::new);

            request.getSession().setAttribute(SessionParameters.BOOKS, books);
            Paginator.paginate(request, books, 1);
        } catch (ServiceException e) {
            return new Router(JSPPages.ERROR_PAGE);
        }
        return new Router(JSPPages.SEARCH_BOOKS_PAGE);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }


}
