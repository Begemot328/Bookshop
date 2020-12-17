package by.epam.bookshop.command;

import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    Router execute (HttpServletRequest request) throws CommandException;
}
