<%-- 
    Document   : change
    Created on : 28 Feb 2022, 21:15:19
    Author     : elzbi
--%>

<%@page import="lt.bit.todo.data.Vartotojas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%Vartotojas v=(Vartotojas)request.getAttribute("vartotojas");
%>



<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
<html>

<head>
    <title>Slaptažodžio keitimas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>
    <div class="container">
        <div class="p-3 text-secondary">Keisti slaptažodį</div>
        <form action="./changePassword" method="POST">
            <div class="input-group mb-3"><span class="input-group-text" id="senas">Senas slaptažodis:</span> <input type="password"
                    name="senas" class="form-control"></div>
                    <div class="input-group mb-3"><span class="input-group-text" id="naujas">Naujas slaptažodis:</span> <input type="password"
                    name="naujas" class="form-control"></div>
            <div class="input-group mb-3"> <span class="input-group-text" id="naujas2">Pakartoti naują: </span><input
                    type="password" name="naujas2" class="form-control"></div>
            <input class="btn btn-primary mb-3" type="submit" value="Keisti">
            <a href="./todo.jsp">Cancel</a>
        </form>
    </div>
</body>

</html>
