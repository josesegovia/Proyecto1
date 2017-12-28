<%-- 
    Document   : NuevoEmpleado
    Created on : 23/06/2017, 05:48:17 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo Empleado</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nuevo Empleado</h1>
            <form action="EmpleadoServlet" class="w3-container" style="width: 60%; margin: auto">
                <label class="w3-text-purple w3-left"><b>Nombre</b></label>
                <input type="text" name="nombre" class="w3-input w3-border w3-khaki" value="" required>
                <label class="w3-text-purple w3-left"><b>Apellido</b></label>
                <input type="text" name="apellido" class="w3-input w3-border w3-khaki" value="" required>
                <label class="w3-text-purple w3-left"><b>Cedula</b></label>
                <input type="text" name="cedula" class="w3-input w3-border w3-khaki" value="" required>
                <label class="w3-text-purple w3-left"><b>Nombre de Usuario</b></label>
                <input type="text" name="name_user" class="w3-input w3-border w3-khaki" value="" required><!--
                <label class="w3-text-purple w3-left"><b>Contraseña</b></label>
                <input type="text" name="pass" class="w3-input w3-border w3-khaki" value="" required />-->
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <input type="hidden" name="vaccion" value="guardar"> 
                <!--<a href="EmpleadoServlet" class="w3-btn w3-blue w3-margin-top" >Cancelar</a>-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top" >Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
