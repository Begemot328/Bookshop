package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.dao.impl.position_action.PositionActionFinder;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position_action.PositionAction;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.service.position_action.PositionActionService;
import by.epam.bookshop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class ProcessPositionCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            Position position = PositionService.getInstance().read(
                    Integer.parseInt(request.getParameter(RequestParameters.POSITION_ID)));
            request.setAttribute(RequestParameters.POSITION, position);

            if (position.getStatus().getId() > 1) {
                Collection<PositionAction> actions =
                        PositionActionService.getInstance().findBy(
                                new PositionActionFinder().findByPosition(position.getId())
                                        .findByFinalStatus(position.getStatus().getId()));
                Optional<PositionAction> action =
                        actions.stream().max(
                                Comparator.comparing(PositionAction::getDate));
                if (action.isPresent()) {
                    request.setAttribute(RequestParameters.BUYER, action.get().getBuyer());
                    request.setAttribute(RequestParameters.SELLER, action.get().getSeller());
                }
            }
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
