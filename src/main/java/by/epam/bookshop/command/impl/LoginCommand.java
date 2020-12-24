package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.controller.dto.UserDTO;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServletRuntimeException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.user.UserService;
import by.epam.bookshop.util.PasswordCoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final String LOGIN_ERROR = "error.login";
    private static final String SERVICE_EXCEPTION = "Service Exception: ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(RequestParameters.LOGIN);
        String password  = request.getParameter(RequestParameters.PASSWORD);
        Optional<User> user;
        try {
            user = UserService.getInstance().findBy(new UserFinder().findByLogin(login)).stream().findAny();
            if (!user.isPresent() || user.get().getPassword() != PasswordCoder.code(password)) {

                request.setAttribute(RequestParameters.ERROR_MESSAGE, LOGIN_ERROR);

                Router router = new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
                return router;
               // throw new ServletRuntimeException(LOGIN_ERROR);
            }
        } catch (ServiceException e) {
            throw new CommandException(SERVICE_EXCEPTION, e);
        }

        request.getSession().setAttribute(SessionParameters.USER, new UserDTO(user.get()));
        Router router = new Router();
        router.setRedirect(request.getRequestURL().toString());
        return router;
    }
}
