/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package lt.bit.todo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
@WebServlet(name = "SaveTodoServlet", urlPatterns = {"/saveTodo"})
public class SaveTodoServlet extends HttpServlet {

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
            String idStr = request.getParameter("id");
            Date atlikta = null;
            if (idStr == null) {
                u = new Uzduotis();
                u.setPavadinimas(request.getParameter("pavadinimas"));
                u.setVartotojas(v);
                String aprasymas = request.getParameter("aprasymas");
                if (aprasymas != null || aprasymas.trim().equals("")) {
                    u.setAprasymas(aprasymas);
                }
                String ikiKadaStr = request.getParameter("ikiKada");
                if (ikiKadaStr != null || !ikiKadaStr.trim().equals("")) {
                    try {
                        u.setIkiKada(sdf.parse(ikiKadaStr));
                    } catch (Exception ex) {
                        //ignored
                    }
                }
                String statusas = request.getParameter("statusas");
                if (statusas != null) {
                    Integer statInt = Integer.valueOf(statusas);
                    u.setStatusas(statInt);
                    if (statInt == 100) {
                        atlikta = new Date();
                    }
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
                u.setAtlikta(atlikta);
                if (u.getAtlikta() != null) {
                    u.setStatusas(100);
                    List<MazaUzduotis> mazos = u.getMazosUzduotys();
                    if (!mazos.isEmpty()) {
                        for (MazaUzduotis m : mazos) {
                            MazaUzduotis mDb = em.find(MazaUzduotis.class, m.getId());
                            if (mDb.getAtlikta() == null) {
                                mDb.setAtlikta(atlikta);
                            }
                        }
                    }
                }

                em.persist(u);
            } else {
                try {
                    Integer id = Integer.valueOf(idStr);
                    u = em.find(Uzduotis.class, id);
                } catch (Exception ex) {
                    // ignored
                }
                if (u != null) {
                    Vartotojas uzdV = u.getVartotojas();
                    if (!uzdV.equals(v)) {
                        request.setAttribute("klaida", "Prisijunkite");
                        RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
                        rd.forward(request, response);
                        return;
                    } else {
                        String pavadinimas = request.getParameter("pavadinimas");
                        if (pavadinimas != null) {
                            u.setPavadinimas(pavadinimas);
                        }

                        String aprasymas = request.getParameter("aprasymas");
                        if (aprasymas != null) {
                            u.setAprasymas(aprasymas);
                        }
                        String ikiKadaStr = request.getParameter("ikiKada");
                        if (ikiKadaStr != null) {
                            try {
                                u.setIkiKada(sdf.parse(ikiKadaStr));
                            } catch (Exception ex) {
                                //ignored
                            }
                        }
                        String statusas = request.getParameter("statusas");
                        if (statusas != null) {
                            Integer statInt = Integer.valueOf(statusas);
                            u.setStatusas(statInt);
                            if (u.getStatusas() == 100) {
                                atlikta = new Date();
                            }
                        }
                        String atliktaStr = request.getParameter("atlikta");
                        if (atliktaStr != null && !atliktaStr.trim().equals("")) {
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
                                u.setStatusas(0);
                            }
                        }
                        Date dabar = new Date();
                        if (atlikta != null && atlikta.getTime() > dabar.getTime()) {
                            atlikta = dabar;
                        }
                        u.setAtlikta(atlikta);
                        if (u.getAtlikta() != null) {
                            u.setStatusas(100);
                            List<MazaUzduotis> mazos = u.getMazosUzduotys();
                            if (!mazos.isEmpty()) {
                                for (MazaUzduotis m : mazos) {
                                    MazaUzduotis mDb = em.find(MazaUzduotis.class, m.getId());
                                    if (mDb.getAtlikta() == null) {
                                        mDb.setAtlikta(atlikta);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            em.flush();
            response.sendRedirect("todo.jsp");
//            response.sendRedirect("todoEdit.jsp?todoId=" + u.getId());
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
