<%-- 
    Document   : Habitaciones
    Created on : 15/09/2017, 03:38:24 PM
    Author     : Jose Segovia
--%>

<%@page import="Tablas.Habitaciones"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Habitaciones</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen todas las Habitaciones para su uso-->
        <%  ArrayList<Habitaciones> habitaciones = (ArrayList<Habitaciones>) request.getAttribute("habitaciones");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                    <tr class="w3-gray w3-xxlarge">
                        <th colspan="5" class="cabecera">Habitaciones</th>
                    </tr>
                    <tr>
                        <th>ID</th>
                        <th>Tipo</th>
                        <th>Estado</th>
                        <th></th>
                    </tr>
                </thead>
    <%          for(Habitaciones h : habitaciones){     %>    
                <tr>
                    <td><%= h.getId_habitacion()%></td>
                    <td><%= h.getTipo() %></td>
                    <%
                        //Se procesa el estado obtenido para mostrar como una palabra en vez de una letra
                    switch(h.getEstado()){
                        case "L":   %>
                        <td><%= "Libre" %></td>
                        <%break;
                        case "O":   %>
                        <td><%= "Ocupado" %></td>
                        <%    break;
                        case "P":   %>
                        <td><%= "Limpiando" %></td>

                        <%  break;
                        case "R":   %>
                        <td><%= "Reservado" %></td>
                        <%    break;
                    }
                    %>
                    <td> 
                        <div class="w3-dropdown-hover">
                            <button class="w3-button w3-red w3-hover-blue">Opciones</button>
                            <div class="w3-dropdown-content w3-bar-block w3-card-4">
                                <!--Este boton pide la modificacion de la Habitacion-->
                                <a href="HabitacionServlet?vaccion=mod&vid=<%= h.getId_habitacion()%>" class="w3-bar-item w3-button w3-red w3-hover-blue">Modificar</a> 
                                <!--Este boton pide la eliminacion de la Habitacion-->
                                <a href="HabitacionServlet?vaccion=eliminar&vid=<%= h.getId_habitacion()%>" class="w3-bar-item w3-button w3-red w3-hover-blue">Eliminar</a> 
                            </div>
                        </div>
                    </td>
                </tr>
        <%      }       %>
                <tr>
                    <!--Este boton lleva a la Creacion de una Nueva Habitacion-->
                    <td colspan="5"> <a href="HabitacionServlet?vaccion=nuevo" class="w3-button w3-circle w3-teal w3-hover-green">+</a> </td>
                </tr>
            </table>
        </div>
    </body>
</html>
