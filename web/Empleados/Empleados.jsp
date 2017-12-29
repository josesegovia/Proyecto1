<%-- 
    Document   : Empleados
    Created on : 23/06/2017, 09:19:20 PM
    Author     : Jose Segovia
--%>

<%@page import="Controladores.ControladorRol"%>
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
        <!--Se obtienen los empleados para su uso-->
        <%  ArrayList<Empleado> empleados = (ArrayList<Empleado>) request.getAttribute("empleados");    %>
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
            <%  //Se obtienen todos los empleados
                ControladorRol cr = new ControladorRol();
                for(Empleado e : empleados){
                    //Se obtiene el Rol del empleado que se va a mostrar para saber el nombre de su Rol
                    Rol r = cr.GetbyId(e.getRol()); %>    
                <tr>
                    <td> <%= e.getNombre() %> </td>
                    <td> <%= e.getApellido() %> </td>
                    <td> <%= e.getCedula() %> </td>
                    <td> <%= r.getNombre_rol() %> </td>
                    <td> <%= e.getNombre_usuario() %> </td>
                    <td> <%= e.getPassword() %> </td>
                    <td>
                        <!--Un dropdown con todas las opciones que se pueden realizar a los empleados-->
                        <div class="w3-dropdown-hover">
                            <button class="w3-button w3-red w3-hover-blue">Opciones</button>
                            <div class="w3-dropdown-content w3-bar-block w3-card-4">
                                <a href="EmpleadoServlet?vaccion=mod&vid=<%= e.getId_empleado()%>" class="w3-bar-item w3-button w3-red w3-hover-blue" >Modificar</a>
                                <!--Este boton pide modificar la contraseña del empleado-->
                                <a href="EmpleadoServlet?vaccion=cambiarcontraseña&vid=<%= e.getId_empleado()%>" class="w3-bar-item w3-button w3-red w3-hover-blue" >Cambiar Contraseña</a>
                                <!--Este boton pide asignar el rol del empleado-->
                                <a href="EmpleadoServlet?vaccion=arol&vid=<%= e.getId_empleado()%>" class="w3-bar-item w3-button w3-red w3-hover-blue" >Asignar Rol</a>
                                <!--Este boton pide elñiminar el empleado-->
                                <a href="EmpleadoServlet?vaccion=eliminar&vid=<%= e.getId_empleado()%>" class="w3-bar-item w3-button w3-red w3-hover-blue" >Eliminar</a>
                            </div>
                        </div>
                    </td>
                </tr>
            <%  }   %>
                <tr>
                    <!--Este boton lleva a la Creacion de un Nuevo Empleado-->
                    <td colspan="7"> <a href="EmpleadoServlet?vaccion=nuevoempleado" class="w3-button w3-circle w3-teal w3-hover-green">+</a> </td>
                </tr>
            </table>
        </div>
    </body>
</html>
