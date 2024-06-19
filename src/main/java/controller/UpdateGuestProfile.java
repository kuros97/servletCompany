package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountRepository;
import model.Account;
import model.AccountDTO;


  
    @WebServlet("/UpdateGuestProfile")
    public class UpdateGuestProfile extends HttpServlet {
        private static final long serialVersionUID = 1L;
        
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("account") != null) {
                Account account = (Account) session.getAttribute("account");
                AccountRepository repo = new AccountRepository();
                AccountDTO accountDTO = repo.getDipendenteById(account.getIdDipendente());
                session.setAttribute("accountDTO", accountDTO); // Salva accountDTO nella sessione
                request.setAttribute("accountDTO", accountDTO);
                RequestDispatcher rd = request.getRequestDispatcher("editYourself.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect("login.jsp");
            }
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            AccountRepository accountRepo = new AccountRepository();
            AccountDTO accountDTO = new AccountDTO();

            try {
                int accountId = Integer.parseInt(request.getParameter("accountId"));
                int idDipendente = Integer.parseInt(request.getParameter("idDipendente"));
                double stipendio = Double.parseDouble(request.getParameter("stipendio"));
                Date dataNascita = Date.valueOf(request.getParameter("dataNascita"));
                Date dataAssunzione = Date.valueOf(request.getParameter("dataAssunzione"));

                accountDTO.setId(accountId);
                accountDTO.setIdDipendente(idDipendente);
                accountDTO.setNome(request.getParameter("nome"));
                accountDTO.setCognome(request.getParameter("cognome"));
                accountDTO.setCf(request.getParameter("cf"));
                accountDTO.setStipendio(stipendio);
                accountDTO.setDataDiNascita(dataNascita);
                accountDTO.setDataDiAssunzione(dataAssunzione);
                accountDTO.setSesso("f".equals(request.getParameter("sesso")));
                accountDTO.setLuogoNascita(request.getParameter("luogoNascita"));
                accountDTO.setUsername(request.getParameter("username"));
                accountDTO.setEmail(request.getParameter("email"));
                accountDTO.setPassword(request.getParameter("password"));

                if (accountRepo.updateGuestAccount(accountDTO)) {
                    response.sendRedirect("home.jsp");
                } else {
                    response.sendRedirect("error.jsp");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        }
        
    }
 
