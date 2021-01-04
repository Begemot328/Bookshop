package by.epam.bookshop.command;

import javax.servlet.http.HttpServletRequest;

public class Paginator {

    private static final int ELEMENTS_PER_PAGE = 30;

    public static void paginate(HttpServletRequest request, int currentPage) {
        paginate(request, (Object[]) request.getSession().getAttribute(SessionParameters.ARRAY), currentPage);
    }

    public static void paginate(HttpServletRequest request, Object[] array, int currentPage) {
        int pageQuantity = array.length / ELEMENTS_PER_PAGE;
        if (((double) array.length) / ELEMENTS_PER_PAGE != pageQuantity) {
            pageQuantity++;
        }

        int firstElement = (currentPage - 1) * ELEMENTS_PER_PAGE;
        int lastElement = (currentPage) * ELEMENTS_PER_PAGE - 1;
        if (lastElement > array.length - 1) {
            lastElement = array.length - 1;
        }

        request.getSession().setAttribute(SessionParameters.ARRAY, array);
        request.getSession().setAttribute(SessionParameters.CURRENT_PAGE, Integer.valueOf(currentPage));
        request.getSession().setAttribute(SessionParameters.PAGE_ELEMENTS, Integer.valueOf(ELEMENTS_PER_PAGE));
        request.getSession().setAttribute(SessionParameters.PAGE_QUANTITY, Integer.valueOf(pageQuantity));
        request.getSession().setAttribute(SessionParameters.LAST_ELEMENT, Integer.valueOf(lastElement));
        request.getSession().setAttribute(SessionParameters.FIRST_ELEMENT, Integer.valueOf(firstElement));
    }
}
