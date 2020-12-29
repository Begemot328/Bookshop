package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.dao.impl.position.PositionFinder;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.book.BookService;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

public class ViewPositionCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            if (request.getParameter(RequestParameters.POSITION_ID) != null) {
                Position position = PositionService.getInstance().read(
                        Integer.parseInt(request.getParameter(RequestParameters.POSITION_ID)));
                request.getSession().setAttribute(SessionParameters.POSITION, position);
            }
        } catch (ServiceException e) {
            request.getSession().setAttribute(SessionParameters.ERROR_MESSAGE, e.getMessage() + e.getStackTrace());
            return new Router(JSPPages.ERROR_PAGE);
        }
        return new Router(JSPPages.PROCESS_POSITION_PAGE);
    }
}
