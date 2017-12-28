<%-- 
    Document   : Nuevo Producto
    Created on : 26/06/2017, 07:14:27 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo Producto</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nuevo Producto</h1>
            <form action="InventarioServlet" class="w3-container" style="width: 60%; margin: auto">
                <label class="w3-text-purple w3-left"><b>Nombre del Producto</b></label>
                <input type="text" name="nombre" class="w3-input w3-border w3-khaki" value="" required>
                <label class="w3-text-purple w3-left"><b>Cantidad</b></label>
                <input type="number" min="1" name="cantidad" class="w3-input w3-border w3-khaki" value="1" required />
                <label class="w3-text-purple w3-left"><b>Minimo</b></label>
                <input type="number" min="0" name="minimo" class="w3-input w3-border w3-khaki" value="0" required />
                <label class="w3-text-purple w3-left"><b>Precio</b></label>
                <input type="number" min="1000" name="precio" class="w3-input w3-border w3-khaki" value="1000" required />
                
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <input type="hidden" name="vaccion" value="guardarproducto">
                <!--<a href="InventarioServlet" class="w3-btn w3-blue w3-margin-top" >Cancelar</a>-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top" >Cancelar</a>
                
            </form>
            <br>
        </div>
    </body>
</html>
