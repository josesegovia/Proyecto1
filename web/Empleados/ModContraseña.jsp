<%-- 
    Document   : ModContraseña
    Created on : 21/12/2017, 04:40:33 PM
    Author     : Admin
--%>

<%@page import="Tablas.Empleado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Empleado</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%  Empleado e = (Empleado) request.getAttribute("empleado");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            
            <h1>Modificar Rol de Empleado</h1>
            <form action="EmpleadoServlet" class="w3-container" style="width: 60%; margin: auto">

                <label class="w3-text-purple w3-left"><b>Contraseña</b></label>
                <input type="password" name="pass" class="w3-input w3-border w3-khaki" value="<%= e.getPassword()%>" required />

                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <input type="hidden" name="vid" value="<%= e.getId_empleado()%>"> 
                <input type="hidden" name="vaccion" value="passguardar"> 
                <!--<a href="EmpleadoServlet" class="w3-btn w3-blue w3-margin-top">Cancelar</a>-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
                
            </form>
            <br>
        </div>
    </body>
</html>
