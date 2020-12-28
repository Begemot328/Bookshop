package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

public class BookBookCommand implements Command {

    private static final String SERVICE_EXCEPTION = "Service Exception: ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        User user = ((User) request.getSession()
                .getAttribute(SessionParameters.CURRENT_USER));
        try {
            if (user.getStatus()
                    == UserStatus.BUYER) {
                Position newPosition = PositionService.getInstance().splitPosition(
                        (Position) request.getSession().getAttribute(SessionParameters.POSITION),
                        Integer.valueOf(request.getParameter(RequestParameters.QUANTITY)),
                        user, null, null,
                        PositionStatus.RESERVED);
                request.getSession().setAttribute(SessionParameters.POSITION, newPosition);
                request.getSession().setAttribute(SessionParameters.SELLER, null);
                request.getSession().setAttribute(SessionParameters.BUYER, user);
                return new Router(JSPPages.VIEW_POSITION_PAGE);
            }
            if (user.getStatus()
                    == UserStatus.ADMIN || user.getStatus()
                    == UserStatus.SELLER) {
                User buyer = UserService.getInstance().read(
                        Integer.valueOf(request.getParameter(RequestParameters.USER_ID))
                );
                Position newPosition = PositionService.getInstance().splitPosition(
                        (Position) request.getSession().getAttribute(SessionParameters.POSITION),
                        Integer.valueOf(request.getParameter(RequestParameters.QUANTITY)),
                        buyer, user, null,
                        PositionStatus.RESERVED);
                request.getSession().setAttribute(SessionParameters.POSITION, newPosition);
                request.getSession().setAttribute(SessionParameters.BUYER, buyer);
                request.getSession().setAttribute(SessionParameters.SELLER, user);
                return new Router(JSPPages.VIEW_POSITION_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(SERVICE_EXCEPTION, e);
        }

        return new Router(JSPPages.LOGIN_PAGE);
    }
}
