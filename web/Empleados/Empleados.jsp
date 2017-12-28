<%-- 
    Document   : Empleados
    Created on : 23/06/2017, 09:19:20 PM
    Author     : Admin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Tablas.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabla de Empleados</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%  ArrayList<Empleado> empleados = (ArrayList<Empleado>) request.getAttribute("empleados");    %>
        <%  ArrayList<Rol> roles = (ArrayList<Rol>) request.getAttribute("roles");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                    <tr class="w3-gray w3-xxlarge">
                        <th colspan="7">Empleados</th>
                    </tr>
                <tr>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Cedula</th>
                    <th>Rol</th>
                    <th>Nombre Usuario</th>
                    <th>Contraseña</th>
                    <th></th>
                </tr>
                </thead>
        <%      for(Empleado e : empleados){
                    for(Rol r : roles){
                        String nombre_rol = r.getNombre_rol();
                        int id_rol = e.getRol();
                        if(id_rol == r.getId_rol()){
        %>    
                <tr>
                    <td> <%= e.getNombre() %> </td>
                    <td> <%= e.getApellido() %> </td>
                    <td> <%= e.getCedula() %> </td>
                    <td> <%= nombre_rol %> </td>
                    <td> <%= e.getNombre_usuario() %> </td>
                    <td> <%= e.getPassword() %> </td>
                    <td>
                        <div class="w3-dropdown-hover">
                            <button class="w3-button w3-red w3-hover-blue">Opciones</button>
                            <div class="w3-dropdown-content w3-bar-block w3-card-4">
                                <a href="EmpleadoServlet?vaccion=mod&vid=<%= e.getId_empleado()%>" class="w3-bar-item w3-button w3-red w3-hover-blue" >Modificar</a>
                                <a href="EmpleadoServlet?vaccion=cambiarcontraseña&vid=<%= e.getId_empleado()%>" class="w3-bar-item w3-button w3-red w3-hover-blue" >Cambiar Contraseña</a>
                                <a href="EmpleadoServlet?vaccion=arol&vid=<%= e.getId_empleado()%>" class="w3-bar-item w3-button w3-red w3-hover-blue" >Asignar Rol</a>
                                <a href="EmpleadoServlet?vaccion=eliminar&vid=<%= e.getId_empleado()%>" class="w3-bar-item w3-button w3-red w3-hover-blue" >Eliminar</a>
                            </div>
                        </div>
                    </td>
                </tr>
        <%                    
                        }
                    }
                }
        %>
                <tr>
                    <td colspan="7"> <a href="EmpleadoServlet?vaccion=nuevoempleado" class="w3-button w3-circle w3-teal w3-hover-green">+</a> </td>
                </tr>
            </table>
        </div>
    </body>
</html>
