package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.service.position.PositionService;

import javax.servlet.http.HttpServletRequest;

public class CancelBookCommand implements Command {


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        User user = ((User) request.getSession()
                .getAttribute(SessionParameters.CURRENT_USER));
        try {
            int id = Integer.parseInt(request.getParameter(RequestParameters.POSITION_ID));

            if (user.getStatus()
                    == UserStatus.BUYER) {
                Position newPosition = PositionService.getInstance().splitPosition(
                        PositionService.getInstance().read(id),
                        Integer.valueOf(request.getParameter(RequestParameters.QUANTITY)),
                        user, null, null,
                        PositionStatus.READY);
                request.setAttribute(RequestParameters.POSITION, newPosition);
                return new Router(JSPPages.VIEW_POSITION_PAGE);
            }
            if (user.getStatus()
                    == UserStatus.ADMIN || user.getStatus()
                    == UserStatus.SELLER) {
                System.out.println(request.getParameter(RequestParameters.USER_ID));

                Position newPosition = PositionService.getInstance().splitPosition(
                        PositionService.getInstance().read(id),
                        Integer.valueOf(request.getParameter(RequestParameters.QUANTITY)),
                        null, user, null,
                        PositionStatus.READY);
                request.setAttribute(RequestParameters.POSITION, newPosition);
                return new Router(JSPPages.VIEW_POSITION_PAGE);
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException(e);
        } catch (ValidationException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            return new Router((JSPPages) request.getSession().getAttribute(SessionParameters.LAST_PAGE));
        }
        return new Router(JSPPages.LOGIN_PAGE);
    }
}
