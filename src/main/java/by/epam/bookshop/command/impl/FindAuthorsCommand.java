package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Paginator;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.UnknownEntityException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

public class FindAuthorsCommand implements Command {


    @Override
    public Router execute(HttpServletRequest request) {
        AuthorFinder finder = new AuthorFinder();

        if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_FIRSTNAME))) {
            finder = finder.findByFirstName(request.getParameter(RequestParameters.AUTHOR_FIRSTNAME));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_LASTNAME))) {
            finder = finder.findByLastName(request.getParameter(RequestParameters.AUTHOR_LASTNAME));
        }

        try {
            Author[] authors = AuthorService.getInstance().findBy(finder).toArray(Author[]::new);
            request.getSession().setAttribute(SessionParameters.AUTHORS, authors);
            Paginator.paginate(request, authors, 1);
        } catch (ServiceException e) {
            return new Router(JSPPages.ERROR_PAGE);
        }
        return new Router(JSPPages.SEARCH_AUTHORS_PAGE);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
