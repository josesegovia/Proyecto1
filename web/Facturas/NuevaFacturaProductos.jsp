<%-- 
    Document   : NuevaFactura
    Created on : 05/12/2017, 08:32:03 PM
    Author     : Jose Segovia
--%>

<%@page import="Tablas.*"%>
<%@page import="Controladores.*"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Facturas</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen todos los Productos para su uso-->
        <%  ArrayList<Inventario> productos = (ArrayList<Inventario>) request.getAttribute("productos");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nueva Factura</h1>
            <!--Un Form hacia FacturacionServlet para Crear el Detalle de Factura-->
            <form action="FacturacionServlet" class="w3-container" style="width: 30%; margin: auto">
                <!--Se selecciona el Tipo de Pago: Efectivo o Tarjeta-->
                <label class="w3-text-purple "><b>Tipo de Pago</b></label><br>
                <p style="text-align: none" class="w3-text-black">
                    <input type="radio" class="w3-radio" name="tipo_pago" value="E" checked="">
                <label>Efectivo</label>
                </p>
                <p style="text-align: none" class="w3-text-black">
                    <input type="radio" class="w3-radio" name="tipo_pago" value="T">
                <label>Tarjeta</label>
                </p>
                <%  
                    //Aqui se obtienen todos los productos para elegir los que fueron consumidos
                    for(Inventario i: productos){   %>
                    <label class="w3-text-purple w3-left" style="width: 80%; margin-left: 43px"><b><%= i.getNombre() %></b> 
                    <input type="number" name="<%=i.getNombre()%>" class="w3-input w3-border w3-khaki" value="0" min="0" max="100"/>
                    </label>
                <%  }   %>
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <!--El input vaccion indica la accion a realizarse-->
                <input type="hidden" name="vaccion" value="guardar1"> 
            </form>
            <br>
        </div>
    </body>
</html>
