package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AccountRepository;

import java.io.IOException;

@WebServlet("/Elimina")
public class Elimina extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cf = request.getParameter("cf");
        request.setAttribute("cf", cf);
        RequestDispatcher rd = request.getRequestDispatcher("eliminazione.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cf = request.getParameter("cf");
        AccountRepository repo = new AccountRepository();

        int result = repo.eliminaDipAcc(cf);
        if (result > 0) {
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            request.setAttribute("msg", "Eliminazione avvenuta correttamente!");
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            request.setAttribute("msg", "Eliminazione non avvenuta!");
            rd.forward(request, response);
        }
    }
}
