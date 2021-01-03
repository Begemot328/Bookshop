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

public class ReturnBookCommand implements Command {

    private static final String SERVICE_EXCEPTION = "Service Exception: ";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        User user = ((User) request.getSession()
                .getAttribute(SessionParameters.CURRENT_USER));
        try {
            if (user.getStatus()
                    == UserStatus.ADMIN || user.getStatus()
                    == UserStatus.SELLER) {
                User buyer = UserService.getInstance().read(
                        Integer.valueOf(request.getParameter(RequestParameters.USER_ID))
                );
                Position oldPosition = (Position) request.getSession().getAttribute(SessionParameters.POSITION);
                Position newPosition = PositionService.getInstance().splitPosition(
                        oldPosition,
                        oldPosition.getQuantity(),
                        null, user, null,
                        PositionStatus.READY);
                request.getSession().setAttribute(SessionParameters.POSITION, newPosition);
                return new Router(JSPPages.VIEW_POSITION_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.LOGIN_PAGE);
    }
}
