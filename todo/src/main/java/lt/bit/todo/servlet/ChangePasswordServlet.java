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
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/changePassword"})
public class ChangePasswordServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
        EntityManager em = (EntityManager) request.getAttribute("em");
        EntityTransaction tx = em.getTransaction();
        String senas = request.getParameter("senas");
        if (senas != null && Vartotojas.checkPassword(senas, v.getSlaptazodis())) {
            String naujas = request.getParameter("naujas");
            String naujas2 = request.getParameter("naujas2");
            if (naujas != null && !"".equals(naujas)) {
                if (naujas2.equals(naujas)) {
                    try {
                        v.setSlaptazodis(Vartotojas.encodeNewPassword(naujas));
                        tx.begin();
                        em.persist(v);
                        tx.commit();
                        response.sendRedirect("./todo.jsp");
                    } catch (Exception ex) {
                        tx.rollback();
                        request.setAttribute("klaida", "Įvyko klaida keičiant slaptažodį");
                        RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
                        rd.forward(request, response);
                        return;
                    }
                } else {
                    request.setAttribute("klaida", "Naujai įvesti slaptažodžiai nesutampa");
                    RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
                    rd.forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("klaida", "Neteisingas naujas slaptaždis");
                RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
                rd.forward(request, response);
                return;
            }
        } else {
            request.setAttribute("klaida", "Neteisingas senas slaptažodis");
            RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
            rd.forward(request, response);
            return;
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
