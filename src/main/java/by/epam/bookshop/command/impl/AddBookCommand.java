package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.util.GoogleDriveUtil;
import by.epam.bookshop.util.ImageUtil;
import by.epam.bookshop.validator.BookValidator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class AddBookCommand implements Command {

    private static final String WRONG_AUTHOR_ERROR = "error.author.id";
    private static final String INPUT_ERROR = "error.input";
    private static final String URL_INPUT_ERROR = "error.url.input";
    private static final String FILE_INPUT_ERROR = "error.file.input";
    private static final int PICTURE_HEIGHT = 200;
    private static final int PICTURE_WIDTH = 150;
    private static final String PATH_ID = "1ACaioGkteNxu6IHd9J-ReQxJRdqADcXs";
    private static final String TEMPFILE_PATH = "resources/";
    private static final String GD_LINK = "https://drive.google.com/uc?export=view&id=";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Book newBook;
        String title = request.getParameter(RequestParameters.TITLE);
        String description = request.getParameter(RequestParameters.DESCRIPTION);
        String photoLink;
        int authorId = 0;
        float price = 0;
        Author author;


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
            authorId = Integer.parseInt(request.getParameter(RequestParameters.AUTHOR_ID));
            price = Float.parseFloat(request.getParameter(RequestParameters.PRICE));
            Optional<Author> authorOptional = AuthorService.getInstance().findBy(new AuthorFinder().findByID(authorId))
                    .stream().findAny();
            if (authorOptional.isEmpty()) {
                request.setAttribute(RequestParameters.AUTHORS, AuthorService.getInstance().findAll());
                request.setAttribute(RequestParameters.ERROR_MESSAGE, WRONG_AUTHOR_ERROR);
                return new Router(JSPPages.ADD_BOOK_PAGE);
            } else {
                author = authorOptional.get();
            }

            new BookValidator().validate(title, author, description, price, link);
            newBook = BookService.getInstance().create(title, author, description, price, link);
            request.getSession().setAttribute(SessionParameters.BOOK, newBook);
            return new Router(JSPPages.VIEW_BOOK_PAGE);
        } catch (NumberFormatException e) {
            return tryAgain(request, INPUT_ERROR);
        } catch (ServiceException | DAOException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            return tryAgain(request, e);
        }
    }

    private Router tryAgain(HttpServletRequest request, Exception e) throws CommandException {
        request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
        try {
            request.setAttribute(RequestParameters.AUTHORS, AuthorService.getInstance().findAll());
        } catch (DAOException | ServiceException exception) {
            throw new CommandException(exception);
        }
        return new Router(JSPPages.ADD_BOOK_PAGE);
    }

    private Router tryAgain(HttpServletRequest request, String error_message) throws CommandException {
        request.setAttribute(RequestParameters.ERROR_MESSAGE, error_message);
        try {
            request.setAttribute(RequestParameters.AUTHORS, AuthorService.getInstance().findAll());
        } catch (DAOException | ServiceException exception) {
            throw new CommandException(exception);
        }
        return new Router(JSPPages.ADD_BOOK_PAGE);
    }
}
