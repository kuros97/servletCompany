package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.AccountRepository;
import model.AccountDTO;

@WebServlet("/Inserimento")
public class Inserimento extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(Inserimento.class.getName());

    public Inserimento() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int page = 1;
        int pageSize = 10;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }

        if ("search".equals(action)) {
            handleSearch(request, response, page, pageSize);
        } else {
            handleDisplayAll(request, response, page, pageSize);
        }
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response, int page, int pageSize) throws ServletException, IOException {
        String cf = request.getParameter("cf");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String luogoNascita = request.getParameter("luogoNascita");
        String stipendioMaxStr = request.getParameter("stipendioMax");
        String permessoStr = request.getParameter("permesso");

        Double stipendioMax = null;
        if (stipendioMaxStr != null && !stipendioMaxStr.isEmpty()) {
            stipendioMax = Double.parseDouble(stipendioMaxStr);
        }

        Integer permesso = null;
        if (permessoStr != null && !permessoStr.isEmpty()) {
            permesso = Integer.parseInt(permessoStr);
        }

        int offset = (page - 1) * pageSize;
        AccountRepository repo = new AccountRepository();
        ResultSet rs = repo.searchDipendenti(cf, nome, cognome, luogoNascita, stipendioMax, permesso, offset, pageSize);
        List<AccountDTO> accounts = new ArrayList<>();

        try {
            while (rs.next()) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setId(rs.getInt("id"));
                accountDTO.setNome(rs.getString("nome"));
                accountDTO.setCognome(rs.getString("cognome"));
                accountDTO.setDataDiNascita(rs.getDate("data_di_nascita"));
                accountDTO.setCf(rs.getString("codice_fiscale"));
                accountDTO.setStipendio(rs.getDouble("stipendio"));
                accountDTO.setSesso(rs.getBoolean("sesso"));
                accountDTO.setDataDiAssunzione(rs.getDate("data_di_assunzione"));
                accountDTO.setLuogoNascita(rs.getString("luogo_nascita"));
                accountDTO.setUsername(rs.getString("username"));
                accountDTO.setEmail(rs.getString("email"));
                accountDTO.setIdPermesso(rs.getInt("idPermesso"));
                accounts.add(accountDTO);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante la ricerca dei dipendenti", e);
            e.printStackTrace();
        }

        request.setAttribute("accounts", accounts);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);

        int totalRecords = repo.getTotalDipendenti(cf, nome, cognome, luogoNascita, stipendioMax, permesso);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("ricercaDipendenti.jsp").forward(request, response);
    }

    private void handleDisplayAll(HttpServletRequest request, HttpServletResponse response, int page, int pageSize) throws ServletException, IOException {
        AccountRepository repo = new AccountRepository();
        List<AccountDTO> accounts = new ArrayList<>();

        int offset = (page - 1) * pageSize;
        try (ResultSet rs = repo.getDipAcc(offset, pageSize)) {
            while (rs.next()) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setId(rs.getInt("id"));
                accountDTO.setNome(rs.getString("nome"));
                accountDTO.setCognome(rs.getString("cognome"));
                accountDTO.setDataDiNascita(rs.getDate("data_di_nascita"));
                accountDTO.setCf(rs.getString("codice_fiscale"));
                accountDTO.setStipendio(rs.getDouble("stipendio"));
                // Recupera il valore del sesso come stringa
                String sesso = rs.getString("sesso");
                // Converte la stringa in booleano
                accountDTO.setSesso("f".equalsIgnoreCase(sesso));
                accountDTO.setDataDiAssunzione(rs.getDate("data_di_assunzione"));
                accountDTO.setLuogoNascita(rs.getString("luogo_nascita"));
                accountDTO.setUsername(rs.getString("username"));
                accountDTO.setEmail(rs.getString("email"));
                accountDTO.setIdPermesso(rs.getInt("idPermesso"));
                accounts.add(accountDTO);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante la visualizzazione di tutti i dipendenti", e);
            request.setAttribute("msg", "Errore del server durante la visualizzazione dei dipendenti");
            RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
            return;
        }

        request.setAttribute("accounts", accounts);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);

        int totalRecords = repo.getTotalDipendenti(null, null, null, null, null, null);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("gestione.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountRepository repo = new AccountRepository();
        AccountDTO accountDTO = new AccountDTO();

        String sesso = request.getParameter("sesso");
        accountDTO.setSesso("f".equalsIgnoreCase(sesso));

        accountDTO.setNome(request.getParameter("nome"));
        accountDTO.setCognome(request.getParameter("cognome"));
        accountDTO.setCf(request.getParameter("cf"));
        accountDTO.setStipendio(Double.parseDouble(request.getParameter("stipendio")));
        accountDTO.setDataDiNascita(Date.valueOf(request.getParameter("dataNascita")));
        accountDTO.setDataDiAssunzione(Date.valueOf(request.getParameter("dataAssunzione")));
        accountDTO.setLuogoNascita(request.getParameter("luogoNascita"));
        accountDTO.setUsername(request.getParameter("username"));
        accountDTO.setEmail(request.getParameter("email"));
        accountDTO.setPassword(request.getParameter("password"));
        accountDTO.setIdPermesso(Integer.parseInt(request.getParameter("permesso")));

        if (repo.isCfExists(accountDTO.getCf())) {
            request.setAttribute("msg", "Codice fiscale già esistente!");
        } else {
            if (repo.inserimentoDipAcc(accountDTO) > 0) {
                request.setAttribute("msg", "Inserimento avvenuto correttamente!");
            } else {
                request.setAttribute("msg", "Inserimento non avvenuto!");
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
        rd.forward(request, response);
    }
}
