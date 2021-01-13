package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;

import javax.servlet.http.HttpServletRequest;

public class ViewAuthorCommand implements Command {

    private static final int ELEMENTS_PER_PAGE = 30;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.getParameter(RequestParameters.AUTHOR_ID);
        int page;
        try {
            page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        try {

            Author author = AuthorService.getInstance()
                    .read(Integer.parseInt(request.getParameter(RequestParameters.AUTHOR_ID)));

            BookFinder finder = new BookFinder().findByAuthor(author.getId());

            Book[] books = BookService.getInstance().findBy(finder).toArray(Book[]::new);

            int pageQuantity = CommandUtil.pageQuantity(BookService.getInstance().countBy(finder), ELEMENTS_PER_PAGE);

            request.setAttribute(RequestParameters.BOOKS, books);
            request.setAttribute(RequestParameters.PAGE_QUANTITY, pageQuantity);
            request.setAttribute(RequestParameters.CURRENT_PAGE, page);
            request.getSession().setAttribute(SessionParameters.AUTHOR, author);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(JSPPages.VIEW_AUTHOR_PAGE);
    }
}
