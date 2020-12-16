package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServletRuntimeException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String LOGIN_ERROR = "wrong password or login";
    private static final String SERVICE_EXCEPTION = "Service Exception: ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password  = request.getParameter(PASSWORD);
        Optional<User> user;
        try {
            user = UserService.getInstance().findBy(new UserFinder().findByLogin(login)).stream().findAny();
            if (!user.isPresent() || user.get().getPassword() != password.hashCode()) {
                throw new ServletRuntimeException(LOGIN_ERROR);
            }
        } catch (ServiceException e) {
            throw new CommandException(SERVICE_EXCEPTION, e);
        }

        request.getSession().setAttribute(SessionParameters.USER, user.get());
        return new Router(JSPPages.START_PAGE);
    }
}
