<%-- 
    Document   : Inventario
    Created on : 26/06/2017, 06:58:15 PM
    Author     : Admin
--%>

<%@page import="Tablas.*"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabla de Inventario</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%  ArrayList<Inventario> productos = (ArrayList<Inventario>) request.getAttribute("productos");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                    <tr class="w3-gray w3-xxlarge">
                        <th colspan="7" class="cabecera">Inventario</th>
                    </tr>
                    <tr>
                        <th>Id</th>
                        <th>Nombre</th>
                        <th>Cantidad</th>
                        <th>Faltante</th>
                        <th>Precio</th>
                        <th></th>
                    </tr>
                </thead>
    <%      for(Inventario i : productos){ %>    
    <%          int cantidad = i.getCantidad(); %>
    <%          int minimo = cantidad - i.getMinimo(); %>
    <%          if(minimo >0 ){ minimo = 0; } %>
                <tr>
                    <td> <%= i.getId_inventario()%> </td>
                    <td> <%= i.getNombre() %> </td>
                    <td> <%= cantidad %> </td>
                    <td> <%= -1*minimo %> </td>
                    <td> <%= i.getPrecio()%> </td>
                    <td>
                        <div class="w3-dropdown-hover">
                            <button class="w3-button w3-red w3-hover-blue">Opciones</button>
                            <div class="w3-dropdown-content w3-bar-block w3-card-4">
                                <a href="InventarioServlet?vaccion=mod&vid=<%= i.getId_inventario()%>" class="w3-bar-item w3-button w3-red w3-hover-blue" >Modificar</a>
                                <a href="InventarioServlet?vaccion=eliminar&vid=<%= i.getId_inventario()%>" class="w3-bar-item w3-button w3-red w3-hover-blue" >Eliminar</a>
                            </div>
                        </div>
                    </td>
                </tr>
    <%      }   %>
                <tr>
                    <td colspan="6"> <a href="InventarioServlet?vaccion=nuevoproducto" class="w3-button w3-circle w3-teal w3-hover-green">+</a> </td>
                </tr>
            </table>
        </div>
    </body>
</html>
