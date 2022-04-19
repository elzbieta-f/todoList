/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package lt.bit.todo.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.persistence.EntityManager;
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
import lt.bit.todo.data.Vartotojas;

/**
 *
 * @author elzbi
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/todoEdit.jsp", "/todo.jsp", "/saveTodo", "/saveMazaUzduotis", "/deleteTodo", "/deleteMazaUzduotis", "/admin.jsp", "/deleteVartotojas", "/setAdmin", "/change.jsp", "/changePassword"})
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session == null) {
            ((HttpServletResponse) response).sendRedirect("./login.html");
            return;
        }

        Integer id = (Integer) session.getAttribute("userId");
        if (id == null) {
            ((HttpServletResponse) response).sendRedirect("./login.html");
            return;
        }

        EntityManager em = (EntityManager) request.getAttribute("em");

        Vartotojas v = em.find(Vartotojas.class, id);
        if (v == null) {
            ((HttpServletResponse) response).sendRedirect("./login.html");
            return;
        }
        request.setAttribute("vartotojas", v);
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }

}
