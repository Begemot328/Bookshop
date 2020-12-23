package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.position.PositionFinder;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.position.PositionService;

import javax.servlet.http.HttpServletRequest;

public class ViewBookCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.getParameter(RequestParameters.BOOK_ID);
        try {
            Book book = BookService.getInstance().read(Integer.parseInt(request.getParameter(RequestParameters.BOOK_ID)));
            request.getSession().setAttribute(SessionParameters.BOOK, book);
            Position[] positions = PositionService.getInstance().findBy(
                    new PositionFinder().findByBook(book.getId())).stream()
                    .filter(position -> position.getStatus() == PositionStatus.READY).toArray(Position[]::new);
            request.getSession().setAttribute(SessionParameters.POSITIONS, positions);
        } catch (ServiceException e) {
            return new Router(JSPPages.ERROR_PAGE);
        }

        return new Router(JSPPages.VIEW_BOOK_PAGE);
    }
}
