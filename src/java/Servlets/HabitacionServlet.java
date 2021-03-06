/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: HabitacionServlet
 * @autor: Jose Segovia
 * Año: 2017
 */
package Servlets;

import Controladores.*;
import Tablas.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @autor: Jose Segovia
 */
public class HabitacionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ControlHabitacion ch = new ControlHabitacion();
        Habitaciones h = new Habitaciones();
        ArrayList<Habitaciones> habitaciones;
        
        String vaccion = request.getParameter("vaccion");
        
        RequestDispatcher rd;
        
        //Se obtiene el atributo isrol que contiene todos los permisos del usuario
        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getHabitacion();
        //Aqui se verifica que permisos tiene el usuario
        if(!permisos.contains("V")){
            //Si no tiene permiso para ver las Habitaciones se envia a NoPermiso.jsp
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }//Si se tiene permiso continua
        
        //En esta seccion es donde se controla la vista de la tabla Habitacion
        if("ver".equals(vaccion) || vaccion == null){
            habitaciones = ch.GetAll();
            request.setAttribute("habitaciones", habitaciones);
            rd = request.getRequestDispatcher("Habitaciones/Habitaciones.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se verifica cuando se pide ingresar una Nueva Habitacion
        if("nuevo".equals(vaccion)){
            if(!permisos.contains("C")){
                //Si no se tiene se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                //Si se tiene se redireccionaa NuevaHabitacion.jsp
                rd = request.getRequestDispatcher("Habitaciones/NuevaHabitacion.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se realiza la insercion de una Nueva Habitacion
        if("guardar".equals(vaccion)){
            String tipo = request.getParameter("tipo");
            h.setTipo(tipo);
            String estado = request.getParameter("estado");
            h.setEstado(estado);
            ch.Insertar(h);
            response.sendRedirect("HabitacionServlet");
        }
        //En esta seccion se realiza la eliminacion del Empleado
        if("eliminar".equals(vaccion)){
            //Se controla que se tenga permiso para eliminar Empleados
            if(!permisos.contains("E")){
                //Si no tiene permiso se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                //Si se tiene se realiza la eliminacion
                int vid = Integer.valueOf(request.getParameter("vid"));
                h.setId_habitacion(vid);
                ch.Eliminar(h);
                response.sendRedirect("HabitacionServlet");
            }
        }
        //En esta seccion se verifica cuando se pide modificar una Habitacion
        if("mod".equals(vaccion)){
            //Se controla que se tenga permiso para modificar Habitaciones
            if(!permisos.contains("M")){
                //Si no tiene permiso se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                //Si tiene permiso se continua
                int vid = Integer.valueOf(request.getParameter("vid"));
                h = ch.GetbyId(vid);
                rd = null;
                //Se verifica el estado de la Habitacion
                String estado = h.getEstado();
                switch(estado){
                    //Si la habitacion esta en P o Limpiando se pone en Libre directamente y se llama a el Servlet para ver
                    case "P":
                        h.setEstado("L");
                        ch.Modificar(h);
                        response.sendRedirect("HabitacionServlet");
                        break;
                    //Si la habitacion esta ocupada se llama al Servlet, porque cambiar de ocupado a otros se hace con la Facturacion
                    case "O":
                        response.sendRedirect("HabitacionServlet");
                        break;
                    //Si el estado de la habitacion no esta ocupada o limpiando se redirecciona a ModificarHabitacion.jsp
                    default:
                        rd = request.getRequestDispatcher("Habitaciones/ModificarHabitacion.jsp");
                        break;
                }
            }
            request.setAttribute("habitacion", h);
            if(rd != null){
                rd.forward(request, response);
            }
        }
        ////En esta seccion se reciben el estado que se desea cambiar y se realizan la actualiza la Habitacion
        if("modificarhabitacion".equals(vaccion)){
            
            int vid = Integer.valueOf(request.getParameter("vid"));
            h = ch.GetbyId(vid);
            String estado = request.getParameter("estado");
            if(estado.equals("O")){
                ControlHistorial cht = new ControlHistorial();
                Historial ht = new Historial();
                ht.setId_habitacion(vid);
                
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String fecha = df.format(date);
                ht.setFecha_entrada(fecha);
                
                df = new SimpleDateFormat("F");
                String dia_semana = df.format(date);
                dia_semana = Extras.Utiles.NumeroToDiaSemana(dia_semana);
                ht.setDia_semana(dia_semana);
                
                df = new SimpleDateFormat("HH:mm:ss");
                String hora = df.format(date);
                ht.setHora_entrada(hora);
                
                ht.setHora_salida("");
                ht.setFecha_salida("");
                cht.Insertar(ht);
            }
            if(estado.equals("R")){
                request.setAttribute("habitacion", h);
                ControlCliente cc = new ControlCliente();
                ArrayList<Cliente> clientes = cc.GetAll();
                request.setAttribute("clientes", clientes);
                rd = request.getRequestDispatcher("Habitaciones/ReservarHabitacion.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
                
            }
            h.setEstado(estado);
            ch.Modificar(h);
            response.sendRedirect("HabitacionServlet");
        }
        //En esta seccion se realiza crea la reservacion de una habitacion
        if("reservarhabitacion".equals(vaccion)){
            
            try {
                int vid = Integer.valueOf(request.getParameter("vid"));
                int cliente = Integer.valueOf(request.getParameter("cliente"));
                //Se Procesa la fecha de reserva para poder ser ingresada
                String fecha = request.getParameter("fecha");
                int separador1 = fecha.indexOf("-");
                int separador2 = fecha.indexOf("-",separador1+1);
                String año = fecha.substring(0,separador1);
                String mes = fecha.substring(separador1+1,separador2);
                String dia = fecha.substring(separador2+1);
                fecha = dia+"/"+mes+"/"+año;
                //Se Procesa la hora de reserva para poder ser ingresada
                String hora = request.getParameter("hora");
                hora = hora+":00";
                //Se obtiene el dia de la reserva
                String formato = "dd/MM/yyyy";
                SimpleDateFormat df = new SimpleDateFormat(formato);
                Date fecha_ini = df.parse(fecha);
                Calendar calendario = Calendar.getInstance();
                calendario.setTime(fecha_ini);
                int dia_s = calendario.get(Calendar.DAY_OF_WEEK);
                String dia_semana = Extras.Utiles.NumeroToDiaSemana2(dia_s);
                //Se ingresa la habitacion
                ControlHistorial cht = new ControlHistorial();
                Historial ht = new Historial();
                ht.setId_habitacion(vid);
                //Se ingresan el dia, fecha y hora de reserva
                ht.setDia_semana(dia_semana);
                ht.setFecha_entrada(fecha);
                ht.setHora_entrada(hora);
                ht.setCliente(cliente);
                ht.setHora_salida("");
                ht.setFecha_salida("");
                //Se actualiza el estado a Reservado
                h.setEstado("R");
                ch.Modificar(h);
                //Y se inserta en la tabla Historial para su posterior uso
                cht.Insertar(ht);
                
                response.sendRedirect("HabitacionServlet");
            } catch (ParseException ex) {
                Logger.getLogger(HabitacionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
