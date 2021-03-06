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
    private static final int ELEMENTS_PER_PAGE = 10;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            int page;
            try {
                page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));
                if (page <= 0) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }

            User user = UserService.getInstance()
                    .read(Integer.parseInt(request.getParameter(RequestParameters.USER_ID)));
            PositionAction[] actions;
            PositionActionFinder finder;
            if (user.getStatus().equals(UserStatus.BUYER) ) {
                finder = new PositionActionFinder().findByBuyer(user.getId());
            } else {
                finder = new PositionActionFinder().findBySeller(user.getId());
            }
            actions = PositionActionService.getInstance()
                    .findBy(finder, (page - 1) * ELEMENTS_PER_PAGE, ELEMENTS_PER_PAGE)
                    .toArray(PositionAction[]::new);

            int pageQuantity = CommandUtil.pageQuantity(PositionActionService.getInstance().countBy(finder),
                    ELEMENTS_PER_PAGE);

            request.setAttribute(RequestParameters.ACTIONS, actions);
            request.setAttribute(RequestParameters.USER, new UserDTO(user));

            request.setAttribute(RequestParameters.PAGE_QUANTITY, pageQuantity);
            request.setAttribute(RequestParameters.CURRENT_PAGE, page);

            if (request.getSession().getAttribute(SessionParameters.CURRENT_USER) != null) {
                User currentUser = (User) request.getSession().getAttribute(SessionParameters.CURRENT_USER);

                if (currentUser.getStatus().equals(UserStatus.SELLER)
                        || currentUser.getStatus().equals(UserStatus.ADMIN)
                        || (currentUser.getStatus().equals(UserStatus.BUYER)
                        && currentUser.getId() == user.getId())) {
                    return new Router(JSPPages.VIEW_USER_PAGE);
                }
            } else {
                return new Router(new URL(request.getRequestURL().toString()));

            }
        } catch (ServiceException | MalformedURLException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.VIEW_USER_PAGE);
    }
}
