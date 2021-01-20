package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.genre.Genre;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.genre.GenreService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class AddBookMenuCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            Author[] authors = AuthorService.getInstance().findAll().toArray(Author[]::new);
            request.setAttribute(RequestParameters.AUTHORS, authors);
        } catch (ServiceException | DAOException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.ADD_BOOK_PAGE);
    }
}
