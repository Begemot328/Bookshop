package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class FindBooksCommand implements Command {

    private static final int ELEMENTS_PER_PAGE = 30;

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

            Object[] books = BookService.getInstance().findBy(finder).toArray();
            request.getSession().setAttribute(SessionParameters.BOOKS, books);
            paginate(request, books);
        } catch (ServiceException e) {
            return new Router(JSPPages.ERROR_PAGE);
        }
        return new Router(JSPPages.SEARCH_BOOKS_PAGE);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    private void paginate(HttpServletRequest request, Object[] array) {
        int pageQuantity = array.length / ELEMENTS_PER_PAGE;
        if (((double) array.length) / ELEMENTS_PER_PAGE != pageQuantity) {
            pageQuantity++;
        }
        request.getSession().setAttribute(SessionParameters.CURRENT_PAGE, Integer.valueOf(1));
        request.getSession().setAttribute(SessionParameters.PAGE_ELEMENTS, Integer.valueOf(ELEMENTS_PER_PAGE));
        request.getSession().setAttribute(SessionParameters.PAGE_QUANTITY, Integer.valueOf(pageQuantity));
    }
}
