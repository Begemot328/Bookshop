package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Paginator;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class AddBookMenuCommand implements Command {
    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String DAO_EXCEPTION = "DAO Exception: ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            Author[] authors = AuthorService.getInstance().findAll().toArray(Author[]::new);
            request.getSession().setAttribute(SessionParameters.AUTHORS, authors);
            Paginator.paginate(request, authors, 1);
        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionParameters.ERROR_MESSAGE, e.getMessage()
                    + Arrays.toString(e.getStackTrace()));
            throw new CommandException(SERVICE_EXCEPTION, e);
        } catch (DAOException e) {
            request.getSession().setAttribute(SessionParameters.ERROR_MESSAGE, e.getMessage()
                    + Arrays.toString(e.getStackTrace()));
            throw new CommandException(DAO_EXCEPTION, e);
        }
        return new Router(JSPPages.ADD_BOOK_PAGE);
    }
}
