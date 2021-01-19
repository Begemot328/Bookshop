package by.epam.bookshop.listener;

import by.epam.bookshop.command.RequestParameters;
import by.epam.bookshop.controller.Controller;
import by.epam.bookshop.exceptions.ConnectionPoolException;
import by.epam.bookshop.exceptions.ConnectionPoolRuntimeException;
import by.epam.bookshop.pool.ConnectionPool;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class StartEndListener implements ServletContextListener {

    public void contextInitialized (ServletContextEvent ev) {
        ServletContext context = ev.getServletContext();

        try {
            ConnectionPool.getInstance();
            Controller.getLoggerInstance().debug("Pool init");
        } catch (ConnectionPoolException e) {
            throw new ConnectionPoolRuntimeException(e);
        }
    }

    public void contextDestroyed (ServletContextEvent ev) {
        ServletContext context = ev.getServletContext();
        try {
            ConnectionPool.getInstance().close();
            Controller.getLoggerInstance().debug("Pool destroy");
        } catch (ConnectionPoolException | IOException e) {
            throw new ConnectionPoolRuntimeException(e);
        }
    }
}
