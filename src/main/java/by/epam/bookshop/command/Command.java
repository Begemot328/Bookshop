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
     * @return {@link Router} object
     * @throws CommandException {@link CommandException} exception
     */
    Router execute (HttpServletRequest request) throws CommandException;
}
