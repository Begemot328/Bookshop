package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.author.AuthorService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class AddAuthorCommand implements Command {

    private static final int PICTURE_HEIGHT = 250;
    private static final int PICTURE_WIDTH = 250;
    private static final String PATH_ID = "1N5hFmFot4-eJx2PKAmEVdPBMysT1nV99";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Author newAuthor;
        String description = request.getParameter(RequestParameters.DESCRIPTION);
        String firstName = request.getParameter(RequestParameters.AUTHOR_FIRSTNAME);
        String lastName = request.getParameter(RequestParameters.AUTHOR_LASTNAME);
        URL link;

        try {
            link = CommandUtil.getBookLink(request, PICTURE_WIDTH, PICTURE_HEIGHT, PATH_ID);

        } catch (MalformedURLException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.URL_INPUT_ERROR);
            return new Router(JSPPages.ADD_AUTHOR_PAGE);
        } catch (IOException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.FILE_INPUT_ERROR);
            return new Router(JSPPages.ADD_AUTHOR_PAGE);
        } catch (GeneralSecurityException | ServletException e) {
            throw new CommandException(e);
        }

        try {
            newAuthor = AuthorService.getInstance().create(firstName, lastName, description, link);

            Map<String, String> parameters = new HashMap<>();
            parameters.put(RequestParameters.COMMAND, CommandEnum.VIEW_AUTHOR_COMMAND.toString());
            parameters.put(RequestParameters.AUTHOR_ID, Integer.toString(newAuthor.getId()));
            return new Router(new URL(CommandUtil.getURL(
                    request.getRequestURL().toString(), parameters)));
        } catch (ServiceException | MalformedURLException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            return new Router(JSPPages.ADD_AUTHOR_PAGE);
        }
    }
}
