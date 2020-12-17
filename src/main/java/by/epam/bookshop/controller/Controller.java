package by.epam.bookshop.controller;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.CommandEnum;
import by.epam.bookshop.command.JSPPages;
import by.epam.bookshop.exceptions.CommandException;

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
        String newPage = request.getParameter("command");
        System.out.println(newPage);
        if (newPage.isEmpty() || newPage == null) {
            newPage = JSPPages.ERROR_PAGE;
        }
        Command command = CommandEnum.valueOf(request.getParameter("command").toString().toUpperCase()).getCommand();
        try {
            request.getRequestDispatcher(command.execute(request).getPage()).forward(request, response);
        } catch (CommandException e) {
            request.getRequestDispatcher(JSPPages.ERROR_PAGE).forward(request, response);
        }
    }
}
