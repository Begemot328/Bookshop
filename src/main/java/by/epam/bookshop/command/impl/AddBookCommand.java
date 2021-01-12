package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.CommandException;
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
    private static final String SERVICE_EXCEPTION = "Service Exception: ";
    private static final String INPUT_ERROR = "error.input";
    private static final String URL_INPUT_ERROR = "error.url.input";
    private static final String FILE_INPUT_ERROR = "error.file.input";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Book newBook;
        String title = request.getParameter(RequestParameters.TITLE);
        String description = request.getParameter(RequestParameters.DESCRIPTION);
        String photoLink = request.getParameter(RequestParameters.PHOTOLINK);

        String fileString = request.getParameter(RequestParameters.FILE);
        URL link = null;
        int authorId = 0;
        float price = 0;
        Author author;

        File file = null;

        Part filePart = null; // Retrieves <input type="file" name="file">
        try {
            filePart = request.getPart("file");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            InputStream fileContent = filePart.getInputStream();

            file = new File("c:/temp/" + fileName);
            //boolean newFile = file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            outStream.write(fileContent.readAllBytes());
            outStream.close();
            fileContent.close();

            File resizedFile = ImageUtil.resizeImage(file, 150, 200);

            if (file.exists()) {
                fileString = GoogleDriveUtil.transferFile(resizedFile, "1ACaioGkteNxu6IHd9J-ReQxJRdqADcXs");
                link = new  URL(fileString);
            }
            file.delete();
            authorId  = Integer.parseInt(request.getParameter(RequestParameters.AUTHOR_ID));
            price  = Float.parseFloat(request.getParameter(RequestParameters.PRICE));
         /*   if (photoLink == null || photoLink.isEmpty()) {
                link = null;
            } else {
                link = new URL(photoLink);
            } */
        } catch (NumberFormatException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        } catch (MalformedURLException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, URL_INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, FILE_INPUT_ERROR);
            return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        } catch (ServletException e) {
            e.printStackTrace();
        }

        try {
            Optional<Author> authorOptional = AuthorService.getInstance().findBy(new AuthorFinder().findByID(authorId))
                    .stream().findAny();
            if (authorOptional.isEmpty()) {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, WRONG_AUTHOR_ERROR);
                return new Router(JSPPages.ADD_BOOK_PAGE);
            } else {
                author = authorOptional.get();
            }

            try {
                new BookValidator().validate(title, author, description, price, link);

            } catch (ValidationException e){
                request.setAttribute(RequestParameters.ERROR_MESSAGE, INPUT_ERROR);
                return new Router((String) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
            }

            newBook = BookService.getInstance().create(title, author, description, price, link);
            request.getSession().setAttribute(SessionParameters.BOOK, newBook);
            return new Router(JSPPages.VIEW_BOOK_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            return new Router(JSPPages.ADD_BOOK_PAGE);
        }
    }
}
