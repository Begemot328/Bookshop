package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;

import javax.servlet.http.HttpServletRequest;

public class FindAuthorsCommand implements Command {

    private static final int ELEMENTS_PER_PAGE = 30;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        AuthorFinder finder = new AuthorFinder();
        int page;
        try {
            page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_FIRSTNAME))) {
            finder = finder.findByFirstName(request.getParameter(RequestParameters.AUTHOR_FIRSTNAME));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR_LASTNAME))) {
            finder = finder.findByLastName(request.getParameter(RequestParameters.AUTHOR_LASTNAME));
        }

        try {
            int pageQuantity = CommandUtil.pageQuantity(AuthorService.getInstance().countBy(finder),
                    ELEMENTS_PER_PAGE);

            Author[] authors = AuthorService.getInstance().findBy(finder).toArray(Author[]::new);
            request.setAttribute(RequestParameters.AUTHORS, authors);
            request.setAttribute(RequestParameters.PAGE_QUANTITY, pageQuantity);
            request.setAttribute(RequestParameters.CURRENT_PAGE, page);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.SEARCH_AUTHORS_PAGE);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
