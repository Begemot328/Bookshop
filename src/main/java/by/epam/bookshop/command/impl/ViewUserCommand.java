package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.controller.dto.UserDTO;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.dao.impl.position.PositionFinder;
import by.epam.bookshop.dao.impl.position_action.PositionActionFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position_action.PositionAction;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.author.AuthorService;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.service.position_action.PositionActionService;
import by.epam.bookshop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class ViewUserCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            User user = UserService.getInstance()
                    .read(Integer.parseInt(request.getParameter(RequestParameters.USER_ID)));
            PositionAction[] actions = null;
            if (user.getStatus().equals(UserStatus.BUYER) ) {
                actions = PositionActionService.getInstance()
                        .findBy(new PositionActionFinder().findByBuyer(user.getId()))
                        .toArray(PositionAction[]::new);
            } else {
                actions = PositionActionService.getInstance()
                        .findBy(new PositionActionFinder().findBySeller(user.getId()))
                        .toArray(PositionAction[]::new);
            }

            request.getSession().setAttribute(SessionParameters.ACTIONS, actions);
            request.getSession().setAttribute(SessionParameters.USER, new UserDTO(user));

            if (request.getSession().getAttribute(SessionParameters.CURRENT_USER) != null) {
                User currentUser = (User) request.getSession().getAttribute(SessionParameters.CURRENT_USER);

                if (currentUser.getStatus().equals(UserStatus.SELLER)
                        || currentUser.getStatus().equals(UserStatus.ADMIN)
                        || (currentUser.getStatus().equals(UserStatus.BUYER)
                        && currentUser.getId() == user.getId())) {
                    return new Router(JSPPages.VIEW_USER_PAGE);
                }
            } else {
                Router router = new Router(request.getRequestURL().toString());
                router.setRedirect();
                return router;
            }

        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionParameters.ERROR_MESSAGE, e.getMessage()
                    + Arrays.toString(e.getStackTrace()));
            return new Router(JSPPages.ERROR_PAGE);
        }
        return new Router(JSPPages.VIEW_USER_PAGE);
    }
}
