<%-- 
    Document   : NuevoSocio
    Created on : 15/09/2017, 05:04:38 PM
    Author     : Jose Segovia
--%>

<%@page import="Tablas.Cliente"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nueva Membrecia</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Obtiene todos los Clientes que no tengan Membrecia-->
        <%  ArrayList<Cliente> clientes = (ArrayList<Cliente>) request.getAttribute("clientes");  %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nuevo Socio</h1>
            <!--Un Form hacia MembreciaServlet para Crear una Nueva Membrecia-->
            <form action="MembreciaServlet" class="w3-container" style="width: 20%; margin: auto">
                <!--Se pide que se seleccione el Cliente al que se le Creara la Membrecia-->
                <label class="w3-text-purple w3-left"><b>Cliente</b></label>
                <select name="cliente" class="w3-input w3-border w3-khaki">
                    <% for(Cliente c : clientes){ %>
                         <%  String nombre = c.getNombre() + " " + c.getApellido(); %>
                            <option value="<%= c.getId_cliente()%>" selected> <%= nombre %> </option>
                <%  }   %>
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
