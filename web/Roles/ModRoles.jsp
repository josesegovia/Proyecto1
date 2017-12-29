<%-- 
    Document   : ModRoles
    Created on : 13/09/2017, 08:40:12 PM
    Author     : Jose Segovia
--%>

<%@page import="Tablas.Rol"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Roles</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtiene el Rol que se va a Modificar-->
        <%  Rol r = (Rol) request.getAttribute("rol");    %>
        <!--Se obtiene el Array de String tablas para ayudar con la Modificacion del Rol
        Este posee los nombres de todas las Tablas que tienen permisos-->
        <%! String[] tablas={"cliente","empleado","habitacion","inventario","roles","socios","historial","factura"}; %>
            <%@include file="/Navegacionjsp.jspf" %>
            <div class="w3-container w3-center w3-blue-gray body-height">
                <h1>Asignar permisos a Rol</h1>
                <form action="RolServlet" class="w3-container" style="margin: auto">
                    <label class="w3-text-purple w3-left"><b>Nombre</b></label>
                    <input type="text" name="nombre" class="w3-input w3-border w3-khaki" value="<%= r.getNombre_rol() %>" required autofocus>
                    <br>
                    <div class="w3-container" style="width: 55%;margin-left: auto; margin-right: auto">
                        
                    <%  //El for se repito 8 veces, uno por cada elemento en tablas
                        for(int i=0;i<8;i++){
                            //Se obtiene el nombre de la Tabla cuyo permiso se modificara
                            String c1 = tablas[i];  %>
                        <div id="demo<%= i+1 %>" class="w3-container w3-card w3-orange" style="float: left; margin: 5px;"> 
                            <!--Se debe seleccionar cada checkbox que representa una accion para tener permiso a esta-->
                            <p style="margin: 0">
                                <%= c1.toUpperCase() %>
                            </p>
                            <!--Permiso Ver-->
                            <input type="checkbox" class="w3-radio" name=<%= c1 %>1 value="V" checked=""/>
                            <label>Ver</label>
                            <!--Permiso Crear-->
                            <input type="checkbox" class="w3-radio" name=<%= c1 %>2 value="C" />
                            <label>Crear</label>
                            <!--Permiso Modificar-->
                            <input type="checkbox" class="w3-radio" name=<%= c1 %>3 value="M" />
                            <label>Modificar</label>
                            <!--Permiso Eliminar-->
                            <input type="checkbox" class="w3-radio" name=<%= c1 %>4 value="E" />
                            <label>Eliminar</label>
                        </div>
                    <%  }  %>
                    </div>
                    <div class="w3-container" style="margin-right: auto; margin-left: auto ">
                        <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                        <!--El input vid envia el id del Rol que se va a modificar-->
                        <input type="hidden" name="vid" value="<%= r.getId_rol()%>"> 
                        <!--El input vaccion indica la accion a realizarse-->
                        <input type="hidden" name="vaccion" value="modificarRol"> 
                        <!--Se regresa a la pagina anterior-->
                        <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>

                    </div>
                </form>
            </div>
    </body>
</html>
