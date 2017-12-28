<%-- 
    Document   : AgregarRol
    Created on : 30/10/2017, 03:22:49 PM
    Author     : Admin
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
        <%  Empleado e = (Empleado) request.getAttribute("empleado");    %>
        <%  Rol isRol = (Rol) request.getAttribute("isrol");    %>
        <%  ArrayList<Rol> roles = (ArrayList<Rol>) request.getAttribute("roles");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            
            <h1>Modificar Rol de Empleado</h1>
            <form action="EmpleadoServlet" class="w3-container" style="width: 60%; margin: auto">
                <label class="w3-text-purple w3-left"><b>Rol</b></label>
                <select name="rol" class="w3-input w3-border w3-khaki">
<%  for(Rol r : roles){     
        if(r.getId_rol() == 1){     
            if(isRol.getId_rol() == 1){
            }       %>
                        <!--<option value="<%= r.getId_rol() %>"><%= r.getNombre_rol() %></option>-->
<%      }else{%>
                    <option value="<%= r.getId_rol() %>"><%= r.getNombre_rol() %></option>
<%      }   %>
<%  }   %>
                </select>
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <input type="hidden" name="vid" value="<%= e.getId_empleado()%>"> 
                <input type="hidden" name="vaccion" value="emp_rol_guardar"> 
                <!--<a href="EmpleadoServlet" class="w3-btn w3-blue w3-margin-top">Cancelar</a>-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
