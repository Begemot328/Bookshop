package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.position_action.PositionActionFinder;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.position_action.PositionAction;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.service.position_action.PositionActionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class ViewPositionCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            if (request.getParameter(RequestParameters.POSITION_ID) != null) {
                int id = Integer.parseInt(request.getParameter(RequestParameters.POSITION_ID));
                Position position = PositionService.getInstance().read(id);
                request.setAttribute(RequestParameters.POSITION, position);
                if (position.getStatus() == PositionStatus.SOLD
                        || position.getStatus() == PositionStatus.RESERVED) {
                    Collection<PositionAction> actions =
                            PositionActionService.getInstance().findBy(
                                    new PositionActionFinder().findByPosition(id)
                                            .findByFinalStatus(position.getStatus().getId()));
                    Optional<PositionAction> action =
                            actions.stream().max(
                                    Comparator.comparing(PositionAction::getDate));
                    if (action.isPresent()) {
                        request.setAttribute(RequestParameters.BUYER, action.get().getBuyer());
                        request.setAttribute(RequestParameters.SELLER, action.get().getSeller());
                    }
                }

            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.VIEW_POSITION_PAGE);
    }
}
