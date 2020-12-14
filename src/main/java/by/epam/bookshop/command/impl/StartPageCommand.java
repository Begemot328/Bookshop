package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;

import javax.servlet.http.HttpServletRequest;

public class StartPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(JSPPages.START_PAGE);
    }
}
