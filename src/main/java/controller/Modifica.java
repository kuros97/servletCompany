package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountRepository;
import model.AccountDTO;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/Modifica")
public class Modifica extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cf = request.getParameter("cf");
        AccountRepository accountRepo = new AccountRepository();

        try {
            ResultSet rs = accountRepo.getDipAccByCf(cf);
            if (rs.next()) {
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
                accountDTO.setPassword(rs.getString("password"));
                accountDTO.setIdPermesso(rs.getInt("idPermesso"));
                accountDTO.setIdDipendente(rs.getInt("id"));

                request.setAttribute("account", accountDTO);

                RequestDispatcher rd = request.getRequestDispatcher("modifica.jsp");
                rd.forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountRepository accountRepo = new AccountRepository();

        AccountDTO accountDTO = new AccountDTO();
        String accountIdString = request.getParameter("accountId");
        String idDipendenteString = request.getParameter("idDipendente");
        int accountId = 0;
        int idDipendente = 0;

        if (accountIdString != null && !accountIdString.isEmpty()) {
            accountId = Integer.parseInt(accountIdString);
        }
        if (idDipendenteString != null && !idDipendenteString.isEmpty()) {
            idDipendente = Integer.parseInt(idDipendenteString);
        }
        
        accountDTO.setId(accountId);
        accountDTO.setIdDipendente(idDipendente);
        accountDTO.setNome(request.getParameter("nome"));
        accountDTO.setCognome(request.getParameter("cognome"));
        accountDTO.setCf(request.getParameter("cf"));
        accountDTO.setStipendio(Double.parseDouble(request.getParameter("stipendio")));
        accountDTO.setDataDiNascita(Date.valueOf(request.getParameter("dataNascita")));
        accountDTO.setDataDiAssunzione(Date.valueOf(request.getParameter("dataAssunzione")));
        accountDTO.setSesso("f".equalsIgnoreCase(request.getParameter("sesso")));
        accountDTO.setLuogoNascita(request.getParameter("luogoNascita"));
        accountDTO.setUsername(request.getParameter("username"));
        accountDTO.setEmail(request.getParameter("email"));
        accountDTO.setPassword(request.getParameter("password"));
        accountDTO.setIdPermesso(Integer.parseInt(request.getParameter("idPermesso")));


        if (accountRepo.updateDipAcc(accountDTO)) {
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            request.setAttribute("msg", "Modifica avvenuta correttamente!");
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            request.setAttribute("msg", "Modifica non avvenuta!");
            rd.forward(request, response);
        }
    }
}