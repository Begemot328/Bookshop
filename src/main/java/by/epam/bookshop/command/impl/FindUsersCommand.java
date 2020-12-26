package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Paginator;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.UnknownEntityException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class FindUsersCommand implements Command {


    @Override
    public Router execute(HttpServletRequest request) {
        UserFinder finder = new UserFinder();

        if (isNotEmpty(request.getParameter(RequestParameters.USER_FIRSTNAME))) {
            finder = finder.findByFirstName(request.getParameter(RequestParameters.USER_FIRSTNAME));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.USER_LASTNAME))) {
            finder = finder.findByLastName(request.getParameter(RequestParameters.USER_LASTNAME));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.USER_STATUS))) {
            try {
                finder = finder.findByStatus(UserStatus.resolveById(
                        Integer.valueOf(request.getParameter(RequestParameters.USER_LASTNAME))));
            } catch (UnknownEntityException e) {

            }
        }

        try {

            Object[] users = UserService.getInstance().findBy(finder).toArray();

            request.getSession().setAttribute(SessionParameters.USERS, users);
            Paginator.paginate(request, users, 1);
        } catch (ServiceException e) {
            return new Router(JSPPages.ERROR_PAGE);
        }
        return new Router(JSPPages.SEARCH_BOOKS_PAGE);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }


}
