<%-- 
    Document   : Pagos
    Created on : 21/12/2017, 06:49:18 PM
    Author     : Jose Segovia
--%>

<%@page import="Tablas.*"%>
<%@page import="Controladores.*"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagos</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen todos los Pagos para su uso-->
        <%  ArrayList<SociosPagos> pagos = (ArrayList<SociosPagos>) request.getAttribute("pagos");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                <tr class="w3-gray w3-xxlarge">
                    <th colspan="5" class="cabecera">Pagos de Socios</th>
                </tr>
                <tr>
                    <th>ID</th>
                    <th>Cliente</th>
                    <th>Estado</th>
                    <th>Fecha de Pago</th>
                    <th></th>
                </tr>
                </thead>
        <%          for(SociosPagos s : pagos){     %>   
                    <%  ControlCliente cl = new ControlCliente();    %>
                    <%  Cliente c = cl.GetbyId(s.getId_socio());    %>
                <tr>
                    <td><%= s.getId_pagos() %></td>
                    <td><%= c.getNombre()+" "+c.getApellido() %></td>
                    <td><%= s.getEstado() %></td>
                    <td><%= s.getFecha() %></td>
                    <td>
                        <div class="w3-dropdown-hover">
                            <button class="w3-button w3-red w3-hover-blue">Opciones</button>
                            <div class="w3-dropdown-content w3-bar-block w3-card-4">
                                <!--Este boton pida la eliminacion del Pago-->
                                <a href="MembreciaServlet?vaccion=eliminarpago&vid=<%= s.getId_pagos() %>" class="w3-bar-item w3-button w3-red w3-hover-blue">Eliminar</a> 
                            </div>
                        </div>
                    </td>
                </tr>
        <%          }       %>
                <tr>
                    <td colspan="5"> 
                        <!--Este boton pide la Creacion de un nuevo Pago-->
                        <a href="MembreciaServlet?vaccion=nuevopago" class="w3-button w3-circle w3-teal w3-hover-green">+</a> 
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
