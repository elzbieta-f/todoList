<%-- 
    Document   : todoEdit
    Created on : 22 Feb 2022, 18:31:29
    Author     : elzbi
--%>

<%@page import="lt.bit.todo.data.MazaUzduotis"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.Query"%>
<%@page import="lt.bit.todo.data.Vartotojas"%>
<%@page import="lt.bit.todo.data.Uzduotis"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
    EntityManager em = (EntityManager) request.getAttribute("em");

    String todoIdStr = request.getParameter("todoId");

    Integer todoId = 0;
    try {
        todoId = Integer.valueOf(todoIdStr);
//        qStr=" and u.id= :todoId"; 
    } catch (NumberFormatException ex) {
//        request.setAttribute("klaida", "Blogas todo Id");
//        RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
//        rd.forward(request, response);
//        return;
    }

    Query q = em.createQuery("select u from Uzduotis u where u.vartotojas= :vartotojas and u.id= :todoId");
    q.setParameter("todoId", todoId);
    q.setParameter("vartotojas", v);
    List<Uzduotis> list = q.getResultList();
    Uzduotis u = null;
    String link = "./todoEdit.jsp?";
    if (!list.isEmpty()) {
        u = list.get(0);
        link += "todoId=" + u.getId();
    }
    if (u != null && !u.getVartotojas().equals(v)) {
        u = null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//tikrinam ar redaguojam maza uzduotį
    MazaUzduotis maza = null;
    String mazaIdStr = request.getParameter("mazaId");
    if (mazaIdStr != null) {
        Integer mazaId = Integer.valueOf(mazaIdStr);
        maza = em.find(MazaUzduotis.class, mazaId);
    }

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/9a66ff09af.js" crossorigin="anonymous"></script>
        <style>
            .link {
                text-decoration: none;
            }
            .mygtukas{
                width: 150px;
            }
        </style>
        <title>Viena užduotis</title>
    </head>
    <body>
        <div class="container">
            <p class="text-end"> Prisijungęs kaip <%=((v != null) ? v.getVardas() : "Anonimas")%> <a href="./logout"> Logout</a></p>
            <p class="text-end">  <a href="./change.jsp">Keisti slaptažodį</a></p>
            <%if (u == null) {%>
            <div class="p-3 text-secondary">Pridedam naują užduotį <%=v.getVardas()%></div>
            <%} else {%>
            <h4 class="p-3 text-secondary">Keičiam užduotį <%=u.getVartotojas().getVardas()%></h4> 

            <%}%>
            <form action="./saveTodo" method="POST">
                <div class="row">
                    <a href="./todo.jsp" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Rodyti visas</div></a>
                    <%if (u != null) {%>                     
                    <a href="./saveTodo?id=<%=u.getId()%>&done=<%=((u.getAtlikta() == null) ? "setTrue" : "setFalse")%>" class="text-secondary link col-1 mb-3"><div class="btn btn-outline-success">Pažymėti kaip atliktą</div></a>
                </div>

                <input type="hidden" name="id" value="<%=u.getId()%>">
                <%} else {%> 
                </div><%}%>                
                <div class="input-group mb-3"><span class="input-group-text" id="pavadinimas">Pavadinimas:</span> <input type="text"
                                                                                                                         name="pavadinimas" class="form-control" value="<%= u != null ? u.getPavadinimas() : ""%>"></div>
                <div class="input-group mb-3"> <span class="input-group-text" id="aprasymas">Aprašymas: </span><input
                        type="text" name="aprasymas" class="form-control" value="<%= u != null ? u.getAprasymas() : ""%>"></div>
                <div class="input-group mb-3"> <span class="input-group-text" id="ikiKada">Iki kada: 
                    </span><input type="date" name="ikiKada" class="form-control" value="<%= u != null && u.getIkiKada() != null ? sdf.format(u.getIkiKada()) : ""%>"></div>
                <div class="input-group mb-3"> <span class="input-group-text" id="statusas">Statusas (proc.): </span><input
                        type="numer" name="statusas" class="form-control" value="<%= u != null ? u.getStatusas() : "0"%>" min="0" max="100"></div>
                <div class="input-group mb-3"> <span class="input-group-text" id="atlikta">Kada atlikta: 
                    </span><input type="date" name="atlikta" class="form-control" value="<%= u != null && u.getAtlikta() != null ? sdf.format(u.getAtlikta()) : ""%>"></div>

                <input class="btn btn-outline-primary mb-3" type="submit" value="Saugoti">
                <a href="./todo.jsp" class="text-danger link mb-3"><div class="btn btn-outline-danger mb-3">Cancel</div></a>
            </form>
            <%if (u != null) {

            %>
            <a href="<%=link%>&newMaza=true" class="link"><div class="btn btn-info mygtukas">+ Nauja maza užduotis</div></a>
            <table class="table table-striped table-hover">
                <thead class="table-light">
                <th>ID</th>
                <th>Pavadinimas</th>
                <th>Aprašymas</th>                
                <th>Kada atlikta</th>
                <th></th>
                <th></th>
                <th></th>
                </thead>
                <tbody>
                    <%                        List<MazaUzduotis> mazos = u.getMazosUzduotys();
                        if (!mazos.isEmpty()) {
                            for (MazaUzduotis m : mazos) {

                                if (m.equals(maza)) {%>
                    <tr> 
                <form action="./saveMazaUzduotis" method="POST">
                    <td>
                        <input type="hidden" name="id" value="<%=m.getId()%>">
                        <input type="hidden" name="todoId" value="<%=u.getId()%>">
                    </td>
                    <td>
                        <input type="text" name="pavadinimas" class="form-control" value="<%=(m.getPavadinimas() != null) ? m.getPavadinimas() : ""%>">
                    </td>
                    <td>
                        <input type="text" name="aprasymas" class="form-control" value="<%=(m.getAprasymas() != null) ? m.getAprasymas() : ""%>">
                    </td>
                    <td>
                        <input type="date" name="atlikta" class="form-control" value="<%=(m.getAtlikta() != null) ? sdf.format(m.getAtlikta()) : ""%>">
                    </td>
                    <td><input class="btn btn-outline-primary mb-3" type="submit" value="Saugoti"></td>
                    <td><a href="<%=link%>" class="text-danger link mb-3"><div class="btn btn-outline-danger">Cancel</div></a></td>
                    <td></td>
                </form>
                </tr>
                <%   } else {
                %>
                <tr>
                    <td>
                        <%=m.getId()%>
                    </td>
                    <td>
                        <%=(m.getPavadinimas() != null) ? m.getPavadinimas() : ""%>
                    </td>
                    <td>
                        <%=(m.getAprasymas() != null) ? m.getAprasymas() : ""%>
                    </td>
                    <td>
                        <%=(m.getAtlikta() != null) ? sdf.format(m.getAtlikta()) : ""%>
                    </td>
                    <td><a href="deleteMazaUzduotis?mazaId=<%=m.getId()%>" class="text-danger"><i class="fas fa-trash-alt"></i></a></td>
                    <td><a href="<%=link%>&mazaId=<%=m.getId()%>" class="text-info"><i class="fas fa-edit"></i></a></td>
                    <td><a href="./saveMazaUzduotis?todoId=<%=u.getId()%>&id=<%=m.getId()%>&done=<%=((m.getAtlikta() == null) ? "setTrue" : "setFalse")%>" class="text-secondary link"><b><%=((m.getAtlikta() == null) ? "&#9744;" : "&#9745;")%></b></a></td>
                </tr>
                <%}
                    }
                } else {
                %>
                <h5>Mažų užduočių sąrašas tuščias. Norėdami pridėti, spauskite mygtuką nauja užduotis.</h5>
                <%
                    }
                    String newMaza = request.getParameter("newMaza");
                    if (newMaza != null && !newMaza.trim().equals("")) {%>
                <tr> 
                <form action="./saveMazaUzduotis" method="POST">
                    <td>  
                        <input type="hidden" name="todoId" value="<%=u.getId()%>">                      
                    </td>
                    <td>
                        <input type="text" class="form-control" name="pavadinimas"%>
                    </td>
                    <td>
                        <input type="text" class="form-control" name="aprasymas">
                    </td>
                    <td>
                        <input type="date" class="form-control" name="atlikta">
                    </td>
                    <td><input class="btn btn-outline-primary mb-3" type="submit" value="Saugoti"></td>
                    <td><a href="<%=link%>" class="text-danger link mb-3"><div class="btn btn-outline-danger">Cancel</div></a></td>
                </form>
                </tr>

                <%}
                    }%>
                </tbody>
            </table>

            <%
            %>

        </div>
    </body>
</html>
