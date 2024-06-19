package controller;

import dao.AccountRepository;
import model.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(Login.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountRepository repo = new AccountRepository();
        String usernameOrEmail = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");
        logger.info("Attempting login with usernameOrEmail: " + usernameOrEmail);

        Account account = repo.getAccountByUsernamePassword(usernameOrEmail, password);

        if (account != null) {
            logger.info("Account found: " + account.getUsername());
            HttpSession session = request.getSession();
            session.setAttribute("account", account);
            session.setAttribute("role", account.getIdPermesso() == 1 ? "admin" : "guest");

            response.sendRedirect("home.jsp");
        } else {
            logger.info("Account not found or invalid credentials");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            request.setAttribute("errorMessage", "Invalid credentials");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
