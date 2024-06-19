package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/guest/*")
public class GuestFilter implements Filter {
    private static final Logger logger = Logger.getLogger(GuestFilter.class.getName());

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String role = (String) session.getAttribute("role");

        if ("guest".equals(role)) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/forbidden.jsp");
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void destroy() {}
}
