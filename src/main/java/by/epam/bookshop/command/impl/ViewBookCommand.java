package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.dao.impl.position.PositionFinder;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.position.PositionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class ViewBookCommand implements Command {
    private static final int ELEMENTS_PER_PAGE = 30;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        int page;
        try {
            page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        try {
            Book book = BookService.getInstance().read(
                    Integer.parseInt(request.getParameter(RequestParameters.BOOK_ID)));
            request.getSession().setAttribute(SessionParameters.BOOK, book);

            PositionFinder finder = new PositionFinder().findByBook(book.getId());
            Position[] positions = PositionService.getInstance().findBy(finder)
                    .toArray(Position[]::new);

            int pageQuantity = ControllerUtil.pageQuantity(PositionService.getInstance().countBy(finder),
                    ELEMENTS_PER_PAGE);

             positions = Arrays.stream(positions)
                    .filter(position -> position.getStatus() == PositionStatus.READY
                            || position.getStatus() == PositionStatus.RESERVED)
                     .toArray(Position[]::new);
            Shop[] shops =  Arrays.stream(positions)
                    .map(position -> position.getShop())
                    .distinct().toArray(Shop[]::new);

            request.setAttribute(RequestParameters.PAGE_QUANTITY, pageQuantity);
            request.setAttribute(RequestParameters.CURRENT_PAGE, page);
            request.setAttribute(RequestParameters.POSITIONS, positions);
            request.setAttribute(RequestParameters.SHOPS, shops);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(JSPPages.VIEW_BOOK_PAGE);
    }
}
