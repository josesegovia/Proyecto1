<%-- 
    Document   : ModificarHabitacion
    Created on : 15/09/2017, 04:04:29 PM
    Author     : Admin
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
    <%  Habitaciones h = (Habitaciones) request.getAttribute("habitacion");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <%    String estado = h.getEstado();
              String est = estado;
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
                }
        
        %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nueva Habitacion</h1>
            <form action="HabitacionServlet" class="w3-container" style="width: 30%; margin: auto">
                <label class="w3-text-amber w3-xlarge w3-left"><b>Estado es: <span class="w3-text-aqua"><%= est %> </span> </b></label>
                <br>
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
                if(estado.equals("O")){   %>
                    <br>
            <%  }
                if(estado.equals("P")){ %>
                        <p style="text-align: left" >
                            <input type="radio" class="w3-radio" name="estado" value="L"  checked>
                        <label>Libre</label>
                        </p>
            <%  }
                if(estado.equals("R")){ %>
                        <p style="text-align: left" >
                            <input type="radio" class="w3-radio" name="estado" value="L"  checked>
                        <label>Libre</label>
                        </p>
                        <p style="text-align: left" >
                            <input type="radio" class="w3-radio" name="estado" value="P" >
                        <label>Limpiando</label>
                        </p>
            <%  }   
                if(!estado.equals("O")){   %>
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
            <%  }   %>
                <input type="hidden" name="vaccion" value="modificarhabitacion">
                <input type="hidden" name="vid" value="<%= h.getId_habitacion()%>">
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
