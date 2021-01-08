package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.validator.BookValidator;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class EditBookCommand implements Command {

    private static final String WRONG_AUTHOR_ERROR = "error.author.id";
    private static final String INPUT_ERROR = "error.input";
    private static final String WRONG_ENTITY = "wrong.entity.error";
    private static final String URL_INPUT_ERROR = "error.url.input";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Book newBook;
        String title = request.getParameter(RequestParameters.TITLE);
        String description = request.getParameter(RequestParameters.DESCRIPTION);
        String photoLink = request.getParameter(RequestParameters.PHOTOLINK);
        URL link;
        int authorId = 0;
        float price;
        Author author;

        try {
            authorId  = Integer.parseInt(request.getParameter(RequestParameters.AUTHOR_ID));
            price  = Float.parseFloat(request.getParameter(RequestParameters.PRICE));
            if (photoLink == null || photoLink.isEmpty()) {
                link = null;
            } else {
                link = new URL(photoLink);
            }
        } catch (NumberFormatException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        } catch (MalformedURLException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, URL_INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        }

        try {
            Optional<Author> authorOptional = AuthorService.getInstance().findBy(
                    new AuthorFinder().findByID(authorId))
                    .stream().findAny();
            if (authorOptional.isEmpty()) {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, WRONG_AUTHOR_ERROR);
                return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
            } else {
                author = authorOptional.get();
            }

            try {
                new BookValidator().validate(title, author, description, price, link);
            } catch (ValidationException e) {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, INPUT_ERROR);
                return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
            }
            if (request.getSession().getAttribute(SessionParameters.BOOK) instanceof Book) {
                newBook = (Book) request.getSession().getAttribute(SessionParameters.BOOK);
            } else {
                throw new CommandException(WRONG_ENTITY);
            }
                newBook.setAuthor(author);
                newBook.setTitle(title);
                newBook.setPrice(price);
                newBook.setDescription(description);
                newBook.setPhotoLink(link);
                BookService.getInstance().update(newBook);
                request.getSession().setAttribute(SessionParameters.BOOK, newBook);
                return new Router(JSPPages.VIEW_BOOK_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            return new Router(JSPPages.EDIT_BOOK_PAGE);
        }
    }
}
