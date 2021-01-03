package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.controller.dto.UserDTO;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.user.UserService;
import by.epam.bookshop.service.user.UserValidator;
import by.epam.bookshop.util.PasswordCoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class RegisterCommand implements Command {
    private static final String REGISTER_ERROR = "error.register";
    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String OCCUPIED_LOGIN_ERROR = "error.login.occupied";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(RequestParameters.LOGIN);
        String password  = request.getParameter(RequestParameters.PASSWORD);
        String firstName  = request.getParameter(RequestParameters.USER_FIRSTNAME);
        String lastName  = request.getParameter(RequestParameters.USER_LASTNAME);
        String address  = request.getParameter(RequestParameters.ADDRESS);
        String photoLink  = request.getParameter(RequestParameters.PHOTOLINK);
        User user;

        try {
            if (new UserValidator().validate(firstName, lastName, login,
                    PasswordCoder.code(password), address, photoLink, UserStatus.BUYER)) {
                if (UserService.getInstance().findBy(new UserFinder().findByLogin(login)).isEmpty()) {
                    user = UserService.getInstance().create(firstName, lastName, login,
                            PasswordCoder.code(password), address, photoLink, UserStatus.BUYER);
                } else {
                    request.setAttribute(RequestParameters.ERROR_MESSAGE, OCCUPIED_LOGIN_ERROR);
                    Router router = new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
                    return router;
                }
            } else {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, REGISTER_ERROR);
                Router router = new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
                return router;
            }
        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionParameters.ERROR_MESSAGE, e.getMessage()
                    + Arrays.toString(e.getStackTrace()));
            throw new CommandException(e);
        }
        request.getSession().setAttribute(SessionParameters.CURRENT_USER, new UserDTO(user));
        Router router = new Router();
        router.setRedirect(request.getRequestURL().toString());
        return router;
    }
}