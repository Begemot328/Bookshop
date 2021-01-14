package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.validator.impl.AuthorValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

public class EditAuthorCommand implements Command {

    private static final int PICTURE_HEIGHT = 250;
    private static final int PICTURE_WIDTH = 250;
    private static final String PATH_ID = "1N5hFmFot4-eJx2PKAmEVdPBMysT1nV99";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Author newAuthor;
        String firstName = request.getParameter(RequestParameters.AUTHOR_FIRSTNAME);
        String lastName = request.getParameter(RequestParameters.AUTHOR_LASTNAME);
        String description = request.getParameter(RequestParameters.DESCRIPTION);
        URL link;

        if (request.getSession().getAttribute(SessionParameters.AUTHOR) instanceof Author) {
            newAuthor = (Author) request.getSession().getAttribute(SessionParameters.AUTHOR);
        } else {
            throw new CommandException(ErrorMessages.WRONG_ENTITY);
        }

        try {
            link = CommandUtil.getBookLink(request, PICTURE_WIDTH, PICTURE_HEIGHT, PATH_ID);
            new AuthorValidator().validate(firstName, lastName, description, link);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        } catch (MalformedURLException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.URL_INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        } catch (GeneralSecurityException | ServletException e) {
            throw new CommandException(e);
        } catch (IOException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.FILE_INPUT_ERROR);
            return new Router(JSPPages.EDIT_AUTHOR_PAGE);
        }

        try {
            newAuthor.setFirstName(firstName);
            newAuthor.setPhotoLink(link);
            newAuthor.setLastName(lastName);
            newAuthor.setDescription(description);
            AuthorService.getInstance().update(newAuthor);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            return new Router(JSPPages.EDIT_AUTHOR_PAGE);
        }
        request.getSession().setAttribute(SessionParameters.AUTHOR, newAuthor);
        request.setAttribute(RequestParameters.AUTHOR, newAuthor);
        return new Router(JSPPages.VIEW_AUTHOR_PAGE);
    }
}
