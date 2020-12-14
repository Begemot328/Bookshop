package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.book.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collector;

public class FindAllBooksCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        try {
            Book[] books = new Book[50];
            Arrays.fill(books, BookService.getInstance().findAll().stream().findAny().get());
            request.setAttribute("books",books);
        } catch (DAOException|ServiceException e) {
            return new Router(JSPPages.ERROR_PAGE);
        }
        return new Router(JSPPages.SEARCH_BOOKS);
    }
}
