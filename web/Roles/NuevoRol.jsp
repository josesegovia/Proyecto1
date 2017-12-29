<%-- 
    Document   : NuevoRol
    Created on : 13/09/2017, 07:41:33 PM
    Author     : Jose Segovia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Roles</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nuevo Rol</h1>
            <!--Un Form hacia RolServlet para crear el Nuevo Rol-->
            <form action="RolServlet" class="w3-container" style="width: 60%; margin: auto">
                <!--Se pide que se ingrese el Nombre del Nuevo Rol-->
                <label class="w3-text-purple w3-left"><b>Nombre</b></label>
                <input type="text" name="nombre" class="w3-input w3-border w3-khaki" value="" required autofocus>
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <!--El input vaccion indica la accion a realizarse-->
                <input type="hidden" name="vaccion" value="guardar"> 
                <!--Se regresa a la pagina anterior-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
