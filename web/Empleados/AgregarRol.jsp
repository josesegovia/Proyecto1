<%-- 
    Document   : AgregarRol
    Created on : 30/10/2017, 03:22:49 PM
    Author     : Jose Segovia
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Tablas.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Rol</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtiene el empleado al que se le asignara el Rol-->
        <%  Empleado e = (Empleado) request.getAttribute("empleado");    %>
        <!--Se obtiene el rol del usuario actual-->
        <%  Rol isRol = (Rol) request.getAttribute("isrol");    %>
        <!--Se obtienen todos los roles existentes-->
        <%  ArrayList<Rol> roles = (ArrayList<Rol>) request.getAttribute("roles");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            
            <h1>Modificar Rol de Empleado</h1>
            <form action="EmpleadoServlet" class="w3-container" style="width: 60%; margin: auto">
                <label class="w3-text-purple w3-left"><b>Rol</b></label>
                <select name="rol" class="w3-input w3-border w3-khaki">
            <%  for(Rol r : roles){     
                    //El Rol 1 es ADMIN
                    //Se verifica que no se muestre el Rol ADMIN como opcion a asignar excepto en ciertos casos
                    if(r.getId_rol() == 1){
                        //Solo si el Usuario es ADMIN puede asignar otro ADMIN
                        if(isRol.getId_rol() == 1){ %>
                            <option value="<%= r.getId_rol() %>"><%= r.getNombre_rol() %></option>
                    <%  }   %>
                <%  }else{%>
                <!--Esta parte muestra todos los Roles menos ADMIN-->
                    <option value="<%= r.getId_rol() %>"><%= r.getNombre_rol() %></option>
                <%  }   %>
            <%  }   %>
                </select>
                <!--El id de Rol elegido se envia para asignarse-->
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <!--El input vid envia el id del empleado al que se le asigna el rol-->
                <input type="hidden" name="vid" value="<%= e.getId_empleado()%>"> 
                <!--El input vaccion indica la accion a realizarse-->
                <input type="hidden" name="vaccion" value="emp_rol_guardar"> 
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
