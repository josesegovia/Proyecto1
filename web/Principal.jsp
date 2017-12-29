<%-- 
    Document   : Principal
    Created on : 22/06/2017, 11:49:54 PM
    Author     : Jose Segovia
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
            //Se obtiene el usuario que esta logueado
            HttpSession s=request.getSession(); 
            Empleado e;
            e = (Empleado) s.getAttribute("usuario");
            //Se obtienen los permisos para Inventario
            Rol r = (Rol) request.getAttribute("isrol");
            String permisos = r.getInventario();
            //Se obtienen todos los productos que deben reponerse
            ArrayList<Inventario> productosbajos = null; 
            //Se ve si esta permitido Ver el Inventario
            if(permisos.contains("V")){
                productosbajos = Extras.Utiles.ProductosBajo();
            }
            //Se obtienen todas las Habitaciones
            ControlHabitacion ch = new ControlHabitacion();
            ArrayList<Habitaciones> habitaciones = ch.GetAll() ;
            //Se obtiene del Historial todas las habitaciones ocupada
            ControlHistorial cht = new ControlHistorial(); 
            ArrayList<Historial> historia = cht.GetAllOcupados();
            //Se obtienen todos los Clientes
            ControlCliente cc = new ControlCliente();
            ArrayList<Cliente> clientes = cc.GetAll();  
            //Se calcula cuantas habitaciones estan en cada estado
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
            <!--Aqui se muestra el estado de todas las Habitaciones-->
        <%  if(0<libres){       %>
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
            //Si hay productos que necesitan reponerse se muentran aqui
            if(productosbajos != null && !productosbajos.isEmpty()){
                for (Inventario p : productosbajos) {   %>
                    <h3 class="w3-card w3-green w3-margin-top" style="width: 50%; margin: auto">Reponer: <%= p.getNombre() %> </h3>
            <%  }
            }
            //Si hay habitaciones Ocupadas se muestran 
            //Si no hay habitaciones Ocupadas se salta
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
                        <!--Aqui se verifica que un Cliente o un Anonimo haya ocupado-->
                        <!--Dependiendo de cual sea se sabe si es una reserva op alguien ocupa el lugar--> 
                    <%  if( h.getCliente() != 0 ){      %>
                        <!--Aqui se obtiene el  cliente que ingreso a la habitacion-->
                        <%  Cliente c1 = cc.GetbyId(h.getCliente()); %>
                        <%  //Se opbtienen el Nombre y Apellido del Cliente y se crea la variable nombre para mostrarse en la tabla
                            String nombre = c1.getNombre() + " " + c1.getApellido(); %>
                    <td><%= nombre %></td>
                    <%  }else{   %>
                    <!--Si no hay Cliente se pone como Anonimo-->
                    <td>Anonimo</td>
                    <%  }   %>
                    <td><%= h.getDia_semana()%></td>
                    <td><%= h.getFecha_entrada()%></td>
                    <td><%= h.getHora_entrada() %></td>
                    <td> 
                        <%  if( h.getCliente() == 0 ){      %>
                        <!--Si el Cliente es anonimo solo esta la opcion de la salir de la Habitacion-->
                        <a href="HistorialServlet?vaccion=sal&vid=<%= h.getId_historial()%>" class="w3-bar-item w3-button w3-red w3-hover-blue">Salida</a> 
                        <%  }else{   %>
                        <!--Si el Cliente no es anonimo significa que es una Reservada-->
                        <!--Tiene la opcion de Ingresar que indica que la Habitacion acaba de ser Ocupada-->
                        <!--Tiene la opcion de tTerminar que indica que se cancelo la Reserva-->
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
            <!--Este boton es donde se puede desloguearse-->
            <a class="w3-centered w3-btn w3-blue w3-margin-top" href="LoginServlet?vaccion=logout">LogOut</a>
        </div>
    </body>
</html>
