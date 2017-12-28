<%-- 
    Document   : Principal
    Created on : 22/06/2017, 11:49:54 PM
    Author     : Admin
--%>

<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="Tablas.*"%>
<%@page import="Controladores.*"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Principal</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <%  
            //Poner autorefresh a 10 minutos
            response.setIntHeader("Refresh", 600);
            
            HttpSession s=request.getSession(); 
            Empleado e;
            e = (Empleado) s.getAttribute("usuario");
            
            Rol r = (Rol) request.getAttribute("isrol");
            String permisos = r.getInventario();
            ArrayList<Inventario> productosbajos = null; 
            if(permisos.contains("V")){
                productosbajos = Extras.Utiles.ProductosBajo();
            }
            boolean verificar = false;
            if(request.getAttribute("verificar") != null){
                verificar = (boolean) request.getAttribute("verificar");
            }
            
            ControlHabitacion ch = new ControlHabitacion(); 
            ArrayList<Habitaciones> habitaciones = ch.GetAll() ;    
            ControlHistorial cht = new ControlHistorial();  
            ArrayList<Historial> historia = cht.GetAllOcupados();
            ControlCliente cc = new ControlCliente();
            ArrayList<Cliente> clientes = cc.GetAll();  
            
            int libres =0;
            int ocupados =0;
            int limpiando =0;
            int reservados = 0;
            for(Habitaciones h : habitaciones){
                switch(h.getEstado()){
                    case "L":
                        libres = libres+1;
                        break;
                    case "O":
                        ocupados = ocupados+1;
                        break;
                    case "P":
                        limpiando = limpiando+1;
                        break;
                    case "R":
                        reservados = reservados+1;
                        break;
                }
            }   %>    
        <%@include file="Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
            <h1>Bienvenido <%= e.getNombre() %></h1>
        <%  if(verificar){      %>
            <h3 class="w3-card w3-red w3-margin-top" style="width: 50%; margin: auto">Membrecias expiraron </h3>
        <%  }
            if(0<libres){       %>
            <h3 class="w3-card w3-amber w3-margin-top" style="width: 50%; margin: auto">Habitaciones Libres: <%= libres %> </h3>
        <%  }
            if(0<ocupados){     %>
            <h3 class="w3-card w3-amber w3-margin-top" style="width: 50%; margin: auto">Habitaciones Ocupadas: <%= ocupados %> </h3>
        <%  }
            if(0<limpiando){    %>
            <h3 class="w3-card w3-amber w3-margin-top" style="width: 50%; margin: auto">Habitaciones Limpiandose: <%= limpiando %> </h3>
        <%  }
            if(0<reservados){   %>
            <h3 class="w3-card w3-amber w3-margin-top" style="width: 50%; margin: auto">Habitaciones Reservadas: <%= reservados %> </h3>
        <%  }
            if(permisos.contains("V")){ 
                if(productosbajos != null && !productosbajos.isEmpty()){
                    for (Inventario p : productosbajos) {   %>
                        <h3 class="w3-card w3-green w3-margin-top" style="width: 50%; margin: auto">Reponer: <%= p.getNombre() %> </h3>
                <%  }
                }
        %>
        <%  }
            if(!historia.isEmpty()){    %>
            <table class="w3-table-all w3-hoverable w3-centered w3-text-blue">
                <thead>
                    <tr class="w3-gray w3-xxlarge">
                        <th colspan="7">Habitaciones Ocupadas</th>
                    </tr>
                    <tr>
                        <th>ID</th>
                        <th>Habitacion</th>
                        <th>Cliente</th>
                        <th>Fecha</th>
                        <th>Dia Entrada</th>
                        <th>Hora Entrada</th>
                        <th></th>
                    </tr>
                </thead>
            <%  for(Historial h : historia){    %>
                <tr>
                    <td><%= h.getId_historial()%></td>
                    <td><%= h.getId_habitacion()%></td>
                    <%  if( h.getCliente() != 0 ){      %>
                        <%  for(Cliente c : clientes){ %>
                    <%          if(c.getId_cliente() == h.getCliente()){    %>
                            <%      String nombre = c.getNombre() + " " + c.getApellido(); %>
                    <td><%= nombre %></td>
                    
                            <%  }   %>
                        <%  }   %>
                    <%  }else{   %>
                    <td>Anonimo</td>
                <%  }   %>
                    <td><%= h.getDia_semana()%></td>
                    <td><%= h.getFecha_entrada()%></td>
                    <td><%= h.getHora_entrada() %></td>
                    <td> 
                        <%  if( h.getCliente() == 0 ){      %>
                        <a href="HistorialServlet?vaccion=sal&vid=<%= h.getId_historial()%>" class="w3-bar-item w3-button w3-red w3-hover-blue">Salida</a> 
                        <%  }else{   %>
                        <div class="w3-dropdown-hover">
                            <button class="w3-button w3-red w3-hover-blue">Opciones</button>
                            <div class="w3-dropdown-content w3-bar-block w3-card-4">
                                <a href="HistorialServlet?vaccion=ingresar&vid=<%= h.getId_historial()%>" class="w3-bar-item w3-button w3-red w3-hover-blue">Ingresar</a> 
                                <a href="HistorialServlet?vaccion=terminar&vid=<%= h.getId_historial()%>" class="w3-bar-item w3-button w3-red w3-hover-blue">Terminar</a> 
                            </div>
                        </div>
                        <%  }   %>
                    </td>                    
                </tr>
            <%  }   %>
            </table>
        <%  }   %>            
            <a class="w3-centered w3-btn w3-blue w3-margin-top" href="LoginServlet?vaccion=logout">LogOut</a>
        </div>
    </body>
</html>
