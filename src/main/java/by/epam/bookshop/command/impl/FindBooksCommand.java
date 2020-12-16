package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.book.BookService;

import javax.servlet.http.HttpServletRequest;

public class FindBooksCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        BookFinder finder = new BookFinder();

        if (request.getParameter(RequestParameters.TITLE) == null) {
            finder = finder.findByTitle(request.getParameter(RequestParameters.TITLE));
        }

        if (request.getParameter(RequestParameters.AUTHOR) == null) {
            finder = finder.findByAuthor(Integer.parseInt(request.getParameter(RequestParameters.AUTHOR)));
        }

        if (request.getParameter(RequestParameters.AUTHOR_FIRSTNAME) == null) {
            finder = finder.findByAuthor(Integer.parseInt(request.getParameter(RequestParameters.AUTHOR)));
        }

        if (request.getParameter(RequestParameters.MAX_PRICE) == null) {
            finder = finder.findByPriceLess(Float.parseFloat(request.getParameter(RequestParameters.MAX_PRICE)));
        }

        if (request.getParameter(RequestParameters.MIN_PRICE) == null) {
            finder = finder.findByPriceMore(Float.parseFloat(request.getParameter(RequestParameters.MIN_PRICE)));
        }

        try {
            request.setAttribute("books", BookService.getInstance().findAll().toArray());
        } catch (DAOException|ServiceException e) {
            return new Router(JSPPages.ERROR_PAGE);
        }
        return new Router(JSPPages.SEARCH_BOOKS);
    }
}
