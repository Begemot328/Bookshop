package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

public class ProcessPositionCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            Position position = PositionService.getInstance().read(
                    Integer.parseInt(request.getParameter(RequestParameters.POSITION_ID)));
            request.setAttribute(RequestParameters.POSITION, position);

            if (((User)request.getSession().getAttribute(SessionParameters.CURRENT_USER)).getStatus()
                    == UserStatus.SELLER
                    || ((User)request.getSession().getAttribute(SessionParameters.CURRENT_USER)).getStatus()
                    == UserStatus.ADMIN) {
                User[] buyers = UserService.getInstance()
                        .findBy(new UserFinder().findByStatus(UserStatus.BUYER)).toArray(User[]::new);
                request.setAttribute(RequestParameters.BUYERS, buyers);
                return new Router(JSPPages.PROCESS_POSITION_PAGE);
            }
            return new Router(JSPPages.BOOK_POSITION_PAGE);

        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
