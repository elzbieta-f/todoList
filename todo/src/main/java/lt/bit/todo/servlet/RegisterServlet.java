/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package lt.bit.todo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lt.bit.todo.data.Vartotojas;

/**
 *
 * @author elzbi
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vardas = request.getParameter("vardas");
        if (vardas == null || vardas.trim().equals("")) {
            request.setAttribute("klaida", "Vartotojo vardas privalomas");
            RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
            rd.forward(request, response);
            return;
        }
        String slaptazodis = request.getParameter("slaptazodis");
        if (slaptazodis == null || slaptazodis.trim().equals("")) {
            request.setAttribute("klaida", "Slaptažodis privalomas");
            RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
            rd.forward(request, response);
            return;
        }
        String slaptazodis2 = request.getParameter("slaptazodis2");
        if (!slaptazodis.equals(slaptazodis2)) {
            request.setAttribute("klaida", "Slaptažodžiai turi sutapti");
            RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
            rd.forward(request, response);
            return;
        }

        Vartotojas v = new Vartotojas();
        v.setVardas(vardas);
//        v.setSalt(Vartotojas.randomString(10, 20));
//        v.setSlaptazodis(Vartotojas.encodePassword(slaptazodis, v.getSalt()));
        v.setSlaptazodis(Vartotojas.encodeNewPassword(slaptazodis));
        EntityManager em = (EntityManager) request.getAttribute("em");

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(v);
            tx.commit();
            response.sendRedirect("./");
        } catch (Exception ex) {
            tx.rollback();
            request.setAttribute("klaida", "Toks vartotojas jau yra");
            RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
            rd.forward(request, response);
            return;

        }

        System.out.println(vardas);
        System.out.println(slaptazodis);
        System.out.println(slaptazodis2);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
