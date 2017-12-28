<%-- 
    Document   : ReservarHabitacion
    Created on : 22/12/2017, 04:07:33 PM
    Author     : Admin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Tablas.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reservar Habitacion</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%  Habitaciones h = (Habitaciones) request.getAttribute("habitacion");    %>
        <%  ArrayList<Cliente> clientes = (ArrayList<Cliente>) request.getAttribute("clientes");  %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Reservar Habitacion</h1>
            <form action="HabitacionServlet" class="w3-container" style="width: 30%; margin: auto">
                
                <label class="w3-text-purple w3-left"><b>Cliente</b></label>
                <select name="cliente" class="w3-input w3-border w3-khaki">
                    <% for(Cliente c : clientes){ %>
                         <%  String nombre = c.getNombre() + " " + c.getApellido(); %>
                            <option value="<%= c.getId_cliente() %>"> <%= nombre %> </option>
                <%  }   %>
                </select>
                <label class="w3-text-purple w3-left"><b>Fecha</b></label>
                <input type="date" name="fecha" class="w3-input w3-border w3-khaki" required="">
                <label class="w3-text-purple w3-left"><b>Hora</b></label>
                <input type="time" name="hora" class="w3-input w3-border w3-khaki" required="">
                
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <input type="hidden" name="vaccion" value="reservarhabitacion">
                <input type="hidden" name="vid" value="<%= h.getId_habitacion()%>">
                <!--<a href="HabitacionServlet" class="w3-btn w3-blue w3-margin-top">Cancelar</a>-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
