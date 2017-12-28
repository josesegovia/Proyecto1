/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controladores.*;
import Tablas.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class HorariosServlet extends HttpServlet {

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
        
        ControlHorario ch = new ControlHorario();
        Horario h;
        ArrayList<Horario> horarios;
        
        String vaccion = request.getParameter("vaccion");
        
        RequestDispatcher rd;
        
        if("ver".equals(vaccion) || vaccion == null){
            horarios = ch.GetAll();
            request.setAttribute("horarios", horarios);
            ControlEmpleado ce = new ControlEmpleado();
            ArrayList<Empleado> empleados = ce.GetAll();
            request.setAttribute("empleados", empleados);
            rd = request.getRequestDispatcher("Horarios/Horarios.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("nuevo".equals(vaccion)){
            ControlEmpleado ce = new ControlEmpleado();
            ArrayList<Empleado> empleados = ce.GetAll();
            request.setAttribute("empleados", empleados);
            rd = request.getRequestDispatcher("Horarios/NuevoHorario.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("guardar".equals(vaccion)){
            h = new Horario();
            
            int empleado = Integer.parseInt(request.getParameter("empleado"));
            h.setId_empleado(empleado);
            String lunes = request.getParameter("Lunes");
            h.setLunes(lunes);
            String martes = request.getParameter("Martes");
            h.setMartes(martes);
            String miercoles = request.getParameter("Miercoles");
            h.setMiercoles(miercoles);
            String jueves = request.getParameter("Jueves");
            h.setJueves(jueves);
            String viernes = request.getParameter("Viernes");
            h.setViernes(viernes);
            String sabado = request.getParameter("Sabado");
            h.setSabado(sabado);
            String domingo = request.getParameter("Domingo");
            h.setDomingo(domingo);
            ch.Insertar(h);
            response.sendRedirect("HorariosServlet");
        }
        if("mod".equals(vaccion)){
            int vid = Integer.valueOf(request.getParameter("vid"));
            h = ch.GetbyId(vid);
            request.setAttribute("hora", h);
            rd = request.getRequestDispatcher("Horarios/ModificarHora.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("modificar".equals(vaccion)){
            h = new Horario();
            int vid = Integer.parseInt(request.getParameter("vid"));
            h.setId_empleado(vid);
            String lunes = request.getParameter("Lunes");
            h.setLunes(lunes);
            String martes = request.getParameter("Martes");
            h.setMartes(martes);
            String miercoles = request.getParameter("Miercoles");
            h.setMiercoles(miercoles);
            String jueves = request.getParameter("Jueves");
            h.setJueves(jueves);
            String viernes = request.getParameter("Viernes");
            h.setViernes(viernes);
            String sabado = request.getParameter("Sabado");
            h.setSabado(sabado);
            String domingo = request.getParameter("Domingo");
            h.setDomingo(domingo);
            ch.Modificar(h);
            response.sendRedirect("HorariosServlet");
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
