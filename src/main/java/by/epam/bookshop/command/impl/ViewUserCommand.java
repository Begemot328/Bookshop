package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.controller.dto.UserDTO;
import by.epam.bookshop.dao.impl.position_action.PositionActionFinder;
import by.epam.bookshop.entity.position_action.PositionAction;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.position_action.PositionActionService;
import by.epam.bookshop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewUserCommand implements Command {
    private static final int ELEMENTS_PER_PAGE = 30;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            User user = UserService.getInstance()
                    .read(Integer.parseInt(request.getParameter(RequestParameters.USER_ID)));
            PositionAction[] actions = null;
            PositionActionFinder finder;
            if (user.getStatus().equals(UserStatus.BUYER) ) {
                finder = new PositionActionFinder().findByBuyer(user.getId());
                actions = PositionActionService.getInstance()
                        .findBy(finder)
                        .toArray(PositionAction[]::new);
            } else {
                finder = new PositionActionFinder().findBySeller(user.getId());
                actions = PositionActionService.getInstance()
                        .findBy(finder)
                        .toArray(PositionAction[]::new);
            }

            int pageQuantity = CommandUtil.pageQuantity(PositionActionService.getInstance().countBy(finder),
                    ELEMENTS_PER_PAGE);

            request.setAttribute(RequestParameters.ACTIONS, actions);
            request.setAttribute(RequestParameters.USER, new UserDTO(user));

            if (request.getSession().getAttribute(SessionParameters.CURRENT_USER) != null) {
                User currentUser = (User) request.getSession().getAttribute(SessionParameters.CURRENT_USER);

                if (currentUser.getStatus().equals(UserStatus.SELLER)
                        || currentUser.getStatus().equals(UserStatus.ADMIN)
                        || (currentUser.getStatus().equals(UserStatus.BUYER)
                        && currentUser.getId() == user.getId())) {
                    return new Router(JSPPages.VIEW_USER_PAGE);
                }
            } else {
                Router router = new Router(new URL(request.getRequestURL().toString()));
                return router;
            }
        } catch (ServiceException | MalformedURLException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.VIEW_USER_PAGE);
    }
}
