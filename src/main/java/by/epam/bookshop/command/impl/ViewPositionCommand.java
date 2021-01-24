package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.*;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.position.PositionService;

import javax.servlet.http.HttpServletRequest;

public class ViewPositionCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            if (request.getParameter(RequestParameters.POSITION_ID) != null) {
                Position position = PositionService.getInstance().read(
                        Integer.parseInt(request.getParameter(RequestParameters.POSITION_ID)));
                request.setAttribute(RequestParameters.POSITION, position);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(JSPPages.PROCESS_POSITION_PAGE);
    }
}
