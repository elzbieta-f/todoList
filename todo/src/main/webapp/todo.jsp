<%-- Document : todo Created on : 21 Feb 2022, 20:37:00 Author : elzbi --%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="lt.bit.todo.data.Uzduotis" %>
<%@page import="java.util.List" %>
<%@page import="javax.persistence.Query" %>
<%@page import="lt.bit.todo.data.Vartotojas" %>
<%@page import="javax.persistence.EntityManager" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% Vartotojas v = (Vartotojas) request.getAttribute("vartotojas");

    String filter = request.getParameter("filter");
    String qStr = "";
    if (filter != null) {
        qStr = " and UPPER(u.pavadinimas) like UPPER(:filter) or UPPER(u.aprasymas) like UPPER(:filter) ";
    }
    String done = request.getParameter("done");
    if (done != null) {
        if (done.equals("true")) {
            qStr += " and u.statusas=100 ";
        } else if (done.equals("false")) {
            qStr += " and u.statusas is not 100 ";
        } else if (done.equals("asc")) {
            qStr += " and u.atlikta is not 0 order by u.atlikta asc ";
        } else if (done.equals("desc")) {
            qStr += " and u.atlikta is not 0 order by u.atlikta desc ";
        }
    }
    String sortStatus = request.getParameter("sortStatus");
    if (sortStatus != null) {
        if (sortStatus.equals("desc")) {
            qStr += " order by u.statusas desc ";
        } else if (sortStatus.equals("asc")) {
            qStr += " order by u.statusas asc";
        }
    }
     String sortDate = request.getParameter("sortDate");
    if (sortDate != null) {
        if (sortDate.equals("desc")) {
            qStr += " and u.ikiKada is not 0 order by u.ikiKada desc ";
        } else if (sortDate.equals("asc")) {
            qStr += " and u.ikiKada is not 0 order by u.ikiKada asc ";
        }
    }
    
    EntityManager em = (EntityManager) request.getAttribute("em");

    Query q = em.createQuery("select u from Uzduotis u where u.vartotojas is :vartotojas" + qStr);
    q.setParameter("vartotojas", v);
    if (filter != null) {
        q.setParameter("filter", "%" + filter + "%");
    }
    List<Uzduotis> uzduotys = q.getResultList();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
        <title>To Do</title>
    </head>

    <body>
        <div class="container">
            <p class="text-end"> Prisijungęs kaip <%=((v!=null)?v.getVardas():"Anonimas")%> <a href="./logout">Logout</a></p>
            <p class="text-end">  <a href="./change.jsp">Keisti slaptažodį</a></p>
            <h1>Vartotojo <%=v.getVardas()%> TO DO list</h1>

            <%if (uzduotys == null) {%>
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
            <div class="row">
                <a href="./todo.jsp" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Rodyti visas</div></a>                
                <a href="./todo.jsp?done=false" class="text-secondary link mb-3 col-1"><div class="btn btn-outline-secondary">Rodyti nebaigtas užduotis</div></a>
                <a href="./todo.jsp?done=true" class="text-secondary link mb-3 col-1"><div class="btn btn-outline-secondary">Rodyti užbaigtas užduotis</div></a>
                <a href="./todo.jsp?sortStatus=desc" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Rūšiuoti: statusas mažėjimo</div></a>
                <a href="./todo.jsp?sortStatus=asc" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Rūšiuoti: statusas didėjimo</div></a>
                <a href="./todo.jsp?sortDate=asc" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Iki kada: nuo skubiausių</div></a>
                <a href="./todo.jsp?sortDate=desc" class="text-secondary link mb-3 col-1 h-100"><div class="h-100 btn btn-outline-secondary">Nuo mažiausiai skubių</div></a>
                <a href="./todo.jsp?done=asc" class="text-secondary link mb-3 col-1"><div class="btn btn-outline-secondary">Nuo anksčiausiai atliktų</div></a>
                <a href="./todo.jsp?done=desc" class="text-secondary link mb-3 col-1 mx-1"><div class="btn btn-outline-secondary">Nuo vėliausiai atliktų</div></a>
            </div>
            <table class="table table-striped table-hover">
                <thead class="table-light">
                <th>ID</th>
                <th>Užduotis</th>
                <th>Aprašymas</th>
                <th>Iki kada</th>
                <th>Statusas</th>
                <th>Atlikta</th>
                <th></th>
                <th></th>
                <th></th>
                </thead>
                <tbody>

                    <%
                        for (Uzduotis uzd : uzduotys) {

                    %>
                    <tr>
                        <td>
                            <%=uzd.getId()%>
                        </td>
                        <td>
                            <%=(uzd.getPavadinimas() != null) ? uzd.getPavadinimas() : ""%>
                        </td>
                        <td>
                            <%=(uzd.getAprasymas() != null) ? uzd.getAprasymas() : ""%>
                        </td>
                        <td>
                            <%=(uzd.getIkiKada() != null) ? sdf.format(uzd.getIkiKada()) : ""%>
                        </td>
                        <td>
                            <%=(uzd.getStatusas() != null) ? uzd.getStatusas() + " %" : ""%>
                        </td>
                        <td>
                            <%=(uzd.getAtlikta() != null) ? sdf.format(uzd.getAtlikta()) : ""%>
                        </td>
                        <td><a href="deleteTodo?id=<%=uzd.getId()%>" class="text-danger"><i class="fas fa-trash-alt"></i></a></td>
                        <td><a href="./todoEdit.jsp?todoId=<%=uzd.getId()%>" class="text-info"><i class="fas fa-edit"></i></a></td>
                        <td><a href="./saveTodo?id=<%=uzd.getId()%>&done=<%=((uzd.getAtlikta() == null) ? "setTrue" : "setFalse")%>" class="text-secondary link"><b><%=((uzd.getAtlikta() == null) ? "&#9744;" : "&#9745;")%></b></a></td>
                    </tr>
                    <%}
                        }%>
                </tbody>

            </table>
            <a href="todoEdit.jsp" class="link"><div class="btn btn-info mygtukas">+ Nauja užduotis</div></a>

        </div>
    </body>

</html>