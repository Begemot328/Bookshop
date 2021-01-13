package by.epam.bookshop.command;

import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.util.GoogleDriveUtil;
import by.epam.bookshop.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Enumeration;

public class CommandUtil {

    private static final String TEMPFILE_PATH = "resources/";
    private static final String GD_LINK = "https://drive.google.com/uc?export=view&id=";


    public static void transferParameter(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String element = enumeration.nextElement();
            request.setAttribute(element, request.getParameter(element));
        }
    }

    public static int pageQuantity(int elements, int elementsPerPage) {
        int pageQuantity =elements / elementsPerPage;
        if (elements % elementsPerPage != 0) {
            pageQuantity++;
        } else if (pageQuantity <= 0) {
            pageQuantity = 1;
        }
        return pageQuantity;
    }

    public static URL getBookLink(HttpServletRequest request, int width, int height, String pathId)
            throws GeneralSecurityException, IOException, ServletException {
        String photoLink;
        URL link = null;
        String fileString = null;
        File file = null;
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

            File resizedFile = ImageUtil.resizeImage(file, width, height);

            if (file.exists()) {
                fileString = GoogleDriveUtil.transferFile(resizedFile, pathId);
                link = new URL(fileString);
            }
            file.delete();
            resizedFile.delete();
        } else if (request.getParameter(RequestParameters.PHOTOLINK) != null
                && !request.getParameter(RequestParameters.PHOTOLINK).isEmpty()) {
            photoLink = request.getParameter(RequestParameters.PHOTOLINK);
            if (!photoLink.contains(GD_LINK)) {
                photoLink = GoogleDriveUtil.transferFile(photoLink, pathId,
                        ImageUtil.getFunction(width, height));
            }
            link = new URL(photoLink);
        }
        return link;
    }
}
