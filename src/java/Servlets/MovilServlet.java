/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: MovilServlet
 * @autor: Jose Segovia
 * Año: 2017
 */
package Servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controladores.*;
import Tablas.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class MovilServlet extends HttpServlet {

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
        
        RequestDispatcher rd;
        HttpSession s=request.getSession(true);
        
        String vaccion;
        vaccion = request.getParameter("vaccion");
        
        //En esta seccion es donde se envia a la vista de Movil 
        //para que los CLientes Inicien Sesion para poder Reservar
        if("ver".equals(vaccion) || vaccion == null){
            rd = request.getRequestDispatcher("Movil/MovilIngreso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se realiza el Inicio de Sesion
        if("ingresar".equals(vaccion)){
            //Se recibe el Nº de Membrecia
            int numero_de_membrecia = Integer.parseInt(request.getParameter("numero"));
            //Se recibe el Nº de Cedula
            String cedula = request.getParameter("cedula");
            //Se obtiene la Membrecia a tra vez del Nº que se recibio
            ControlMembrecia cm = new ControlMembrecia();
            Membrecia m = cm.GetbyId(numero_de_membrecia);
            //Se Obtiene 
            ControlCliente cc = new ControlCliente();
            Cliente c = cc.GetbyId(m.getId_cliente());
            String estado = m.getEstado();
            //Se verifica que la Membrecia este Activa
            if("Activo".equals(estado)){
                if(cedula.equals(c.getCedula())){
                    //Si esta Activo se Inicia Sesion
                    //Se guarda el Cliente actual en las Sesion
                    s.setAttribute("cliente", c);
                    //Se Obtienen todas las Habitaciones Libres;
                    ControlHabitacion ch = new ControlHabitacion();
                    ArrayList<Habitaciones> habitaciones = ch.GetAllLibres();
                    request.setAttribute("habitaciones", habitaciones);
                    rd = request.getRequestDispatcher("Movil/MovilPrincipal.jsp");

                }else {
                    rd = request.getRequestDispatcher("Movil/ErrorIngreso.jsp");
                }
            }else{
                //Si esta Inactiva se envia NoActivo.jsp
                rd = request.getRequestDispatcher("Movil/NoActivo.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se realiza el cierre de Sesion
        if("logout".equals(vaccion)){
            s.invalidate();
            response.sendRedirect("MovilServlet");
        }
        //En esta seccion se verifica cuando se pide un Reservar una Habitacion
        if("reservar".equals(vaccion)){
            int id_habitacion = Integer.parseInt(request.getParameter("vid"));
            ControlHabitacion ch = new ControlHabitacion();
            Habitaciones h = ch.GetbyId(id_habitacion);
            request.setAttribute("h", h);
            rd = request.getRequestDispatcher("Movil/MovilReservar.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se realiza la Reserva de la Habitacion
        if("reservarhabitacion".equals(vaccion)){
            try {
                //Se recibe el id de la Habitacion
                int id_habitacion = Integer.parseInt(request.getParameter("vid"));
                //Se usa para obtener la Habitacion
                ControlHabitacion ch = new ControlHabitacion();
                Habitaciones h = ch.GetbyId(id_habitacion);
                
                ControlHistorial cht = new ControlHistorial();
                Historial ht = new Historial();
                //Se obtiene el los datos del Cliente actual de la Sesion
                Cliente c = (Cliente) s.getAttribute("cliente");
                //Se recibe la fecha que se Reservo
                String fecha = request.getParameter("fecha");
                //Se procesa en un formato que pueda ser usado
                int separador1 = fecha.indexOf("-");
                int separador2 = fecha.indexOf("-",separador1+1);
                String año = fecha.substring(0,separador1);
                String mes = fecha.substring(separador1+1,separador2);
                String dia = fecha.substring(separador2+1);
                fecha = dia+"/"+mes+"/"+año;
                //Se recibe la Hora que se Reservo
                String hora = request.getParameter("hora");
                //Se le agrega ":00" para ser mas facil su uso
                hora = hora+":00";
                //Se obtiene el Dia de la Semana de la reserva
                String formato = "dd/MM/yyyy";
                SimpleDateFormat df = new SimpleDateFormat(formato);
                Date fecha_ini = df.parse(fecha);
                Calendar calendario = Calendar.getInstance();
                calendario.setTime(fecha_ini);
                int dia_s = calendario.get(Calendar.DAY_OF_WEEK);
                String dia_semana = Extras.Utiles.NumeroToDiaSemana2(dia_s);
                //Se introducen todos los datos de la Reserva
                ht.setCliente(c.getId_cliente());
                ht.setId_habitacion(h.getId_habitacion());
                ht.setDia_semana(dia_semana);
                ht.setFecha_entrada(fecha);
                ht.setHora_entrada(hora);
                //Estos 2 no se completan porque se completaron al hacer la Factura, si no se cancela antes
                ht.setHora_salida("");
                ht.setFecha_salida("");
                //Se Modifica el Estado de la Habitacion a Reservado
                h.setEstado("R");
                ch.Modificar(h);
                //Se insertan los datos en el Historial
                cht.Insertar(ht);
                rd = request.getRequestDispatcher("Movil/Aceptada.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
            } catch (ParseException ex) {
                Logger.getLogger(MovilServlet.class.getName()).log(Level.SEVERE, null, ex);
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
