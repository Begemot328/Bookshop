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
import java.util.Map;

/**
 * Class of the servlet processing utils
 *
 * @author Yury Zmushko
 * @version 1.0
 */
public class CommandUtil {

    private static final String TEMPFILE_PATH = "resources/";
    private static final String GD_LINK = "https://drive.google.com/uc?export=view&id=";

    /**
     * Transfers request parameters to request attributes to be able to operate them in JSP servlet.
     *
     * @param request {@link HttpServletRequest} to handle
     * @see HttpServletRequest
     */
    public static void transferParameter(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String element = enumeration.nextElement();
            request.setAttribute(element, request.getParameter(element));
        }
    }

    /**
     * Simple method to calculate quantity of pages for pagination
     *
     * @param elements quantity of elements
     * @param elementsPerPage quantity of elements per page
     */
    public static int pageQuantity(int elements, int elementsPerPage) {
        int pageQuantity =elements / elementsPerPage;
        if (elements % elementsPerPage != 0) {
            pageQuantity++;
        } else if (pageQuantity <= 0) {
            pageQuantity = 1;
        }
        return pageQuantity;
    }

    /**
     * Simple method to calculate quantity of pages for pagination
     *
     * @param width target image width
     * @param height target image height
     * @param pathId ID of target path at DriveGoogle
     * @param request {@link HttpServletRequest} to handle
     * @see HttpServletRequest
     */
    public static URL getBookLink(HttpServletRequest request, int width, int height, String pathId)
            throws GeneralSecurityException, IOException, ServletException {
        String photoLink;
        URL link = null;
        String fileString = null;
        File file = null;
        Part filePart = null;
        // Retrieves <input type="file" name="file">
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

    public static String getURL(String baseURL, final Map<String, String> params) {
        StringBuilder builder = new StringBuilder(baseURL);
        builder.append("?");
        for (String key :params.keySet()) {
            builder.append(key).append("=").append(params.get(key).replace(" ", "%20")).append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
