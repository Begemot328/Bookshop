package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;

import javax.servlet.http.HttpServletRequest;

public class ViewAuthorCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.getParameter(RequestParameters.AUTHOR_ID);
        try {
            Author author = AuthorService.getInstance()
                    .read(Integer.parseInt(request.getParameter(RequestParameters.AUTHOR_ID)));
            Book[] books = BookService.getInstance().findBy(
                    new BookFinder().findByAuthor(author.getId())).toArray(Book[]::new);

            request.getSession().setAttribute(SessionParameters.BOOKS, books);
            request.getSession().setAttribute(SessionParameters.AUTHOR, author);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(JSPPages.VIEW_AUTHOR_PAGE);
    }
}
