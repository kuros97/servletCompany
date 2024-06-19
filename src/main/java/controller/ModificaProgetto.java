package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProgettoRepository;
import model.Progetto;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ModificaProgetto")
public class ModificaProgetto extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));  // Converte la stringa ID in un intero
        ProgettoRepository repo = new ProgettoRepository();

        try {
            ResultSet rs = repo.getProgettoById(id);
            if (rs.next()) {
                Progetto p = new Progetto();
                p.setId(rs.getInt("id"));
                p.setNomeProgetto(rs.getString("nomeProgetto"));
                p.setDescrizione(rs.getString("descrizione"));
                
                request.setAttribute("progetto", p);
                RequestDispatcher rd = request.getRequestDispatcher("modificaprogetto.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProgettoRepository repo = new ProgettoRepository();

        Progetto p = new Progetto();
        
        int id = Integer.parseInt(request.getParameter("id"));
        p.setId(id); // Imposta l'ID del progetto
        p.setNomeProgetto(request.getParameter("nomeProgetto"));
        p.setDescrizione(request.getParameter("descrizione"));

        if (repo.aggiornaProgetto(p) > 0) {
            // Va in porto
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            request.setAttribute("msg", "Modifica del progetto avvenuta correttamente!");
            rd.forward(request, response);
        } else {
            // Non va in porto
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            request.setAttribute("msg", "Modifica del progetto non avvenuta!");
            rd.forward(request, response);
        }
    }

}
