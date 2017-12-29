<%-- 
    Document   : NoPermiso
    Created on : 24/10/2017, 05:18:46 PM
    Author     : Jose Segovia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>No Permiso</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se redirecciona a esta pagina cuando no se tienen los permisos
        para hacer la accion que se solicito-->
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <p class="w3-xlarge">
                Usted no tiene los permisos para realizar tal accion
            </p>
            <!--Este boton lleva a la pagina anterior-->
            <a href="javascript: window.history.go(-1)" class="w3-button w3-red w3-hover-blue w3-large">Regresar</a>
        </div>
    </body>
</html>
