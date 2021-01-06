package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;

import javax.servlet.http.HttpServletRequest;

public class FindAuthorsCommand implements Command {


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        AuthorFinder finder = new AuthorFinder();

        if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_FIRSTNAME))) {
            finder = finder.findByFirstName(request.getParameter(RequestParameters.AUTHOR_FIRSTNAME));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_LASTNAME))) {
            finder = finder.findByLastName(request.getParameter(RequestParameters.AUTHOR_LASTNAME));
        }

        try {
            Author[] authors = AuthorService.getInstance().findBy(finder).toArray(Author[]::new);
            request.setAttribute(RequestParameters.AUTHORS, authors);
            Paginator.paginate(request, authors, 1);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.SEARCH_AUTHORS_PAGE);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
