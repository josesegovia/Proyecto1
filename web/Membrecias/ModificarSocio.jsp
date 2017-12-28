<%-- 
    Document   : ModificarSocio
    Created on : 15/09/2017, 05:10:01 PM
    Author     : Admin
--%>

<%@page import="Tablas.Membrecia"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar Membrecia</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%  Membrecia m = (Membrecia) request.getAttribute("membrecia");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nuevo Socio</h1>
            <form action="MembreciaServlet" class="w3-container" style="width: 20%; margin: auto">
                <label class="w3-text-purple w3-left"><b>Estado</b></label>
                <select name="estado" class="w3-input w3-border w3-khaki">
                    <option selected="" value="Inactivo">Inactivo </option>
                    <option value="Activo">Activo</option>
                </select>
                    <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                    <input type="hidden" name="vaccion" value="modificarsocio">
                    <input type="hidden" name="vid" value="<%= m.getId_membrecia()%>">
                    <a href="MembreciaServlet" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
            <br>
        </div>
        <%@include file="/PieDePagina.jspf" %>
    </body>
</html>
