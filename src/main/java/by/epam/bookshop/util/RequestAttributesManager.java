package by.epam.bookshop.util;

import javax.servlet.ServletRequest;
import java.io.ObjectStreamClass;
import java.util.Enumeration;
import java.util.stream.Stream;

public class RequestAttributesManager {

    public static void copy(ServletRequest initialRequest, ServletRequest targetRequest) {
        Enumeration<String> attributeNames =  targetRequest.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String key = attributeNames.nextElement();
            targetRequest.setAttribute(key, initialRequest.getAttribute(key));
        }

    }
}
