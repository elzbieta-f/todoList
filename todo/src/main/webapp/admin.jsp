<%-- 
    Document   : admin
    Created on : 23 Feb 2022, 18:40:41
    Author     : elzbi
--%>

<%@page import="java.util.List"%>
<%@page import="javax.persistence.Query"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="lt.bit.todo.data.Vartotojas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");
    EntityManager em = (EntityManager) request.getAttribute("em");
    if (!v.getAdmin()) {
        request.setAttribute("klaida", "Nesate autorizuotas pamatyti šį puslapį");
        RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
        rd.forward(request, response);
        return;
    }
    String filter = request.getParameter("filter");
    String qStr = "";
    if (filter != null) {
        qStr = " where UPPER(v.vardas) like UPPER(:filter)";
    }
    Query q = em.createQuery("select v from Vartotojas v"+qStr);
    if (filter != null) {
        q.setParameter("filter", "%" + filter + "%");
    }
    List<Vartotojas> list = q.getResultList();
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
            .h-100 {
                height: 100px;
            }
        </style>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <p class="text-end"> Prisijungęs kaip <%=((v != null) ? v.getVardas() : "Anonimas")%> <a href="./logout">Logout</a></p>
            <p class="text-end">  <a href="./change.jsp">Keisti slaptažodį</a></p>
            <p class="text-end">  <a href="./todo.jsp" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Į užduotis</div></a></p>    
            <h1>Vartotojų sąrašas</h1>

            <%if (list == null) {%>
            <h2>Sąrašas tuščias</h2>
            <%} else {%>
            <form class="p-3">
                <div class="row">              
                    <div class="mb-3 col-6">
                        <input class="form-control" id="filter" class="form-text" name="filter">
                    </div>
                    <div class="mb-3 col-2"> <input class="btn btn-secondary text-light" type="submit" value="Ieškoti">
                    </div>
                </div>
            </form>
            <table class="table table-striped table-hover">
                <thead class="table-light">
                <th>ID</th>
                <th>Vardas</th>
                <th>Administratorius?</th>
                <th>Suteikti admino teises</th>
                <th>Atimti admino teises</th>
                <th>Trinti</th>
                </thead>
                <tbody>

                    <%
                        for (Vartotojas var : list) {%>
                    <tr>
                        <td>
                            <%=var.getId()%>
                        </td>
                        <td>
                            <%=(var.getVardas() != null) ? var.getVardas() : ""%>
                        </td>
                        <td>
                            <%=(var.getAdmin()) ? "Taip" : "Ne"%>
                        </td>
                        <td><a href="setAdmin?id=<%=var.getId()%>&setAdmin=true" class="text-success link">Suteikti</a></td>
                        <td><a href="setAdmin?id=<%=var.getId()%>&setAdmin=false" class="text-danger link">Atimti</a></td>
                        <td><a href="deleteVartotojas?id=<%=var.getId()%>" class="text-danger"><i class="fas fa-trash-alt"></i></a></td>
                    </tr>

                    <%
                                }%>
                </tbody>   
            </table>
            <%
                }

            %>
        </div>
    </body>
</html>
