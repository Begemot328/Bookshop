package by.epam.bookshop.controller;

import by.epam.bookshop.command.Command;
import by.epam.bookshop.command.CommandEnum;

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
        System.out.println(request.getAttribute("command"));
        String newPage = request.getParameter("command");
        System.out.println(newPage);
        if (newPage.isEmpty() || newPage == null) {
            newPage = "/error.jsp";
        }
        Command command = CommandEnum.valueOf(request.getParameter("command").toString().toUpperCase()).getCommand();
        request.getRequestDispatcher(command.execute(request).getPage()).forward(request, response);
    }
}
