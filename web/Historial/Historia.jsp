<%-- 
    Document   : Historial
    Created on : 08/11/2017, 02:28:29 PM
    Author     : Jose Segovia
--%>

<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Tablas.*"%>
<%@page import="Extras.Utiles"%>
<%@page import="Controladores.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen todas las historias para su uso-->
        <%  ArrayList<Historial> historia = (ArrayList<Historial>) request.getAttribute("historia");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                    <tr class="w3-gray w3-xxlarge">
                        <th colspan="9" class="cabecera">Historial</th>
                    </tr>
                    <tr>
                        <th>ID</th>
                        <th>Habitacion</th>
                        <th>Cliente</th>
                        <th>Fecha</th>
                        <th>Dia Entrada</th>
                        <th>Hora Entrada</th>
                        <th>Dia Salida</th>
                        <th>Hora Salida</th>
                        <th>Tiempo Ocupado</th>
                    </tr>
                </thead>                
            <%  for(Historial h : historia){
                    String f_entada = h.getHora_entrada()+" "+ h.getFecha_entrada();
                    String f_salida = h.getHora_salida()+" "+ h.getFecha_salida();
                    if(!" ".equals(f_salida)){  %>
                <tr>
                    <td><%= h.getId_historial()%></td>
                    <td><%= h.getId_habitacion()%></td>
                    <%  ControlCliente cc = new ControlCliente();
                    Cliente c1 = cc.GetbyId(h.getCliente());
                    String nombre = c1.getNombre() + " " + c1.getApellido(); %>
                    <td><%= nombre %></td>
                    <td><%= h.getDia_semana()%></td>
                    <td><%= h.getFecha_entrada()%></td>
                    <td><%= h.getHora_entrada() %></td>
                    <td><%= h.getFecha_salida()%></td>
                    <td><%= h.getHora_salida()%></td>
                        <td><%= Utiles.DiferenciaFechas(f_entada, f_salida) %></td>
                </tr>
<%                    
                    }
                }
%>
            </table>
        </div>
    </body>
</html>
