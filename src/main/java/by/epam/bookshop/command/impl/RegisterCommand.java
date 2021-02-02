package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.controller.dto.UserDTO;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.AddressException;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.user.UserService;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.util.PasswordCoder;
import by.epam.bookshop.validator.impl.UserValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

public class RegisterCommand implements Command {
    private static final String REGISTER_ERROR = "error.register";
    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String OCCUPIED_LOGIN_ERROR = "error.login.occupied";
    private static final String ADDRESS_INPUT_ERROR = "error.address.input";
    private static final String URL_INPUT_ERROR = "error.url.input";
    private static final String FILE_INPUT_ERROR = "error.file.input";
    private static final int PICTURE_HEIGHT = 250;
    private static final int PICTURE_WIDTH = 250;
    private static final String PATH_ID = "1SIB7p5krDkontV99kQSwpMvmFZlNStJl";
    private static final String TEMPFILE_PATH = "resources/";
    private static final String GD_LINK = "https://drive.google.com/uc?export=view&id=";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(RequestParameters.LOGIN);
        String password = request.getParameter(RequestParameters.PASSWORD);
        String firstName = request.getParameter(RequestParameters.USER_FIRSTNAME);
        String lastName = request.getParameter(RequestParameters.USER_LASTNAME);
        String address = request.getParameter(RequestParameters.ADDRESS);
        try {
            if (address != null && !address.isEmpty()) {
                address = new AddressObject(address).getFormattedAddress();
            }
        } catch (AddressException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, ADDRESS_INPUT_ERROR);
            return new Router((JSPPages) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        }

        AddressObject addressObject;
        User user;
        URL link;

        try {
            link = CommandUtil.getBookLink(request, PICTURE_WIDTH, PICTURE_HEIGHT, PATH_ID);
            addressObject = new AddressObject(address);
        } catch (MalformedURLException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, URL_INPUT_ERROR);
            return new Router(JSPPages.ADD_AUTHOR_PAGE);
        } catch (IOException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, FILE_INPUT_ERROR);
            return new Router(JSPPages.ADD_AUTHOR_PAGE);
        } catch (GeneralSecurityException | ServletException e) {
            throw new CommandException(e);
        } catch (AddressException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, ADDRESS_INPUT_ERROR);
            return new Router(JSPPages.ADD_AUTHOR_PAGE);
        }

        try {
            new UserValidator().validate(firstName, lastName, login,
                    PasswordCoder.hash(password), addressObject, link, UserStatus.BUYER);
        } catch (ValidationException validationException) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, REGISTER_ERROR);
            return new Router((JSPPages) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        }
        try {
            if (UserService.getInstance().findBy(new UserFinder().findByLogin(login)).isEmpty()) {
                user = UserService.getInstance().create(firstName, lastName, login,
                        PasswordCoder.hash(password), address, link, UserStatus.BUYER);
            } else {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, OCCUPIED_LOGIN_ERROR);
                return new Router((JSPPages) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
            }
            request.getSession().setAttribute(SessionParameters.CURRENT_USER, new UserDTO(user));
            Router router = new Router();
            router.setRedirect(new URL(request.getRequestURL().toString()));
            return router;
        } catch (ServiceException | MalformedURLException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            return new Router(JSPPages.REGISTER_PAGE);
        }

    }
}
