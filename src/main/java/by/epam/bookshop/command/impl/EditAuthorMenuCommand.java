package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;

import javax.servlet.http.HttpServletRequest;

public class EditAuthorMenuCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        try {
            Author author = AuthorService.getInstance().read(Integer.parseInt(
                    request.getParameter(RequestParameters.AUTHOR_ID)));
            request.setAttribute(RequestParameters.AUTHOR, author);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.EDIT_AUTHOR_PAGE);
    }
}
