<%-- 
    Document   : MovilPrincipal
    Created on : 25/12/2017, 04:49:47 PM
    Author     : Admin
--%>

<%@page import="Tablas.*"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Principal</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%  ArrayList<Habitaciones> habitaciones = (ArrayList<Habitaciones>) request.getAttribute("habitaciones");    %>
        <header class="w3-container w3-red w3-card w3-center w3-text-black">
            <h1>Hotel 4Quedas</h1>
        </header>
        <div class="w3-container">
            <h3 class="w3-brown w3-card w3-center w3-text-yellow">Eliga la habitacion que Prefiera</h3>
            <hr>
            <div class="w3-cell-row w3-brown centro">
                <div class="w3-cell w3-center" style="width: 100%">
                    <p>Habitaciones Libres</p>
                </div>
            </div>
            <div class="w3-cell-row w3-green centro">
                <div class="w3-cell w3-center" style="width: 50%">
                    <p>Numero de Habitacion</p>
                </div>
                <div class="w3-cell w3-center" style="width: 50%">
                    <p>Tipo de Habitaciones</p>
                </div>
            </div>
            <%
        for(Habitaciones h : habitaciones){ %>
            <div class="w3-cell-row w3-blue centro">
                <div class="w3-cell w3-center" style="width: 50%">
                    <a href="MovilServlet?vaccion=reservar&vid=<%= h.getId_habitacion() %>" class="w3-button w3-blue w3-hover-red" style="width: 50%" ><%= h.getId_habitacion() %> </a>
                </div>
                <div class="w3-cell w3-center" style="width: 50%">
                    <p> <%= h.getTipo()%> </p>
                </div>
            </div>
     
        <%
        }
        %>
            <div class="w3-cell-row w3-orange centro">
                <div class="w3-cell w3-center" style="width: 100%;">
                    <a class="w3-centered w3-btn w3-blue w3-margin w3-hover-cyan" href="MovilServlet?vaccion=logout">LogOut</a>
                </div>
            </div>
        </div>
    </body>
</html>
