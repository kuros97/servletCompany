package controller;

import dao.AccountRepository;
import model.Account;
import model.AccountDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoadGuestProfile")
public class LoadGuestProfile extends HttpServlet {
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

}
