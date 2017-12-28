<%-- 
    Document   : ModificarProducto
    Created on : 27/06/2017, 03:58:10 PM
    Author     : Admin
--%>

<%@page import="Tablas.Inventario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar Producto</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%@include file="/Navegacionjsp.jspf" %>
        <%  Inventario i = (Inventario) request.getAttribute("producto");  %>
            <div class="w3-container w3-center w3-blue-gray body-height">
                <h1>Modificar Producto: <%= i.getNombre()%> </h1>
                <form action="InventarioServlet" class="w3-container" style="width: 60%; margin: auto">
                    
                    <label class="w3-text-purple w3-left"><b>Nombre del Producto</b></label>
                    <input type="text" name="nombre" class="w3-input w3-border w3-khaki" value="<%= i.getNombre()%>" required>
                    <label class="w3-text-purple w3-left"><b>Cantidad</b></label>
                    <input type="number" min="1" name="cantidad" class="w3-input w3-border w3-khaki" value="<%= i.getCantidad() %>" required />
                    <label class="w3-text-purple w3-left"><b>Minimo</b></label>
                    <input type="number" min="0" name="minimo" class="w3-input w3-border w3-khaki" value="<%= i.getMinimo()%>" required />
                    <label class="w3-text-purple w3-left"><b>Precio</b></label>
                    <input type="number" min="1000" name="precio" class="w3-input w3-border w3-khaki" value="<%= i.getPrecio()%>" required />
                                       
                    <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                    <input type="hidden" name="vaccion" value="modificarproducto"> 
                    <input type="hidden" name="vid" value="<%= i.getId_inventario()%>"> 
                    <!--<a href="InventarioServlet" class="w3-btn w3-blue w3-margin-top">Cancelar</a>-->
                    <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
                    
                </form>
                <br>
            </div>
    </body>
</html>
