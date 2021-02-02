package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ChangeLocaleCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        request.getSession().setAttribute(SessionParameters.LANGUAGE,
                request.getParameter(RequestParameters.LANGUAGE));
        if (request.getSession().getAttribute(SessionParameters.LAST_GET_REQUEST) != null
        && request.getSession().getAttribute(SessionParameters.LAST_GET_REQUEST) instanceof HttpServletRequest) {
            request = (HttpServletRequest) request.getSession().getAttribute(SessionParameters.LAST_GET_REQUEST);
            Command command = CommandEnum.getCommand(request.getParameter(RequestParameters.COMMAND));
            return command.execute(request);
        } else {
            return new Router(JSPPages.START_PAGE);
        }

    }
}
