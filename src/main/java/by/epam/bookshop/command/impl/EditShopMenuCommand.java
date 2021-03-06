package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.shop.ShopService;

import javax.servlet.http.HttpServletRequest;

public class EditShopMenuCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        try {
            Shop shop = ShopService.getInstance().read(Integer.parseInt(
                    request.getParameter(RequestParameters.SHOP_ID)));
            request.setAttribute(RequestParameters.SHOP, shop);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.EDIT_SHOP_PAGE);
    }
}
