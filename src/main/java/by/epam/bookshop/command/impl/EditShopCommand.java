package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.AddressException;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.shop.ShopService;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.validator.impl.ShopValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

public class EditShopCommand implements Command {

    private static final int PICTURE_HEIGHT = 500;
    private static final int PICTURE_WIDTH = 500;
    private static final String PATH_ID = "1nfC7esCSgn3fS0MlorqM1pItDRxknOke";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Shop newShop;
        AddressObject addressObject = null;
        URL link = null;

        String name = request.getParameter(RequestParameters.SHOP_NAME);
        String photoLink = request.getParameter(RequestParameters.PHOTOLINK);
        String address = request.getParameter(RequestParameters.ADDRESS);
        try {

            link = CommandUtil.getBookLink(request, PICTURE_WIDTH, PICTURE_HEIGHT, PATH_ID);
            addressObject = new AddressObject(address);

        } catch (AddressException e) {
            return tryAgain(request, ErrorMessages.ADDRESS_INPUT_ERROR);
        } catch (MalformedURLException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.URL_INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        } catch (IOException e) {
            return tryAgain(request, ErrorMessages.FILE_INPUT_ERROR);
        } catch (GeneralSecurityException | ServletException e) {
            throw new CommandException(e);
        }

        try {
            new ShopValidator().validate(name, addressObject, link);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, ErrorMessages.INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        }

        if (request.getSession().getAttribute(SessionParameters.SHOP) instanceof Shop) {
            newShop = (Shop) request.getSession().getAttribute(SessionParameters.SHOP);
        } else {
            throw new CommandException(ErrorMessages.WRONG_ENTITY);
        }
        newShop.setAddress(addressObject);
        newShop.setName(name);
        newShop.setPhotoLink(link);

        try {
            ShopService.getInstance().update(newShop);
            request.getSession().setAttribute(SessionParameters.SHOP, newShop);
            return new Router(JSPPages.VIEW_SHOP_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            return new Router(JSPPages.EDIT_SHOP_PAGE);
        }
    }

    private Router tryAgain(HttpServletRequest request, Exception e) throws CommandException {
        return tryAgain(request, e.getMessage());
    }

    private Router tryAgain(HttpServletRequest request, String error_message) throws CommandException {
        request.setAttribute(RequestParameters.ERROR_MESSAGE, error_message);
        return new Router(JSPPages.EDIT_SHOP_PAGE);
    }
}
