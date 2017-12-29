<%-- 
    Document   : Facturas
    Created on : 05/12/2017, 06:56:44 PM
    Author     : Jose Segovia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Tablas.*"%>
<%@page import="Tablas.FacturaDetalle"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Facturas</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen todas las Facturas para su uso-->
        <%  ArrayList<Factura> facturas = (ArrayList<Factura>) request.getAttribute("facturas");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                    <tr class="w3-gray w3-xxlarge">
                        <th colspan="10" class="cabecera">Facturas</th>
                    </tr>
                    <tr>
                        <th>ID Factura</th>
                        <th>Habitacion</th>
                        <th>Fecha</th>
                        <th>Total</th>
                        <th>Hora Entrada</th>
                        <th>Hora Salida</th>
                        <th>Tarifa</th>
                        <th>Cliente</th>
                        <th>Tipo de Pago</th>
                        <th></th>
                    </tr>
                </thead>
    <%          for(Factura f : facturas){          %>    
                <tr>
                    <td> <%= f.getId_factura() %> </td>
                    <td> <%= f.getId_habitacion()%> </td>
                    <td> <%= f.getFecha()%> </td>
                    <td> <%= f.getTotal()%> </td>
                    <td> <%= f.getHora_entrada()%> </td>
                    <td> <%= f.getHora_salida()%> </td>
                    <td> <%= f.getTarifa()%> </td>
                    <td> <%= f.getCliente()%> </td>
                    <td> <%= f.getTipo_pago()%> </td>
                    <td> 
                        <div class="w3-dropdown-hover">
                            <button class="w3-button w3-red w3-hover-blue">Opciones</button>
                            <div class="w3-dropdown-content w3-bar-block w3-card-4">
                                <!--Este boton te envia a ver todos los Detalles de la factura-->
                                <a href="FacturacionServlet?vaccion=verdetalle&vid=<%= f.getId_factura()%>" class="w3-bar-item w3-button w3-red w3-hover-blue">Ver Detalles</a>
                            </div>
                        </div>
                    </td>
                </tr>
    <%          }       %>
            </table>
        </div>
    </body>
</html>
