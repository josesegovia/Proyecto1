<%-- 
    Document   : ModificarHabitacion
    Created on : 15/09/2017, 04:04:29 PM
    Author     : Jose Segovia
--%>

<%@page import="Tablas.Habitaciones"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar Habitacion</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtiene la Habitacion que se va a Modificar-->
    <%  Habitaciones h = (Habitaciones) request.getAttribute("habitacion");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <%  String estado = h.getEstado();
            String est = estado;
            //Se crea un String estado que muestra el estado de la Habitacion como una palabra
            switch(estado){
                case "L":
                    est="Libre";
                    break;
                case "O":   
                    est="Ocupado";
                    break;
                case "P":   
                    est="Limpiando";
                    break;
                case "R":   
                    est="Reservado";
                    break;
            }   %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nueva Habitacion</h1>
            <!--Un Form hacia HabitacionServlet para modificar la Habitacion-->
            <form action="HabitacionServlet" class="w3-container" style="width: 30%; margin: auto">
                <label class="w3-text-amber w3-xlarge w3-left"><b>Estado es: <span class="w3-text-aqua"><%= est %> </span> </b></label>
                <br>
                <!--Si estado es Libre se puede cambiar a Ocupado o Reservar manualmente-->
            <%  if(estado.equals("L")){   %>
                        <p style="text-align: left" >
                            <input type="radio" class="w3-radio" name="estado" value="O" checked>
                        <label>Ocupado</label>
                        </p>
                        <p style="text-align: left" >
                            <input type="radio" class="w3-radio" name="estado" value="R">
                        <label>Reservado</label>
                        </p>
            <%  }
                //Si estado es Reservado se puede cambiar manualmente a Libre u Ocupado
                if(estado.equals("R")){ %>
                        <p style="text-align: left" >
                            <input type="radio" class="w3-radio" name="estado" value="L"  checked>
                        <label>Libre</label>
                        </p>
                        <p style="text-align: left" >
                            <input type="radio" class="w3-radio" name="estado" value="P" >
                        <label>Limpiando</label>
                        </p>
            <%  }   %>
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <!--El input vid envia el id del cliente que se va a modificar-->
                <input type="hidden" name="vid" value="<%= h.getId_habitacion()%>">
                <!--El input vaccion indica la accion a realizarse-->
                <input type="hidden" name="vaccion" value="modificarhabitacion">
                <!--Este boton lleva a la pagina anterior-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
