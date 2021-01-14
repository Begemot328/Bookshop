package by.epam.bookshop.controller;

import by.epam.bookshop.command.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import by.epam.bookshop.exceptions.CommandException;
import by.epam.bookshop.exceptions.ServletRuntimeException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Controller class
 *
 * @author Yury Zmushko
 * @version 1.0
 */
@WebServlet("/ControllerURL")
@MultipartConfig
public class Controller  extends HttpServlet {
    private static final String GET = "GET";
    private static final String POST = "POST";
    static Logger logger = LoggerFactory.getLogger(Controller.class);

    public static Logger getLoggerInstance() {
        return logger;
    }

    /**
     * GET method to process GET request
     *
     * @param request {@link HttpServletRequest} to handle
     * @param response {@link HttpServletResponse} to handle
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @throws IOException
     * @throws ServletException
     *
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug(GET);
        processRequest(request, response);

    }

    /**
     * POST method to process GET request
     *
     * @param request {@link HttpServletRequest} to handle
     * @param response {@link HttpServletResponse} to handle
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @throws IOException
     * @throws ServletException
     *
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug(POST);

        processRequest(request, response);
    }

    /**
     * Method to process the request
     *
     * @param request {@link HttpServletRequest} to handle
     * @param response {@link HttpServletResponse} to handle
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @throws IOException
     * @throws ServletException
     *
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CommandUtil.transferParameter(request);

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
            request.setAttribute(RequestParameters.ERROR, e);
            router = new Router(JSPPages.ERROR_PAGE);
        } catch (Exception e) {
            request.setAttribute(RequestParameters.ERROR, e);
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
