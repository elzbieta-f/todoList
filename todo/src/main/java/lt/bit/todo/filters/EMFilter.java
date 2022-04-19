package lt.bit.todo.filters;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "EMFilter", urlPatterns = {"/*"})
public class EMFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletContext sc = request.getServletContext();
        EntityManagerFactory emf = (EntityManagerFactory) sc.getAttribute("emf");
        EntityManager em;
        try {
            em = emf.createEntityManager();
        } catch (Exception ex) {
            throw new ServletException("Failed to connnect to DB", ex);
        }
        request.setAttribute("em", em);
        try {
            chain.doFilter(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
    }
}
