package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;

public class RedirectCommand implements Command {
    private String page;

    public RedirectCommand(String page) {
        this.page = page;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setRedirect(page);
        return router;
    }
}
