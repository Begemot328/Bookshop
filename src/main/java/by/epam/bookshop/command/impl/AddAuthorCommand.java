package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.util.GoogleDriveUtil;
import by.epam.bookshop.util.ImageUtil;
import by.epam.bookshop.validator.AuthorValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class AddAuthorCommand implements Command {

    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String INPUT_ERROR = "error.input";
    private static final String FILE_INPUT_ERROR = "error.file.input";
    private static final String URL_INPUT_ERROR = "error.url.input";
    private static final int PICTURE_HEIGHT = 250;
    private static final int PICTURE_WIDTH = 250;
    private static final String PATH_ID = "1ACaioGkteNxu6IHd9J-ReQxJRdqADcXs";
    private static final String TEMPFILE_PATH = "resources/";
    private static final String GD_LINK = "https://drive.google.com/uc?export=view&id=";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Author newAuthor;
        String firstName = request.getParameter(RequestParameters.AUTHOR_FIRSTNAME);
        String lastName = request.getParameter(RequestParameters.AUTHOR_LASTNAME);
        String photoLink;
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
            request.setAttribute(RequestParameters.ERROR_MESSAGE, URL_INPUT_ERROR);
            return new Router(JSPPages.ADD_AUTHOR_PAGE);
        } catch (IOException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, FILE_INPUT_ERROR);
            return new Router(JSPPages.ADD_AUTHOR_PAGE);
        } catch (GeneralSecurityException | ServletException e) {
            throw new CommandException(e);
        }

        try {
            newAuthor = AuthorService.getInstance().create(firstName, lastName, link);
            request.getSession().setAttribute(SessionParameters.AUTHOR, newAuthor);
            return new Router(JSPPages.VIEW_AUTHOR_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            return new Router(JSPPages.ADD_AUTHOR_PAGE);
        }
    }
}
