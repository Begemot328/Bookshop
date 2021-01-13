package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.shop.ShopFinder;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.shop.ShopService;

import javax.servlet.http.HttpServletRequest;

public class FindShopsCommand implements Command {
    private static final int ELEMENTS_PER_PAGE = 30;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ShopFinder finder = new ShopFinder();
        int page;
        try {
            page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        if (isNotEmpty(request.getParameter(RequestParameters.SHOP_NAME))) {
            finder = finder.findByName(request.getParameter(RequestParameters.SHOP_NAME));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.AUTHOR))) {
            finder = finder.findByAdress(request.getParameter(RequestParameters.SHOP_ADRESS));
        }
        try {
            int pageQuantity = CommandUtil.pageQuantity(ShopService.getInstance().countBy(finder),
                    ELEMENTS_PER_PAGE);

            request.setAttribute(
                    RequestParameters.SHOPS,
                    ShopService.getInstance().findBy(finder).toArray(Shop[]::new));
            request.setAttribute(RequestParameters.PAGE_QUANTITY, pageQuantity);
            request.setAttribute(RequestParameters.CURRENT_PAGE, page);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.SEARCH_SHOPS_PAGE);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
