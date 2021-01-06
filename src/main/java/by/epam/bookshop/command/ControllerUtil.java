package by.epam.bookshop.command;

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
}
