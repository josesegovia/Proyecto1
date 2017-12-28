/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: HistorialServlet
 * @autor: Jose Segovia
 * Año: 2017
 */
package Servlets;

import Controladores.*;
import Tablas.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class HistorialServlet extends HttpServlet {

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
        
        String vaccion = request.getParameter("vaccion");
        
        RequestDispatcher rd;
        HttpSession s=request.getSession(true);
        
        Historial ht;
        ControlHistorial cht = new ControlHistorial();
        ArrayList<Historial> historia;
        //Se obtiene el atributo isrol que contiene todos los permisos del usuario
        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getHistorial();
        //Aqui se verifica que permisos tiene el usuario
        if(!permisos.contains("V")){
            //Si no tiene permiso para ver las Habitaciones se envia a NoPermiso.jsp
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }//Si se tiene permiso continua

        //En esta seccion es donde se controla la vista de la tabla Hsitorial
        if("ver".equals(vaccion) || vaccion == null){
            //Se envian todas las historias de la BD
            historia = cht.GetAll();
            request.setAttribute("historia", historia);
            //Se envian todos los Cliente para usarse en la vista en Historia.jsp
            ControlCliente cc = new ControlCliente();
            ArrayList<Cliente> clientes = cc.GetAll();
            request.setAttribute("clientes", clientes);
            rd = request.getRequestDispatcher("Historial/Historia.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se ejecuta cuando una Habitacion es desocupada por un Anonimo, completa el historial y envia a la Facturacion para completarse
        if("sal".equals(vaccion)){
            Habitaciones h;
            ControlHabitacion ch = new ControlHabitacion();
            //Se obtiene la Historia que esta trabajandose
            int vid = Integer.valueOf(request.getParameter("vid"));
            ht = cht.GetbyId(vid);
            //Se obtiene la Habitacion desocupada
            h = ch.GetbyId(ht.getId_habitacion());
            //Se cambia el estado a Limpiando
            h.setEstado("P");
            //Se obtiene la fecha y hora de salida
            DateFormat df;
            df = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String hora = df.format(date);
            ht.setHora_salida(hora);
            df = new SimpleDateFormat("dd/MM/yyyy");
            String fecha_salida = df.format(date);
            ht.setFecha_salida(fecha_salida);
            //Se actualiza la Historia
            ch.Modificar(h);
            //Se actualiza la Habitacion
            cht.Modificar(ht);
            //Se envia la id de la historia para ayudar con la Facturacion
            s.setAttribute("vid", vid);
            rd = request.getRequestDispatcher("FacturacionServlet");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //Esta seccion se ejecuta cuando una Habitacion Reservada es ocupada
        if("ingresar".equals(vaccion)){
            Habitaciones h;
            ControlHabitacion ch = new ControlHabitacion();
            //Se obtiene la Historia que esta trabajandose
            int vid = Integer.valueOf(request.getParameter("vid"));
            ht = cht.GetbyId(vid);
            //Se obtiene la Habitacion
            h = ch.GetbyId(ht.getId_habitacion());
            //Se cambia el estado a Limpiando
            h.setEstado("P");
            //Se obtiene la fecha y hora de salida
            DateFormat df;
            df = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String hora = df.format(date);
            ht.setHora_salida(hora);
            df = new SimpleDateFormat("dd/MM/yyyy");
            String fecha_salida = df.format(date);
            ht.setFecha_salida(fecha_salida);
            //Se actualiza la Habitacion
            ch.Modificar(h);
            //Se envia la id de la historia para ayudar con la Facturacion
            cht.Modificar(ht);
            s.setAttribute("vid", vid);
            response.sendRedirect("FacturacionServlet?vaccion=sal");
            
        }
        //Esta seccion se ejecuta cuando una Habitacion Reservada es cancelada
        if("terminar".equals(vaccion)){
            Habitaciones h;
            ControlHabitacion ch = new ControlHabitacion();
            //Se obtiene la Historia que esta trabajandose
            int vid = Integer.valueOf(request.getParameter("vid"));
            ht = cht.GetbyId(vid);
            //Se obtiene la Habitacion
            h = ch.GetbyId(ht.getId_habitacion());
            //Se cambia la Habitacion a Libre
            h.setEstado("L");
            //Se actualiza la Habitacion
            ch.Modificar(h);
            //Se elimiona el Historial
            cht.Eliminar(ht);
            rd = request.getRequestDispatcher("Principal.jsp");
            if (rd != null) {
                rd.forward(request, response);
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
