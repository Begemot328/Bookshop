package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.validator.impl.BookValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Optional;

public class AddBookCommand implements Command {

    private static final String WRONG_AUTHOR_ERROR = "error.author.id";
    private static final int PICTURE_HEIGHT = 200;
    private static final int PICTURE_WIDTH = 150;
    private static final String PATH_ID = "1ACaioGkteNxu6IHd9J-ReQxJRdqADcXs";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Book newBook;
        String title = request.getParameter(RequestParameters.TITLE);
        String description = request.getParameter(RequestParameters.DESCRIPTION);
        int authorId = 0;
        float price = 0;
        Author author;
        URL link = null;

        try {
            link = CommandUtil.getBookLink(request, PICTURE_WIDTH, PICTURE_HEIGHT, PATH_ID);
        } catch (MalformedURLException e) {
            return tryAgain(request, ErrorMessages.URL_INPUT_ERROR);
        } catch (IOException e) {
            return tryAgain(request, ErrorMessages.FILE_INPUT_ERROR);
        } catch (GeneralSecurityException | ServletException e) {
            throw new CommandException(e);
        }

        try {
            authorId = Integer.parseInt(request.getParameter(RequestParameters.AUTHOR_ID));
            price = Float.parseFloat(request.getParameter(RequestParameters.PRICE));
            Optional<Author> authorOptional = AuthorService.getInstance().findBy(new AuthorFinder().findByID(authorId))
                    .stream().findAny();
            if (authorOptional.isEmpty()) {
                request.setAttribute(RequestParameters.AUTHORS, AuthorService.getInstance().findAll());
                request.setAttribute(RequestParameters.ERROR_MESSAGE, WRONG_AUTHOR_ERROR);
                return new Router(JSPPages.ADD_BOOK_PAGE);
            } else {
                author = authorOptional.get();
            }

            new BookValidator().validate(title, author, description, price, link);
            newBook = BookService.getInstance().create(title, author, description, price, link);
            request.getSession().setAttribute(SessionParameters.BOOK, newBook);
            return new Router(JSPPages.VIEW_BOOK_PAGE);
        } catch (NumberFormatException e) {
            return tryAgain(request, ErrorMessages.INPUT_ERROR);
        } catch (ServiceException | DAOException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            return tryAgain(request, e);
        }
    }

    private Router tryAgain(HttpServletRequest request, Exception e) throws CommandException {
        return tryAgain(request, e.getMessage());
    }

    private Router tryAgain(HttpServletRequest request, String error_message) throws CommandException {
        request.setAttribute(RequestParameters.ERROR_MESSAGE, error_message);
        try {
            request.setAttribute(RequestParameters.AUTHORS, AuthorService.getInstance().findAll());
        } catch (DAOException | ServiceException exception) {
            throw new CommandException(exception);
        }
        return new Router(JSPPages.ADD_BOOK_PAGE);
    }
}
