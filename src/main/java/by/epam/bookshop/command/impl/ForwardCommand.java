package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ForwardCommand implements Command {
    private JSPPages page;

    public ForwardCommand(JSPPages page) {
        this.page = page;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(page);
    }
}
