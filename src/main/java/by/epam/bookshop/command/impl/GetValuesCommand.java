package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;

public class GetValuesCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        System.out.println(request.getParameter(RequestParameters.COMMAND));
        for (String name : request.getParameterValues("authorId")
             ) {
            System.out.println(name);
        }

        return new Router(JSPPages.AUTHORS_PAGE);
    }
}
