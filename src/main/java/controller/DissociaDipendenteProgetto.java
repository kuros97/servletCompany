package controller;

import dao.WorkingRepository;
import dao.DipendenteRepository;
import dao.ProgettoRepository;
import model.Dipendente;
import model.Progetto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/DissociaDipendenteProgetto")
public class DissociaDipendenteProgetto extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DipendenteRepository dipRepo = new DipendenteRepository();
        ProgettoRepository projRepo = new ProgettoRepository();

        List<Dipendente> dipendenti = dipRepo.getAllDipendenti();
        List<Progetto> progetti = projRepo.getAllProgetti();

        request.setAttribute("dipendenti", dipendenti);
        request.setAttribute("progetti", progetti);

        RequestDispatcher dispatcher = request.getRequestDispatcher("associaDipendenteProgetto.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idDipendente = Integer.parseInt(request.getParameter("idDipendente"));
        int idProgetto = Integer.parseInt(request.getParameter("idProgetto"));

        WorkingRepository workingRepo = new WorkingRepository();

        boolean success = workingRepo.dissociaDipendenteDaProgetto(idDipendente, idProgetto);
        if (success) {
            request.setAttribute("successMessage", "Dipendente dissociato dal progetto con successo.");
        } else {
            request.setAttribute("errorMessage", "Errore nella dissociazione del dipendente dal progetto.");
        }

        doGet(request, response);
    }
}
