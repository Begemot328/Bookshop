package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.genre.Genre;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.genre.GenreService;

import javax.servlet.http.HttpServletRequest;

public class GenreManageCommand implements Command {
    private final static String CREATE = "create";
    private final static String DELETE = "delete";
    private final static String UPDATE = "update";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        switch (request.getParameter(RequestParameters.GENRE_COMMAND).toLowerCase()) {
            case CREATE:
                String name = request.getParameter(RequestParameters.NAME);
                if (name != null
                        &&  !name.isEmpty()) {
                    try {
                        Genre newGenre = GenreService.getInstance().create(name);
                        request.getServletContext().setAttribute(RequestParameters.GENRES,
                                GenreService.getInstance().findAll().toArray(Genre[]::new));
                    } catch (ServiceException | DAOException e) {
                        throw new CommandException(e);
                    } catch (ValidationException e) {
                        request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.INPUT_ERROR);
                        return new Router(JSPPages.GENRE_MENU_PAGE);
                    }
                }
                break;
            case DELETE:
                try {
                    int id = Integer.parseInt(request.getParameter(RequestParameters.GENRE_ID));
                    GenreService.getInstance().delete(GenreService.getInstance().read(id));
                    request.getServletContext().setAttribute(RequestParameters.GENRES,
                            GenreService.getInstance().findAll().toArray(Genre[]::new));
                } catch (NumberFormatException e) {
                    request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.INPUT_ERROR);
                    return new Router(JSPPages.GENRE_MENU_PAGE);
                } catch (ServiceException | DAOException e) {
                    throw new CommandException(e);
                }
                break;
            case UPDATE:
                name = request.getParameter(RequestParameters.NAME);
                if (name != null
                        &&  !name.isEmpty()) {
                    try {
                        int id = Integer.parseInt(request.getParameter(RequestParameters.GENRE_ID));
                        Genre newGenre = GenreService.getInstance().read(id);
                        newGenre.setName(name);
                        GenreService.getInstance().update(newGenre);
                        request.getServletContext().setAttribute(RequestParameters.GENRES,
                                GenreService.getInstance().findAll().toArray(Genre[]::new));
                    } catch (ServiceException | DAOException e) {
                        throw new CommandException(e);
                    } catch (ValidationException e) {
                        request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.INPUT_ERROR);
                        return new Router(JSPPages.GENRE_MENU_PAGE);
                    }
                }
                break;
            default:
                return new Router(JSPPages.GENRE_MENU_PAGE);
        }
        return null;
    }
}
