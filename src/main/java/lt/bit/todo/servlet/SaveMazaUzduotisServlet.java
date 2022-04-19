/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package lt.bit.todo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
@WebServlet(name = "SaveMazaUzduotisServlet", urlPatterns = {"/saveMazaUzduotis"})
public class SaveMazaUzduotisServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        EntityManager em = (EntityManager) request.getAttribute("em");
        Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Uzduotis u = null;
            MazaUzduotis m = null;
            String todoIdStr = request.getParameter("todoId");
            try {
                Integer todoId = Integer.valueOf(todoIdStr);
                u = em.find(Uzduotis.class, todoId);
            } catch (NumberFormatException ex) {
                response.sendRedirect("todo.jsp");
                return;
            }
            if (u == null) {
                response.sendRedirect("todo.jsp");
                return;
            } else {
                String idStr = request.getParameter("id");
                Date atlikta = null;
                if (idStr == null) {
                    m = new MazaUzduotis();
                    m.setPavadinimas(request.getParameter("pavadinimas"));
                    m.setUzduotis(u);
                    String aprasymas = request.getParameter("aprasymas");
                    if (aprasymas != null || aprasymas.trim().equals("")) {
                        m.setAprasymas(aprasymas);
                    }

                    String atliktaStr = request.getParameter("atlikta");
                    if (atliktaStr != null || !atliktaStr.trim().equals("")) {
                        try {
                            atlikta = sdf.parse(atliktaStr);
                        } catch (Exception ex) {
                            //ignored
                        }
                    }
                    Date dabar = new Date();
                    if (atlikta != null && atlikta.getTime() > dabar.getTime()) {
                        atlikta = dabar;
                    }
                    m.setAtlikta(atlikta);
                    em.persist(m);
                } else {
                    try {
                        Integer id = Integer.valueOf(idStr);
                        m = em.find(MazaUzduotis.class, id);
                    } catch (Exception ex) {
                        // ignored
                    }
                    if (m != null) {
                        String pavadinimas = request.getParameter("pavadinimas");
                        if (pavadinimas != null && !pavadinimas.trim().equals("")) {
                            m.setPavadinimas(pavadinimas);
                        }
                        m.setUzduotis(u);
                        String aprasymas = request.getParameter("aprasymas");
                        if (aprasymas != null) {
                            m.setAprasymas(aprasymas);
                        }

                        String atliktaStr = request.getParameter("atlikta");
                        if (atliktaStr != null) {
                            try {
                                atlikta = sdf.parse(atliktaStr);
                            } catch (Exception ex) {
                                //ignored
                            }
                        }
                        String done = request.getParameter("done");
                        if (done != null && !done.equals("")) {
                            if (done.equals("setTrue")) {
                                atlikta = new Date();
                            } else if (done.equals("setFalse")) {
                                atlikta = null;
                            }
                        }
                        Date dabar = new Date();
                        if (atlikta != null && atlikta.getTime() > dabar.getTime()) {
                            atlikta = dabar;
                        }
                        m.setAtlikta(atlikta);
                    }
                }
                em.flush();
                response.sendRedirect("todoEdit.jsp?todoId=" + ((u != null) ? u.getId() : ""));
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
