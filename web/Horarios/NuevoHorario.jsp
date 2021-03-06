<%-- 
    Document   : NuevoHorario
    Created on : 20/12/2017, 10:11:03 PM
    Author     : Jose Segovia
--%>

<%@page import="Tablas.Empleado"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Horarios</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <!--Se obtienen los Empleados que no tienen Horario para crearles uno Nuevo-->
        <%  ArrayList<Empleado> empleados = (ArrayList<Empleado>) request.getAttribute("empleados");    %>
        <%@include file="/Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Nuevo Horario</h1>
            <!--Un Form hacia HorariosServletpara crear el nuevo Horario-->
            <form action="HorariosServlet" class="w3-container" style="width: 20%; margin: auto">
                <!--Se pide que escoga el Empleado al que se le pondra este Horario-->
                <label class="w3-text-purple w3-left"><b>Empleado</b></label>
                <select name="empleado" class="w3-input w3-border w3-khaki w3-center">
                    <% for(Empleado e : empleados){ %>
                         <%  String nombre = e.getNombre() + " " + e.getApellido(); %>
                            <option value="<%= e.getId_empleado()%>" selected> <%= nombre %> </option>
                <%  }   %>
                </select>
                <!--Se seleccionan los Horarios para cada dia-->
                <!--Lunes-->
                <label class="w3-text-purple w3-left"><b>Lunes</b></label>
                <select name="Lunes" class="w3-input w3-border w3-khaki w3-center">
                    <option value="Libre" selected>Libre</option>
                    <option value="07:00 a 14:00" >07:00 a 14:00</option>
                    <option value="14:00 a 21:00" >14:00 a 21:00</option>
                    <option value="21:00 a 07:00" >21:00 a 07:00</option>
                </select>
                <!--Martes-->
                <label class="w3-text-purple w3-left"><b>Martes</b></label>
                <select name="Martes" class="w3-input w3-border w3-khaki w3-center">
                    <option value="Libre" selected>Libre</option>
                    <option value="07:00 a 14:00" >07:00 a 14:00</option>
                    <option value="14:00 a 21:00" >14:00 a 21:00</option>
                    <option value="21:00 a 07:00" >21:00 a 07:00</option>
                </select>
                <!--Miercoles-->
                <label class="w3-text-purple w3-left"><b>Miercoles</b></label>
                <select name="Miercoles" class="w3-input w3-border w3-khaki w3-center">
                    <option value="Libre" selected>Libre</option>
                    <option value="07:00 a 14:00" >07:00 a 14:00</option>
                    <option value="14:00 a 21:00" >14:00 a 21:00</option>
                    <option value="21:00 a 07:00" >21:00 a 07:00</option>
                </select>
                <!--Jueves-->
                <label class="w3-text-purple w3-left"><b>Jueves</b></label>
                <select name="Jueves" class="w3-input w3-border w3-khaki w3-center">
                    <option value="Libre" selected>Libre</option>
                    <option value="07:00 a 14:00" >07:00 a 14:00</option>
                    <option value="14:00 a 21:00" >14:00 a 21:00</option>
                    <option value="21:00 a 07:00" >21:00 a 07:00</option>
                </select>
                <!--Viernes-->
                <label class="w3-text-purple w3-left"><b>Viernes</b></label>
                <select name="Viernes" class="w3-input w3-border w3-khaki w3-center">
                    <option value="Libre" selected>Libre</option>
                    <option value="07:00 a 14:00" >07:00 a 14:00</option>
                    <option value="14:00 a 21:00" >14:00 a 21:00</option>
                    <option value="21:00 a 07:00" >21:00 a 07:00</option>
                </select>
                <!--Sabado-->
                <label class="w3-text-purple w3-left"><b>Sabado</b></label>
                <select name="Sabado" class="w3-input w3-border w3-khaki w3-center">
                    <option value="Libre" selected>Libre</option>
                    <option value="07:00 a 14:00" >07:00 a 14:00</option>
                    <option value="14:00 a 21:00" >14:00 a 21:00</option>
                    <option value="21:00 a 07:00" >21:00 a 07:00</option>
                </select>
                <!--Domingo-->
                <label class="w3-text-purple w3-left"><b>Domingo</b></label>
                <select name="Domingo" class="w3-input w3-border w3-khaki w3-center">
                    <option value="Libre" selected>Libre</option>
                    <option value="07:00 a 19:00" >07:00 a 19:00</option>
                    <option value="19:00 a 07:00" >19:00 a 07:00</option>
                </select>
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Guardar" /> 
                <!--El input vaccion indica la accion a realizarse-->
                <input type="hidden" name="vaccion" value="guardar"> 
                <!--Se regresa a la pagina anterior-->
                <a href="javascript: window.history.go(-1)" class="w3-btn w3-blue w3-margin-top">Cancelar</a>
            </form>
            <br>
        </div>
    </body>
</html>
