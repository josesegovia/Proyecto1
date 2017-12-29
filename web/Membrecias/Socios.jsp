<%-- 
    Document   : Membrecias
    Created on : 15/09/2017, 04:57:48 PM
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
        <title>Membrecias</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen los Socios para su uso-->
        <%  ArrayList<Membrecia> socios = (ArrayList<Membrecia>) request.getAttribute("socios");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                <tr class="w3-gray w3-xxlarge">
                    <th colspan="5" class="cabecera">Membrecias</th>
                </tr>
                <tr>
                    <th>ID</th>
                    <th>Cliente</th>
                    <th>Estado</th>
                    <th></th>
                </tr>
                </thead>
                <%  for(Membrecia m : socios){  %>
                    <%  ControlCliente cl = new ControlCliente();  %>
                    <%  Cliente c = cl.GetbyId(m.getId_cliente());  %>
                <tr>
                    <td><%= m.getId_membrecia()%></td>
                    <td><%= c.getNombre()+" "+c.getApellido() %></td>
                    <td><%= m.getEstado()%></td>
                    <td>
                        <div class="w3-dropdown-hover">
                            <button class="w3-button w3-red w3-hover-blue">Opciones</button>
                            <div class="w3-dropdown-content w3-bar-block w3-card-4">
                                <!--Este boton pide la eliminacion de la Membrecia de un Cliente o Socio-->
                                <a href="MembreciaServlet?vaccion=eliminar&vid=<%= m.getId_membrecia() %>" class="w3-bar-item w3-button w3-red w3-hover-blue">Eliminar</a> 
                            </div>
                        </div>
                    </td>
                </tr>
                <%  }   %>
                <tr>
                    <td colspan="5"> 
                        <!--Este boton lleva a la Creacion de una Nueva Membrecia-->
                        <a href="MembreciaServlet?vaccion=nuevo" class="w3-button w3-circle w3-teal w3-hover-green">+</a> 
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
