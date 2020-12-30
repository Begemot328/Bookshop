package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.dao.impl.position.PositionFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

public class ViewUserCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            User buyer = UserService.getInstance()
                    .read(Integer.parseInt(request.getParameter(RequestParameters.USER_ID)));
            Position[] positions; // todo

            request.getSession().setAttribute(SessionParameters.BOOKS, books);
            request.getSession().setAttribute(SessionParameters.AUTHOR, author);
        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionParameters.ERROR_MESSAGE, e.getMessage() + e.getStackTrace());
            return new Router(JSPPages.ERROR_PAGE);
        }

        return new Router(JSPPages.VIEW_AUTHOR_PAGE);
    }
}
