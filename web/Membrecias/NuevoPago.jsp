<%-- 
    Document   : NuevoPago
    Created on : 21/12/2017, 09:41:22 PM
    Author     : Jose Segovia
--%>

<%@page import="Tablas.Cliente"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagos</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen todos los Clientes que estan que no han pagado o deben (estan Inactivos)-->
        <%  ArrayList<Cliente> clientes = (ArrayList<Cliente>) request.getAttribute("clientes");  %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nuevo Pago</h1>
            <!--Un Form hacia MembreciaServlet para crear el Nuevo Pago-->
            <form action="MembreciaServlet" class="w3-container" style="width: 20%; margin: auto">
                <!--Se selecciona el Cliente que esta pagando-->
                <label class="w3-text-purple w3-left"><b>Socio</b></label>
                <select name="socio" class="w3-input w3-border w3-khaki">
                    <% for(Cliente c : clientes){ %>
                         <%  String nombre = c.getNombre() + " " + c.getApellido(); %>
                            <option value="<%= c.getId_cliente()%>"> <%= nombre %> </option>
                <%  }   %>
                </select>
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <!--El input vaccion indica la accion a realizarse-->
                <input type="hidden" name="vaccion" value="pagoguardar">
                <!--Se regresa a la pagina anterior-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
