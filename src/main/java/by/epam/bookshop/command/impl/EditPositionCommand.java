package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.service.shop.ShopService;
import by.epam.bookshop.validator.BookValidator;
import by.epam.bookshop.validator.PositionValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class EditPositionCommand implements Command {

    private static final String WRONG_AUTHOR_ERROR = "error.author.id";
    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String INPUT_ERROR = "error.input";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Position newPosition;
        String note = request.getParameter(RequestParameters.NOTE);
        int shopId = 0;
        int bookId = 0;
        int quantity;
        User currentUser = null;

        try {
            shopId = Integer.parseInt(request.getParameter(RequestParameters.SHOP_ID));
            bookId = Integer.parseInt(request.getParameter(RequestParameters.BOOK_ID));
            quantity = Integer.parseInt(request.getParameter(RequestParameters.QUANTITY));
        } catch (NumberFormatException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        }

        try {
            Shop shop = ShopService.getInstance().read(shopId);
            if (shop == null) {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, WRONG_AUTHOR_ERROR);
                return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
            }
            Book book = BookService.getInstance().read(bookId);
            if (book == null) {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, WRONG_AUTHOR_ERROR);
                return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
            }

            if (request.getSession().getAttribute(SessionParameters.CURRENT_USER) == null) {
                return new Router(JSPPages.LOGIN_PAGE);
            } else {
                currentUser = (User) request.getSession().getAttribute(SessionParameters.CURRENT_USER);
            }

            try {
                new PositionValidator().validate(book, shop, PositionStatus.READY, note, quantity);
            } catch (ValidationException e){
                request.setAttribute(RequestParameters.ERROR_MESSAGE, INPUT_ERROR);
                return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
            }
            newPosition = (Position) request.getSession().getAttribute(SessionParameters.POSITION);
                newPosition.setQuantity(quantity);
                newPosition.setShop(shop);
                newPosition.setBook(book);
                newPosition.setNote(note);
                PositionService.getInstance().update(newPosition);
                request.getSession().setAttribute(SessionParameters.POSITION, newPosition);
                return new Router(JSPPages.VIEW_POSITION_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
