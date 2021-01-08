package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.AddressException;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.shop.ShopService;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.validator.AuthorValidator;
import by.epam.bookshop.validator.ShopValidator;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class AddShopCommand implements Command {

    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String INPUT_ERROR = "error.input";
    private static final String WRONG_ENTITY = "wrong.entity.error";
    private static final String ADDRESS_INPUT_ERROR = "error.address.input";
    private static final String URL_INPUT_ERROR = "error.url.input";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Shop newShop;
        String address = request.getParameter(RequestParameters.ADDRESS);

        String name = request.getParameter(RequestParameters.SHOP_NAME);
        String photoLink = request.getParameter(RequestParameters.PHOTOLINK);
        AddressObject addressObject;
        URL link;
        try {
            if (photoLink == null || photoLink.isEmpty()) {
                link = null;
            } else {
                link = new URL(photoLink);
            }
            addressObject = new AddressObject(address);
        } catch (AddressException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, ADDRESS_INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        } catch (MalformedURLException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, URL_INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        }

        try {
            new ShopValidator().validate(name, addressObject, link);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        }
        try {
            newShop = ShopService.getInstance().create(name, addressObject, link);
            request.getSession().setAttribute(SessionParameters.SHOP, newShop);
            return new Router(JSPPages.VIEW_SHOP_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            return new Router(JSPPages.ADD_SHOP_PAGE);
        }
    }
}
