<%-- 
    Document   : Facturas
    Created on : 05/12/2017, 06:56:44 PM
    Author     : Jose Segovia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Tablas.Factura"%>
<%@page import="Tablas.FacturaDetalle"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Facturas</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen los detalles de la Factura solicitada-->
        <%  ArrayList<FacturaDetalle> detalles = (ArrayList<FacturaDetalle>) request.getAttribute("detalles");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                    <tr class="w3-gray w3-xxlarge">
                        <th colspan="6" class="cabecera">Detalles de Facturas</th>
                    </tr>
                    <tr>
                        <th>ID Factura</th>
                        <th>ID Detalle</th>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Precio</th>
                        <th>Precio total</th>
                    </tr>
                </thead>
    <%          for(FacturaDetalle f : detalles){          %>    
                <tr>
                    <td> <%= f.getId_factura() %> </td>
                    <td> <%= f.getId_detalle()%> </td>
                    <td> <%= f.getProducto()%> </td>
                    <td> <%= f.getCantidad()%> </td>
                    <td> <%= f.getPrecio()%> </td>
                    <td> <%= f.getPrecio()*f.getCantidad() %> </td>
                </tr>
    <%          }       %>
            </table>
            <a href="FacturacionServlet" class="w3-btn w3-blue w3-margin-top">Volver</a>
        </div>
    </body>
</html>
