<%-- 
    Document   : Clientes
    Created on : 30/06/2017, 04:18:32 PM
    Author     : Admin
--%>

<%@page import="Tablas.Cliente"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clientes</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%  ArrayList<Cliente> clientes = (ArrayList<Cliente>) request.getAttribute("clientes");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                    <tr class="w3-gray w3-xxlarge">
                        <th colspan="5" class="cabecera">Clientes</th>
                    </tr>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Cedula</th>
                        <th></th>
                    </tr>
                </thead>
    <%          for(Cliente c : clientes){     %>    
                <tr>
                    <td><%= c.getId_cliente() %></td>
                    <td><%= c.getNombre() %></td>
                    <td><%= c.getApellido() %></td>
                    <td><%= c.getCedula() %></td>
                    <td> 
                        <div class="w3-dropdown-hover">
                            <button class="w3-button w3-red w3-hover-blue">Opciones</button>
                            <div class="w3-dropdown-content w3-bar-block w3-card-4">
                                <a href="ClienteServlet?vaccion=mod&vid=<%= c.getId_cliente()%>" class="w3-bar-item w3-button w3-red w3-hover-blue">Modificar</a>
                                <a href="ClienteServlet?vaccion=eliminar&vid=<%= c.getId_cliente()%>" class="w3-bar-item w3-button w3-red w3-hover-blue">Eliminar</a> 
                            </div>
                        </div>
                    </td>
                </tr>
        <%      }       %>
                <tr>
                    <td colspan="5"> 
                        <a href="ClienteServlet?vaccion=nuevocliente" class="w3-button w3-circle w3-teal w3-hover-green">+</a> 
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
