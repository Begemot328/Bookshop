package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.book.BookService;

import javax.servlet.http.HttpServletRequest;

public class FindAllBooksCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            request.getSession()
                    .setAttribute(SessionParameters.BOOKS,
                            BookService.getInstance().findAll().toArray());
        } catch (DAOException|ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.SEARCH_BOOKS_PAGE);
    }
}
