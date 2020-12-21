package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        request.getSession().setAttribute(SessionParameters.USER, null);

        return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
    }
}
