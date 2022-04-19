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
@WebServlet(name = "DeleteVartotojasServlet", urlPatterns = {"/deleteVartotojas"})
public class DeleteVartotojasServlet extends HttpServlet {

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
        Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
        EntityManager em = (EntityManager) request.getAttribute("em");
        if (!v.getAdmin()) {
            request.setAttribute("klaida", "Nesate autorizuotas pamatyti šį puslapį");
            RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
            rd.forward(request, response);
            return;
        }
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Vartotojas user = null;
            String idStr = request.getParameter("id");
            if (idStr == null) {
                response.sendRedirect("./admin.jsp");
                return;
            } else {
                try {
                    Integer id = Integer.valueOf(idStr);
                    user = em.find(Vartotojas.class, id);
                } catch (Exception ex) {
                    // ignored
                }
                if (user != null&&!user.equals(v)) {
                    em.remove(user);
                }
            }
            response.sendRedirect("./admin.jsp");
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
