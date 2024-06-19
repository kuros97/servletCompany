package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.WorkingRepository;
import model.Working;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/DipendentiProgetti")
public class DipendentiProgetti extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WorkingRepository repo = new WorkingRepository();

        try {
            ResultSet rs = repo.getAllWorkings();
            List<Working> workings = new ArrayList<>();

            while (rs.next()) {
                Working w = new Working();
                w.setIdDipendente(rs.getInt("idDipendente"));
                w.setNomeDipendente(rs.getString("nomeDipendente"));
                w.setCognomeDipendente(rs.getString("cognomeDipendente"));
                w.setIdProgetto(rs.getInt("idProgetto"));
                w.setNomeProgetto(rs.getString("nomeProgetto"));
                workings.add(w);
            }

            request.setAttribute("workings", workings);
            RequestDispatcher rd = request.getRequestDispatcher("dipendentiProgetti.jsp");
            rd.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
