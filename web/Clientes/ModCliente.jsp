<%-- 
    Document   : ModClientes
    Created on : 13/09/2017, 06:34:07 PM
    Author     : Jose Segovia
--%>

<%@page import="Tablas.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clientes</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen el Cliente que se va a modificar con sus valores originales-->
        <%  Cliente c = (Cliente) request.getAttribute("cliente");    %>
        <%@include file="/Navegacionjsp.jspf" %>
            <div class="w3-container w3-center w3-blue-gray body-height">
                <h1>Modificar Cliente</h1>
                <!--Un Form hacia ClienteServlet para modificar el cliente-->
                <form action="ClienteServlet" method="POST" class="w3-container" style="width: 60%; margin: auto">
                    <!--Se muestra el viejo Nombre, el cual puede ser cambiado-->
                    <label class="w3-text-purple w3-left"><b>Nombre</b></label>
                    <input type="text" name="nombre" class="w3-input w3-border w3-khaki" value="<%= c.getNombre() %>" required autofocus>
                    <!--Se muestra el viejo Apellido, el cual puede ser cambiado-->
                    <label class="w3-text-purple w3-left"><b>Apellido</b></label>
                    <input type="text" name="apellido" class="w3-input w3-border w3-khaki" value="<%= c.getApellido() %>" required />
                    <!--Se muestra la vieja Cedula, el cual puede ser cambiado-->
                    <label class="w3-text-purple w3-left"><b>Cedula</b></label>
                    <input type="text" name="cedula" class="w3-input w3-border w3-khaki" value="<%= c.getCedula() %>" required />
                    <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                    <!--El input vid envia el id del Cliente que se va a modificar-->
                    <input type="hidden" name="vid" value="<%= c.getId_cliente() %>">
                    <!--El input vaccion indica la accion a realizarse-->
                    <input type="hidden" name="vaccion" value="modificarcliente"> 
                    <!--Este boton lleva a la pagina anterior-->
                    <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
                </form>
                <br>
            </div>
    </body>
</html>
