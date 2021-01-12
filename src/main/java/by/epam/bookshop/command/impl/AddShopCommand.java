package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.*;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.shop.ShopService;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.util.GoogleDriveUtil;
import by.epam.bookshop.util.ImageUtil;
import by.epam.bookshop.validator.AuthorValidator;
import by.epam.bookshop.validator.ShopValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class AddShopCommand implements Command {

    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String INPUT_ERROR = "error.input";
    private static final String WRONG_ENTITY = "wrong.entity.error";
    private static final String ADDRESS_INPUT_ERROR = "error.address.input";
    private static final String URL_INPUT_ERROR = "error.url.input";
    private static final String FILE_INPUT_ERROR = "error.file.input";
    private static final int PICTURE_HEIGHT = 500;
    private static final int PICTURE_WIDTH = 500;
    private static final String PATH_ID = "1ACaioGkteNxu6IHd9J-ReQxJRdqADcXs";
    private static final String TEMPFILE_PATH = "resources/";
    private static final String GD_LINK = "https://drive.google.com/uc?export=view&id=";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Shop newShop;
        String address = request.getParameter(RequestParameters.ADDRESS);

        String name = request.getParameter(RequestParameters.SHOP_NAME);
        String photoLink = request.getParameter(RequestParameters.PHOTOLINK);
        AddressObject addressObject = null;
        URL link = null;
        String fileString = null;
        File file = null;

        try {
            Part filePart = null; // Retrieves <input type="file" name="file">
            filePart = request.getPart(RequestParameters.FILE);
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            if (!fileName.isEmpty()) {
                InputStream fileInputStream = filePart.getInputStream();
                String filePath = request.getServletContext().getRealPath("/") + TEMPFILE_PATH + fileName;
                file = new File(filePath);
                OutputStream outStream = new FileOutputStream(file);
                outStream.write(fileInputStream.readAllBytes());
                outStream.close();
                fileInputStream.close();

                File resizedFile = ImageUtil.resizeImage(file, PICTURE_WIDTH, PICTURE_HEIGHT);

                if (file.exists()) {
                    fileString = GoogleDriveUtil.transferFile(resizedFile, PATH_ID);
                    link = new URL(fileString);
                }
                file.delete();
            } else if (request.getPart(RequestParameters.PHOTOLINK) != null) {
                photoLink = request.getParameter(RequestParameters.PHOTOLINK);
                if (!photoLink.contains(GD_LINK)) {
                    photoLink = GoogleDriveUtil.transferFile(photoLink, PATH_ID,
                            ImageUtil.getFunction(PICTURE_WIDTH, PICTURE_HEIGHT));
                }
                link = new URL(photoLink);
            }
        } catch (MalformedURLException e) {
            return tryAgain(request, URL_INPUT_ERROR);
        } catch (IOException e) {
            return tryAgain(request, FILE_INPUT_ERROR);
        } catch (GeneralSecurityException | ServletException e) {
            throw new CommandException(e);
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

    private Router tryAgain(HttpServletRequest request, Exception e) throws CommandException {
        return tryAgain(request, e.getMessage());
    }

    private Router tryAgain(HttpServletRequest request, String error_message) throws CommandException {
        request.setAttribute(RequestParameters.ERROR_MESSAGE, error_message);
        return new Router(JSPPages.ADD_SHOP_PAGE);
    }
}
