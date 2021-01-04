package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class EditBookMenuCommand implements Command {
    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String DAO_EXCEPTION = "DAO Exception: ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        try {
            if (request.getSession().getAttribute(SessionParameters.BOOK) == null) {
                Book book = BookService.getInstance().read(Integer.parseInt(
                        request.getParameter(RequestParameters.BOOK_ID)));
                request.getSession().setAttribute(SessionParameters.BOOK, book);
            }
            Author[] authors = AuthorService.getInstance().findAll().toArray(Author[]::new);
            request.getSession().setAttribute(SessionParameters.AUTHORS, authors);
            Paginator.paginate(request, authors, 1);
        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionParameters.ERROR_MESSAGE, e.getMessage()
                    + Arrays.toString(e.getStackTrace()));
            throw new CommandException(e);
        } catch (DAOException e) {
            request.getSession().setAttribute(SessionParameters.ERROR_MESSAGE, e.getMessage()
                    + Arrays.toString(e.getStackTrace()));
            throw new CommandException(e);
        }
        return new Router(JSPPages.EDIT_BOOK_PAGE);
    }
}