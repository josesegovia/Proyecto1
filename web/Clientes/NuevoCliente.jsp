<%-- 
    Document   : NuevoCliente
    Created on : 13/09/2017, 06:13:32 PM
    Author     : Jose Segovia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clientes</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nuevo Cliente</h1>
            <!--Un Form hacia ClienteServlet para crear el nuevo cliente-->
            <form action="ClienteServlet" class="w3-container" style="width: 60%; margin: auto">
                <!--Se piden que se ingresen el Nombre, Apellido y Cedula-->
                <label class="w3-text-purple w3-left"><b>Nombre</b></label>
                <input type="text" name="nombre" class="w3-input w3-border w3-khaki" value="" required autofocus>
                <label class="w3-text-purple w3-left"><b>Apellido</b></label>
                <input type="text" name="apellido" class="w3-input w3-border w3-khaki" value="" required />
                <label class="w3-text-purple w3-left"><b>Cedula</b></label>
                <input type="text" name="cedula" class="w3-input w3-border w3-khaki" value="" required />
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
