package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.entity.genre.Genre;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.genre.GenreService;
import org.mortbay.jetty.Request;

import javax.servlet.http.HttpServletRequest;

public class AddGenreCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter(RequestParameters.NAME);
        if (name != null && !name.isEmpty()) {
            try {
                Genre genre =
                        GenreService.getInstance().create(name);
                request.setAttribute(RequestParameters.GENRE_ID, genre.getId());
                request.getServletContext().setAttribute(RequestParameters.GENRES,
                        GenreService.getInstance().findAll());
                request.setAttribute(RequestParameters.GENRE_ID, genre.getId());
                return new FindBooksCommand().execute(request);
            } catch (DAOException | ServiceException | ValidationException e) {
                throw new CommandException(e);
            }
        }
        return new Router(JSPPages.ADD_GENRE_PAGE);
    }
}
