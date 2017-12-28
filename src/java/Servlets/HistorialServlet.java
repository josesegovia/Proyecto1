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
        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getHistorial();
        if(!permisos.contains("V")){
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }

        if("ver".equals(vaccion) || vaccion == null){
            
            historia = cht.GetAll();
            request.setAttribute("historia", historia);
            
            ControlCliente cc = new ControlCliente();
            ArrayList<Cliente> clientes = cc.GetAll();
            request.setAttribute("clientes", clientes);
            rd = request.getRequestDispatcher("Historial/Historia.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("sal".equals(vaccion)){

            Habitaciones h;
            ControlHabitacion ch = new ControlHabitacion();
            int vid = Integer.valueOf(request.getParameter("vid"));
            ht = cht.GetbyId(vid);
            h = ch.GetbyId(ht.getId_habitacion());
            h.setEstado("P");
            ch.Modificar(h);
            DateFormat df;
            df = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String hora = df.format(date);
            ht.setHora_salida(hora);
            df = new SimpleDateFormat("dd/MM/yyyy");
            String fecha_salida = df.format(date);
            ht.setFecha_salida(fecha_salida);
            cht.Modificar(ht);
            s.setAttribute("vid", vid);
            rd = request.getRequestDispatcher("FacturacionServlet");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("ingresar".equals(vaccion)){
            Habitaciones h;
            ControlHabitacion ch = new ControlHabitacion();
            int vid = Integer.valueOf(request.getParameter("vid"));
            ht = cht.GetbyId(vid);
            h = ch.GetbyId(ht.getId_habitacion());
            h.setEstado("P");
            ch.Modificar(h);
            DateFormat df;
            df = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String hora = df.format(date);
            ht.setHora_salida(hora);
            df = new SimpleDateFormat("dd/MM/yyyy");
            String fecha_salida = df.format(date);
            ht.setFecha_salida(fecha_salida);
            cht.Modificar(ht);
            s.setAttribute("vid", vid);
            response.sendRedirect("FacturacionServlet?vaccion=sal");
            
        }
        if("terminar".equals(vaccion)){
            Habitaciones h;
            ControlHabitacion ch = new ControlHabitacion();
            int vid = Integer.valueOf(request.getParameter("vid"));
            ht = cht.GetbyId(vid);
            h = ch.GetbyId(ht.getId_habitacion());
            h.setEstado("L");
            ch.Modificar(h);
            cht.Elminar(ht);
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
