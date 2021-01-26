package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.validator.impl.PositionValidator;
import by.epam.bookshop.service.shop.ShopService;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AddPositionCommand implements Command {

    private static final String WRONG_AUTHOR_ERROR = "error.author.id";

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
            request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.INPUT_ERROR);
            return new Router((JSPPages) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        }

        try {
            Shop shop = ShopService.getInstance().read(shopId);
            if (shop == null) {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, WRONG_AUTHOR_ERROR);
                return new Router((JSPPages) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
            }
            Book book = BookService.getInstance().read(bookId);
            if (book == null) {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, WRONG_AUTHOR_ERROR);
                return new Router((JSPPages) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
            }

            if (request.getSession().getAttribute(SessionParameters.CURRENT_USER) == null) {
                return new Router(JSPPages.LOGIN_PAGE);
            } else {
                currentUser = (User) request.getSession().getAttribute(SessionParameters.CURRENT_USER);
            }

            try {
                new PositionValidator().validate(book, shop, PositionStatus.READY, note, quantity);
            } catch (ValidationException e){
                request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.INPUT_ERROR);
                return new Router((JSPPages) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
            }


            newPosition = PositionService.getInstance().createPosition(
                    currentUser, book, shop, note, quantity);

            Map<String, String> parameters = new HashMap<>();
        /*    parameters.put(RequestParameters.COMMAND, CommandEnum.VIEW_POSITION_COMMAND.toString());
            parameters.put(RequestParameters.POSITION_ID, Integer.toString(newPosition.getId()));
            return new Router(new URL(CommandUtil.getURL(
                    request.getRequestURL().toString(), parameters))); */
            return new Router(new URL(request.getRequestURL().toString()));
        } catch (ServiceException | MalformedURLException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            return new Router(JSPPages.ADD_POSITION_PAGE);
        }
    }
}
