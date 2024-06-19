package filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String loginURI = httpRequest.getContextPath() + "/login.jsp";
        String forbiddenURI = httpRequest.getContextPath() + "/forbidden.jsp";
        String requestURI = httpRequest.getRequestURI();

        boolean isLoginRequest = requestURI.endsWith("/login.jsp") || requestURI.endsWith("/Login");
        boolean isForbiddenRequest = requestURI.equals(forbiddenURI);
        boolean isLoggedIn = (session != null && session.getAttribute("account") != null);
        String role = (String) (isLoggedIn ? session.getAttribute("role") : null);

        // Define admin-only pages more explicitly
        boolean isAdminPage = requestURI.contains("/inserimento.jsp") || requestURI.contains("/InserimentoProgetto")
                || requestURI.contains("/inserimentoprogetto.jsp") || requestURI.contains("/AssociaDipendenteProgetto");

        // Define guest accessible pages
        boolean isGuestPage = requestURI.contains("/Inserimento") && !isAdminPage;

        logger.info("Request URI: " + requestURI);
        logger.info("Is Logged In: " + isLoggedIn);
        logger.info("Role: " + role);

        if (isLoggedIn) {
            if (isAdminPage && !"admin".equals(role)) {
                logger.info("Access denied for non-admin user to admin page.");
                httpResponse.sendRedirect(forbiddenURI);
            } else if (isGuestPage && !"guest".equals(role) && !"admin".equals(role)) {
                logger.info("Access denied for non-guest user to guest page.");
                httpResponse.sendRedirect(forbiddenURI);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (isLoginRequest || isForbiddenRequest) {
                chain.doFilter(request, response);
            } else {
                logger.info("User not logged in, redirecting to login page.");
                httpResponse.sendRedirect(loginURI);
            }
        }
    }

    public void destroy() {}
}
