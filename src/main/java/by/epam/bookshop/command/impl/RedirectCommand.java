package by.epam.bookshop.command.impl;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

public class RedirectCommand implements Command {
    private final URL url;

    public RedirectCommand(URL url) {
        this.url = url;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(url);
        router.setRedirect();
        return router;
    }
}
