package by.epam.bookshop.controller;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.CommandEnum;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.command.Router;
import by.epam.bookshop.command.impl.RequestParameters;
import by.epam.bookshop.command.impl.SessionParameters;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServletRuntimeException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ControllerURL")
public class Controller  extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Command command = CommandEnum.getCommand(request.getParameter(RequestParameters.COMMAND));
        Router router;
        try {
            router = command.execute(request);
        } catch (CommandException e) {
            router = new Router(JSPPages.ERROR_PAGE);
            router.setRedirect();

        } catch (ServletRuntimeException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            router = new  Router(request.getRequestURL().toString());
        }

        if (router.getType() == Router.Type.FORWARD) {
            request.getSession().setAttribute(SessionParameters.LAST_PAGE, router.getPage());
            request.getRequestDispatcher(router.getPage()).forward(request, response);
        } else {
            response.sendRedirect(router.getPage());
        }
    }
}
