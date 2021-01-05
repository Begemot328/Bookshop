package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.service.shop.ShopService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class EditPositionMenuCommand implements Command {
    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String DAO_EXCEPTION = "DAO Exception: ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            if (request.getSession().getAttribute(SessionParameters.POSITION) == null) {
                Position position = PositionService.getInstance().read(Integer.parseInt(
                        request.getParameter(RequestParameters.POSITION_ID)));
                request.getSession().setAttribute(SessionParameters.POSITION, position);
            }
            Book[] books = BookService.getInstance().findAll().toArray(Book[]::new);
            request.getSession().setAttribute(SessionParameters.BOOKS, books);
            Shop[] shops = ShopService.getInstance().findAll().toArray(Shop[]::new);
            request.getSession().setAttribute(SessionParameters.SHOPS, shops);
        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionParameters.ERROR_MESSAGE, e.getMessage()
                    + Arrays.toString(e.getStackTrace()));
            throw new CommandException(e);
        } catch (DAOException e) {
            request.getSession().setAttribute(SessionParameters.ERROR_MESSAGE, e.getMessage()
                    + Arrays.toString(e.getStackTrace()));
            throw new CommandException(e);
        }
        return new Router(JSPPages.EDIT_POSITION_PAGE);
    }
}
