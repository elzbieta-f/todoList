<%-- Document : error Created on : 18 Feb 2022, 20:59:04 Author : elzbi --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>

        <!DOCTYPE html>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
                crossorigin="anonymous">
            <title>Klaida</title>
        </head>

        <body>
            <h1>Įvyko klaida</h1>
            <% Object klaidaObj=request.getAttribute("klaida"); String klaida=null; if (klaidaObj!=null){
                klaida=klaidaObj.toString(); } if (klaida!=null){%>
                <h2>
                    <%=klaida%>
                </h2>
                <% } %>
                    <a href="index.html">Į pagrindinį puslapį</a>
        </body>

        </html>