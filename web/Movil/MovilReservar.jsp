<%-- 
    Document   : MovilReservar
    Created on : 25/12/2017, 06:51:56 PM
    Author     : Jose Segovia
--%>

<%@page import="Tablas.Habitaciones"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reservar</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtiene la Habitacion que se a solicitado Reservar-->
        <%  Habitaciones h = (Habitaciones) request.getAttribute("h");    %>
        <header class="w3-container w3-red w3-card w3-center w3-text-black">
            <h1>Hotel 4Quedas</h1>
        </header>
        <div class="w3-container">
            <h3 class="w3-brown w3-card w3-center w3-text-yellow">Eliga la habitacion que Prefiera</h3>
            <!--Un Form hacia MovilServlet para hacer la Reserva-->
            <form action="MovilServlet" class="w3-container" method="POST">
                <!--Se ingresa la Fecha que se quiere reservar-->
                <label class="w3-text-purple w3-left"><b>Fecha</b></label>
                <input type="date" name="fecha" class="w3-input w3-border w3-khaki" required="">
                <!--Se ingresa la Hora que quiere Reservar-->
                <label class="w3-text-purple w3-left"><b>Hora</b></label>
                <input type="time" name="hora" class="w3-input w3-border w3-khaki" required="">
                Se ingresa la Duracion de la Reserva en Minutos
                <label class="w3-text-purple w3-left"><b>Duracion de Reserva</b></label>
                <input type="number" name="tiempo" class="w3-input w3-border w3-khaki" min="30" value="30" required="">
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <!--El input vid envia el id de la Habitacion que se va a Reservar-->
                <input type="hidden" name="vid" value="<%= h.getId_habitacion()%>">
                <!--El input vaccion indica la accion a realizarse-->
                <input type="hidden" name="vaccion" value="reservarhabitacion">
                <!--Este boton lleva a la pagina anterior-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
        </div>
    </body>
</html>
