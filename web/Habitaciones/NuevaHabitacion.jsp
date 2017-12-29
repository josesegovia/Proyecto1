<%-- 
    Document   : NuevaHabitacion
    Created on : 15/09/2017, 03:49:13 PM
    Author     : Jose Segovia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nueva Habitacion</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nueva Habitacion</h1>
            <!--Un Form hacia HabitacionServlet para crear una nueva Habitacion-->
            <form action="HabitacionServlet" class="w3-container" style="width: 20%; margin: auto">
                Se pide que seleccione el tipo de Habitacion 
                <label class="w3-text-purple w3-left"><b>Tipo</b></label>    
                <select name="tipo" class="w3-input w3-border w3-khaki" >
                    <option selected="" value="Economico">Economico</option>
                    <option value="Ejecutivo">Ejecutivo</option>
                    <option value="VIP">VIP</option>
                </select>
                <label class="w3-text-purple w3-left"><b>Estado</b></label>    
                <select name="estado" class="w3-input w3-border w3-khaki" >
                    <option selected="" value="L">Libre</option>
                    <option value="P">Limpiando</option>
                </select>
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
