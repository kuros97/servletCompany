package controller;

import dao.DipendenteRepository;
import dao.ProgettoRepository;
import dao.WorkingRepository;
import model.Dipendente;
import model.Progetto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AssociaDipendenteProgetto")
public class AssociaDipendenteProgetto extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DipendenteRepository dipendenteRepository = new DipendenteRepository();
    private ProgettoRepository progettoRepository = new ProgettoRepository();
    private WorkingRepository workingRepository = new WorkingRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Dipendente> dipendenti = dipendenteRepository.getAllDipendenti();
        List<Progetto> progetti = progettoRepository.getAllProgetti();
        request.setAttribute("dipendenti", dipendenti);
        request.setAttribute("progetti", progetti);
        request.getRequestDispatcher("associaDipendenteProgetto.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idDipendente = Integer.parseInt(request.getParameter("idDipendente"));
        int idProgetto = Integer.parseInt(request.getParameter("idProgetto"));

        boolean coppiaEsiste = workingRepository.coppiaExists(idDipendente, idProgetto);
        if (coppiaEsiste) {
            request.setAttribute("errorMessage", "Dipendente già associato a questo progetto.");
        } else {
            boolean successo = workingRepository.associaDipendenteProgetto(idDipendente, idProgetto);
            if (successo) {
                request.setAttribute("successMessage", "Dipendente associato al progetto con successo.");
            } else {
                request.setAttribute("errorMessage", "Errore durante l'associazione del dipendente al progetto.");
            }
        }

        doGet(request, response); // Ricarica i dipendenti e i progetti aggiornati
    }
}
