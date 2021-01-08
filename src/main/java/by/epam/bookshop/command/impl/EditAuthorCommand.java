package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.validator.AuthorValidator;
import by.epam.bookshop.validator.BookValidator;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class EditAuthorCommand implements Command {

    private static final String WRONG_AUTHOR_ERROR = "error.author.id";
    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String INPUT_ERROR = "error.input";
    private static final String WRONG_ENTITY = "wrong.entity.error";
    private static final String URL_INPUT_ERROR = "error.url.input";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Author newAuthor;
        String firstName = request.getParameter(RequestParameters.AUTHOR_FIRSTNAME);
        String lastName = request.getParameter(RequestParameters.AUTHOR_LASTNAME);
        String photoLink = request.getParameter(RequestParameters.PHOTOLINK);

        URL link;

        try {
            if (photoLink == null || photoLink.isEmpty()) {
                link = null;
            } else {
                link = new URL(photoLink);
            }
            new AuthorValidator().validate(firstName, lastName, photoLink);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        } catch (MalformedURLException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, URL_INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        }

        if (request.getSession().getAttribute(SessionParameters.AUTHOR) instanceof Author) {
            newAuthor = (Author) request.getSession().getAttribute(SessionParameters.AUTHOR);
        } else {
            throw new CommandException(WRONG_ENTITY);
        }

        try {
            newAuthor.setFirstName(firstName);
            newAuthor.setPhotoLink(new URL(photoLink));
            newAuthor.setLastName(lastName);
            AuthorService.getInstance().update(newAuthor);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ValidationException | MalformedURLException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            return new Router(JSPPages.EDIT_AUTHOR_PAGE);
        }
        request.getSession().setAttribute(SessionParameters.AUTHOR, newAuthor);
        request.setAttribute(RequestParameters.AUTHOR, newAuthor);
        return new Router(JSPPages.VIEW_AUTHOR_PAGE);
    }
}
