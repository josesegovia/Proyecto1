<%-- 
    Document   : ModEmpleados
    Created on : 12/09/2017, 06:39:14 PM
    Author     : Admin
--%>

<%@page import="Tablas.Rol"%>
<%@page import="Tablas.Empleado"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabla de Empleados</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%  Empleado e = (Empleado) request.getAttribute("empleado");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Modificar Empleado</h1>
            <form action="EmpleadoServlet" class="w3-container" style="width: 60%; margin: auto">
                <label class="w3-text-purple w3-left"><b>Nombre</b></label>
                <input type="text" name="nombre" class="w3-input w3-border w3-khaki" value="<%= e.getNombre()%>" required>
                <label class="w3-text-purple w3-left"><b>Apellido</b></label>
                <input type="text" name="apellido" class="w3-input w3-border w3-khaki" value="<%= e.getApellido()%>" required>
                <label class="w3-text-purple w3-left"><b>Cedula</b></label>
                <input type="text" name="cedula" class="w3-input w3-border w3-khaki" value="<%= e.getCedula()%>" required>
                <label class="w3-text-purple w3-left"><b>Nombre de Usuario</b></label>
                <input type="text" name="name_user" class="w3-input w3-border w3-khaki" value="<%= e.getNombre_usuario()%>" required>
                                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <input type="hidden" name="vaccion" value="modificarempleado"> 
                <input type="hidden" name="vid" value="<%= e.getId_empleado()%>"> 
                <!--<a href="EmpleadoServlet" class="w3-btn w3-blue w3-margin-top" >Cancelar</a>-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top" >Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
