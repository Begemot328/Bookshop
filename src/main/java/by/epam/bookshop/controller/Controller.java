package by.epam.bookshop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.CommandEnum;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.command.SessionParameters;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServletRuntimeException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/ControllerURL")
public class Controller  extends HttpServlet {
    private static final String GET = "GET";
    private static final String POST = "POST";
    static Logger logger = LoggerFactory.getLogger(Controller.class);

    public static Logger getLoggerInstance() {
        return logger;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.warn(GET);
        processRequest(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.warn(POST);
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Enumeration<String> pars = request.getParameterNames();
        while (pars.hasMoreElements()) {
            String name = pars.nextElement();
            logger.debug(name.concat("=").concat(request.getParameter(name)));
        }

        Command command = CommandEnum.getCommand(request.getParameter(RequestParameters.COMMAND));
        Router router;
        try {
            router = command.execute(request);
        } catch (CommandException | ServletRuntimeException e) {
            request.getSession().setAttribute(SessionParameters.ERROR, e);
            router = new Router(JSPPages.ERROR_PAGE);
        }

        if (router.getType() == Router.Type.FORWARD) {
            request.getSession().setAttribute(SessionParameters.LAST_PAGE, router.getPage());
            request.getRequestDispatcher(router.getPage()).forward(request, response);
        } else {
            response.sendRedirect(router.getPage());
        }
    }
}
