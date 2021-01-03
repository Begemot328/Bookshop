package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.command.SessionParameters;
import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ChangeLocaleCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        request.getSession().setAttribute(SessionParameters.LANGUAGE,
                request.getParameter(RequestParameters.LANGUAGE));
        Router router = new Router(
                (String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        return router;
    }
}
