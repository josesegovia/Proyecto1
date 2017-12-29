<%-- 
    Document   : ModContraseña
    Created on : 21/12/2017, 04:40:33 PM
    Author     : Jose Segovia
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
        <!--Se obtienen el Empleado al que se le va a cambiar la contraseña-->
        <%  Empleado e = (Empleado) request.getAttribute("empleado");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Modificar Rol de Empleado</h1>
            <!--Un Form hacia EmpleadoServlet para modificar el empleado-->
            <form action="EmpleadoServlet" class="w3-container" style="width: 60%; margin: auto">
                <!--Se pide ingresar la nueva contraseña-->
                <label class="w3-text-purple w3-left"><b>Contraseña</b></label>
                <input type="password" name="pass" class="w3-input w3-border w3-khaki" required />
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <!--El input vid envia el id del empleado al que se le modificara la contraseña-->
                <input type="hidden" name="vid" value="<%= e.getId_empleado()%>"> 
                <!--El input vaccion indica la accion a realizarse-->
                <input type="hidden" name="vaccion" value="passguardar"> 
                <!--Este boton lleva a la pagina anterior-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
