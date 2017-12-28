/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
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
        HttpSession s=request.getSession(true);
        
        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getHabitacion();
        if(!permisos.contains("V")){
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        
        if("ver".equals(vaccion) || vaccion == null){
            habitaciones = ch.GetAll();
            request.setAttribute("habitaciones", habitaciones);
            rd = request.getRequestDispatcher("Habitaciones/Habitaciones.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("nuevo".equals(vaccion)){
            if(!permisos.contains("C")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                rd = request.getRequestDispatcher("Habitaciones/NuevaHabitacion.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("guardar".equals(vaccion)){
            String tipo = request.getParameter("tipo");
            h.setTipo(tipo);
            String estado = request.getParameter("estado");
            h.setEstado(estado);
            ch.Insertar(h);
            response.sendRedirect("HabitacionServlet");
        }
        if("eliminar".equals(vaccion)){
            if(!permisos.contains("E")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                int vid = Integer.valueOf(request.getParameter("vid"));
                h.setId_habitacion(vid);
                ch.Elminar(h);
                response.sendRedirect("HabitacionServlet");
            }
        }
        if("mod".equals(vaccion)){
            if(!permisos.contains("M")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                int vid = Integer.valueOf(request.getParameter("vid"));
                h = ch.GetbyId(vid);
                rd = null;
                String estado = h.getEstado();
                switch(estado){
                    case "P":
                        h.setEstado("L");
                        ch.Modificar(h);
                        response.sendRedirect("HabitacionServlet");
                        break;
//                    case "R":
//                        h.setEstado("L");
//                        ch.Modificar(h);
//                        response.sendRedirect("HabitacionServlet");
//                        break;
                    case "O":
                        response.sendRedirect("HabitacionServlet");
                        break;
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
        if("modificarhabitacion".equals(vaccion)){
            int vid = Integer.valueOf(request.getParameter("vid"));
            h = ch.GetbyId(vid);
//            String tipo = request.getParameter("tipo");
//            h.setTipo(tipo);
            String estado = request.getParameter("estado");
            if(estado.equals("LA")){
                ch.Liberar();
            }
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
        if("reservarhabitacion".equals(vaccion)){
            
            try {
                int vid = Integer.valueOf(request.getParameter("vid"));
                int cliente = Integer.valueOf(request.getParameter("cliente"));
                
                String fecha = request.getParameter("fecha");
                int separador1 = fecha.indexOf("-");
                int separador2 = fecha.indexOf("-",separador1+1);
                String año = fecha.substring(0,separador1);
                String mes = fecha.substring(separador1+1,separador2);
                String dia = fecha.substring(separador2+1);
                fecha = dia+"/"+mes+"/"+año;
                String hora1 = request.getParameter("hora");
                hora1 = hora1+":00";
                
                String formato = "dd/MM/yyyy";
                SimpleDateFormat df = new SimpleDateFormat(formato);
                Date fecha_ini = df.parse(fecha);
                Calendar calendario = Calendar.getInstance();
                calendario.setTime(fecha_ini);
                int dia_s = calendario.get(Calendar.DAY_OF_WEEK);
                String dia_semana = Extras.Utiles.NumeroToDiaSemana2(dia_s);
                
                
                
                ControlHistorial cht = new ControlHistorial();
                Historial ht = new Historial();
                ht.setId_habitacion(vid);
                
                ht.setDia_semana(dia_semana);
                ht.setFecha_entrada(fecha);
                
                
                
                ht.setHora_entrada(hora1);
                
                ht.setCliente(cliente);
                ht.setHora_salida("");
                ht.setFecha_salida("");
                
                h.setEstado("R");
                ch.Modificar(h);
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
