package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ChangeLocaleCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        request.getSession().setAttribute(SessionParameters.LANGUAGE,
                request.getParameter(RequestParameters.LANGUAGE));
        Router router = new Router(
                JSPPages.START_PAGE);
        return router;
    }
}
