package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.UnknownEntityException;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.shop.ShopService;
import by.epam.bookshop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

public class FindUsersCommand implements Command {
    private static final int ELEMENTS_PER_PAGE = 30;

    private static final String UNKNOWN_ENTITY_EXCEPTION = "Unknown entity exception";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserFinder finder = new UserFinder();

        int page;
        try {
            page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        if (isNotEmpty(request.getParameter(RequestParameters.USER_FIRSTNAME))) {
            finder = finder.findByFirstName(request.getParameter(RequestParameters.USER_FIRSTNAME));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.USER_LASTNAME))) {
            finder = finder.findByLastName(request.getParameter(RequestParameters.USER_LASTNAME));
        }

        if (isNotEmpty(request.getParameter(RequestParameters.USER_STATUS))) {
            try {
                finder = finder.findByStatus(UserStatus.resolveById(
                        Integer.parseInt(request.getParameter(RequestParameters.USER_LASTNAME))));
            } catch (UnknownEntityException e) {
                throw new CommandException(e);
            }
        }

        try {
        int pageQuantity = ControllerUtil.pageQuantity(UserService.getInstance().countBy(finder),
                ELEMENTS_PER_PAGE);

            User[] users = UserService.getInstance().findBy(finder).toArray(User[]::new);

            request.setAttribute(RequestParameters.USERS, users);
            request.setAttribute(RequestParameters.PAGE_QUANTITY, pageQuantity);
            request.setAttribute(RequestParameters.CURRENT_PAGE, page);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(JSPPages.SEARCH_USERS_PAGE);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }


}
