package by.epam.bookshop.command;

import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Command interface.
 *
 * @author Yury Zmushko
 * @version 1.0
 */
public interface Command {

    /**
     * {@link Router} creation after command execution.
     *
     * @param request   {@link HttpServletRequest} request to get params
     * @throws {@link CommandException} exception
     * @return {@link Router} object
     */
    Router execute (HttpServletRequest request) throws CommandException;
}
