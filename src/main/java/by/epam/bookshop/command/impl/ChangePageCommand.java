package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.Paginator;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ChangePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Paginator.paginate(request, Integer.valueOf(request.getParameter(RequestParameters.PAGE)));
        Router router = new Router(
                (String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        return router;
    }
}
