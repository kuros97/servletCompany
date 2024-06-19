package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.ProgettoRepository;

import java.io.IOException;
import java.sql.ResultSet;

@WebServlet("/EliminaProgetto")
public class EliminaProgetto extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        int id = Integer.parseInt(request.getParameter("id"));  // Converte la stringa ID in un intero
        ProgettoRepository repo = new ProgettoRepository();
        

        boolean result = repo.eliminaProgetto(id);
  
        if (result) {
            // Eliminazione avvenuta con successo
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            request.setAttribute("msg", "Eliminazione del progetto avvenuta correttamente!");
            rd.forward(request, response);
        } else {
            // Eliminazione non avvenuta
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            request.setAttribute("msg", "Eliminazione del progetto non avvenuta!");
            rd.forward(request, response);
        }
    }
}
