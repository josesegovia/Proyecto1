<%-- 
    Document   : Horarios
    Created on : 20/12/2017, 09:49:42 PM
    Author     : Jose Segovia
--%>

<%@page import="Controladores.*"%>
<%@page import="Tablas.*"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Horarios</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen los horarios de todos los Empleados para su uso-->
        <%  ArrayList<Horario> horarios = (ArrayList<Horario>) request.getAttribute("horarios");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                    <tr class="w3-gray w3-xxlarge">
                        <th colspan="9" class="cabecera">Horario</th>
                    </tr>
                    <tr>
                        <th>Empleado</th>
                        <th>Lunes</th>
                        <th>Martes</th>
                        <th>Miercoles</th>
                        <th>Jueves</th>
                        <th>Viernes</th>
                        <th>Sabado</th>
                        <th>Domingo</th>
                        <th></th>
                    </tr>
                </thead>
    <%          for(Horario h : horarios){     %>    
                <tr>
                <%  ControlEmpleado ce = new ControlEmpleado();
                    Empleado e = ce.GetbyId(h.getId_empleado());
                    String nombre = e.getNombre() + " " + e.getApellido(); %>
                    <td><%= nombre%></td>
                    <td><%= h.getLunes()%></td>
                    <td><%= h.getMartes()%></td>
                    <td><%= h.getMiercoles()%></td>
                    <td><%= h.getJueves()%></td>
                    <td><%= h.getViernes()%></td>
                    <td><%= h.getSabado() %></td>
                    <td><%= h.getDomingo() %></td>
                    <td> 
                        <div class="w3-dropdown-hover">
                            <button class="w3-button w3-red w3-hover-blue">Opciones</button>
                            <div class="w3-dropdown-content w3-bar-block w3-card-4">
                                <!--Este boton pide la modificacion del Horario-->
                                <a href="HorariosServlet?vaccion=mod&vid=<%= h.getId_empleado ()%>" class="w3-bar-item w3-button w3-red w3-hover-blue">Modificar</a>
                            </div>
                        </div>
                    </td>
                </tr>
        <%      }       %>
                <tr>
                    <td colspan="9"> 
                        <!--Este boton lleva a la Creacion de un Nuevo Horario-->
                        <a href="HorariosServlet?vaccion=nuevo" class="w3-button w3-circle w3-teal w3-hover-green">+</a> 
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
