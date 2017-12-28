<%-- 
    Document   : ModificarHabitacionEstado
    Created on : 09/11/2017, 05:03:07 PM
    Author     : Admin
--%>

<%@page import="Tablas.Historial"%>
<%@page import="Tablas.Habitaciones"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar Estado de Habitacion</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
    <%  Habitaciones h = (Habitaciones) request.getAttribute("habitacion");    %>
    <%  Historial ht = (Historial) request.getAttribute("historial");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nueva Habitacion</h1>
            <form action="HistorialServlet" class="w3-container" style="width: 30%; margin: auto">
                <label class="w3-text-amber w3-xlarge w3-left"><b>Estado</b></label>
                <br>
                <%
                  String estado = h.getEstado();
                %>                
                <p style="text-align: left" >
                <%
                    if(estado.equals("L")){ %>
                    <input type="radio" class="w3-radio" name="estado" value="L" checked>
                <%
                    }else{  %>
                    <input type="radio" class="w3-radio" name="estado" value="L">
                <%
                    }
                %>
                    <label>Libre</label>
                </p>
                <p style="text-align: left" >
                <%
                    if(estado.equals("O")){ %>
                    <input type="radio" class="w3-radio" name="estado" value="O" checked>
                <%
                    }else{  %>
                    <input type="radio" class="w3-radio" name="estado" value="O">
                <%
                    }
                %>
                    <label>Ocupado</label>
                </p>
                <p>
                <p style="text-align: left" >
                <%
                    if(estado.equals("P")){ %>
                    <input type="radio" class="w3-radio" name="estado" value="P" checked>
                <%
                    }else{  %>
                    <input type="radio" class="w3-radio" name="estado" value="P">
                <%
                    }
                %>
                    <label>Limpiando</label>
                <p style="text-align: left" >
                <%
                    if(estado.equals("R")){ %>
                    <input type="radio" class="w3-radio" name="estado" value="R" checked>
                <%
                    }else{  %>
                    <input type="radio" class="w3-radio" name="estado" value="R">
                <%
                    }
                %>
                    <label>Reservado</label>
                </p>
                
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <input type="hidden" name="vaccion" value="modifi">
                <input type="hidden" name="vid_habitacion" value="<%= h.getId_habitacion()%>">
                <input type="hidden" name="vid_historial" value="<%= ht.getId_historial()%>">
                <!--<a href="HabitacionServlet" class="w3-btn w3-blue w3-margin-top">Cancelar</a>-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
