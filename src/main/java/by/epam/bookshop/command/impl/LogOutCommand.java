package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        request.getSession().setAttribute(SessionParameters.CURRENT_USER, null);
        if (request.getSession().getAttribute(SessionParameters.LAST_PAGE) == null) {
            return new Router(JSPPages.START_PAGE);
        }
        return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
    }
}
