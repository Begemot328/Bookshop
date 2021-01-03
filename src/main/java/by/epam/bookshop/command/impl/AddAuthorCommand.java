package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.author.AuthorValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class AddAuthorCommand implements Command {

    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String INPUT_ERROR = "error.input";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Author newAuthor;
        String firstName = request.getParameter(RequestParameters.AUTHOR_FIRSTNAME);
        String lastName = request.getParameter(RequestParameters.AUTHOR_LASTNAME);
        String photoLink = request.getParameter(RequestParameters.PHOTOLINK);

        try {
            if (new AuthorValidator().validate(firstName, lastName, photoLink)) {
                newAuthor = AuthorService.getInstance().create(firstName, lastName, photoLink);
                request.getSession().setAttribute(SessionParameters.AUTHOR, newAuthor);
                request.getSession().removeAttribute(SessionParameters.BOOKS);
                return new Router(JSPPages.VIEW_AUTHOR_PAGE);
            } else {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, INPUT_ERROR);
                return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
            }

        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionParameters.ERROR_MESSAGE, e.getMessage()
                    + Arrays.toString(e.getStackTrace()));
            throw new CommandException(e);
        }
    }
}
