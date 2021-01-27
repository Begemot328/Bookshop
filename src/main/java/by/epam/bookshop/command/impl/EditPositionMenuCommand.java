package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.command.Router;
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

public class EditPositionMenuCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            Position position = PositionService.getInstance().read(Integer.parseInt(
                    request.getParameter(RequestParameters.POSITION_ID)));
            request.setAttribute(RequestParameters.POSITION, position);
            Book[] books = BookService.getInstance().findAll().toArray(Book[]::new);
            request.setAttribute(RequestParameters.BOOKS, books);
            Shop[] shops = ShopService.getInstance().findAll().toArray(Shop[]::new);
            request.setAttribute(RequestParameters.SHOPS, shops);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (DAOException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.EDIT_POSITION_PAGE);
    }
}
