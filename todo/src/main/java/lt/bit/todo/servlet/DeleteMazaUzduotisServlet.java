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
import lt.bit.todo.data.MazaUzduotis;
import lt.bit.todo.data.Uzduotis;
import lt.bit.todo.data.Vartotojas;

/**
 *
 * @author elzbi
 */
@WebServlet(name = "DeleteMazaUzduotisServlet", urlPatterns = {"/deleteMazaUzduotis"})
public class DeleteMazaUzduotisServlet extends HttpServlet {

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
        EntityManager em = (EntityManager) request.getAttribute("em");
        Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Uzduotis u = null;
            MazaUzduotis m = null;
            String idStr = request.getParameter("mazaId");
            if (idStr == null) {
                response.sendRedirect("./todo.jsp");
            } else {
                try {
                    Integer mazaId = Integer.valueOf(idStr);
                    m = em.find(MazaUzduotis.class, mazaId);
                } catch (Exception ex) {
                    // ignored
                }
                if (m != null) {
                    u = m.getUzduotis();
                    Vartotojas uzdV = u.getVartotojas();
                    if (!uzdV.equals(v)) {
                        request.setAttribute("klaida", "Prisijunkite");
                        RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
                        rd.forward(request, response);
                        return;
                    } else {
                        em.remove(m);
                    }
                    response.sendRedirect("./todoEdit.jsp?todoId=" + u.getId());
                }

            }
            if (u == null) {
                response.sendRedirect("./todo.jsp");
            }
        } finally {
            tx.commit();
        }
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
