<%-- 
    Document   : Roles
    Created on : 30/06/2017, 03:49:44 PM
    Author     : Jose Segovia
--%>

<%@page import="Tablas.Rol"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Roles</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen todos los Roles para su uso-->
        <%  ArrayList<Rol> roles = (ArrayList<Rol>) request.getAttribute("roles");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                    <tr class="w3-gray w3-xxlarge">
                        <th colspan="11" class="cabecera">Roles</th>
                    </tr>
                <tr>
                    <th>ID</th>
                    <th>Rol</th>
                    <th>Cliente</th>
                    <th>Empleado</th>
                    <th>Habitacion</th>
                    <th>Inventario</th>
                    <th>Roles</th>
                    <th>Socios</th>
                    <th>Historial</th>
                    <th>Factura</th>
                    <th></th>
                </tr>
                </thead>
    <%          for(Rol r : roles){     %>    
                <tr>
                    <td> <%= r.getId_rol()%> </td>
                    <td> <%= r.getNombre_rol() %> </td>
                    <td><%= r.getCliente()%> </td>
                    <td><%= r.getEmpleado()%> </td>
                    <td><%= r.getHabitacion()%> </td>
                    <td><%= r.getInventario()%> </td>
                    <td><%= r.getRoles()%> </td>
                    <td><%= r.getSocios()%> </td>
                    <td><%= r.getHistorial()%> </td>
                    <td><%= r.getFactura()%> </td>
                    <td>
                        <div class="w3-dropdown-hover">
                            <button class="w3-button w3-red w3-hover-blue">Opciones</button>
                            <div class="w3-dropdown-content w3-bar-block w3-card-4">
                                <!--Este boton pide la modificar los permisos del Rol-->
                                <a href="RolServlet?vaccion=mod&vid=<%= r.getId_rol()%>" class="w3-bar-item w3-button w3-red w3-hover-blue">Asignar Permisos</a> 
                                <!--Este boton pide la eliminacion del Rol-->
                                <a href="RolServlet?vaccion=eliminar&vid=<%= r.getId_rol() %>" class="w3-bar-item w3-button w3-red w3-hover-blue">Eliminar</a> 
                            </div>
                        </div>
                    </td>
                </tr>
        <%      }       %>
                <tr>
                    <td colspan="11"> 
                        <!--Este boton lleva a la Creacion de un Nuevo Rol-->
                        <a href="RolServlet?vaccion=nuevorol" class="w3-button w3-circle w3-teal w3-hover-green">+</a>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
