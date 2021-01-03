package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.dao.impl.shop.ShopFinder;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.shop.ShopService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class FindShopsCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ShopFinder finder = new ShopFinder();

        if (isNotEmpty(request.getParameter(RequestParameters.SHOP_NAME))) {
            finder = finder.findByName(request.getParameter(RequestParameters.SHOP_NAME));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR))) {
            finder = finder.findByAdress(request.getParameter(RequestParameters.SHOP_ADRESS));
        }
        try {
            request.getSession().setAttribute(
                    SessionParameters.SHOPS,
                    ShopService.getInstance().findBy(finder).toArray());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.SEARCH_SHOPS_PAGE);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
