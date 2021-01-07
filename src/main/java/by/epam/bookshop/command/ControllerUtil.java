package by.epam.bookshop.command;

import by.epam.bookshop.service.book.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class ControllerUtil {
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
}
