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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class EmpleadoServlet extends HttpServlet {

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
        
        ControlEmpleado ce = new ControlEmpleado();
        Empleado e = new Empleado();
        ArrayList<Empleado> empleados;
        
        String usuario;
        String contraseña;
        String vaccion = request.getParameter("vaccion");
        
        RequestDispatcher rd;
        HttpSession s=request.getSession(true);

        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getEmpleado();
        if(!permisos.contains("V")){
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        
        if("ver".equals(vaccion) || vaccion == null){
            empleados = ce.GetAll();
            request.setAttribute("empleados", empleados);
            ControladorRol cr = new ControladorRol();
            ArrayList<Rol> roles = cr.GetAll();
            request.setAttribute("roles", roles);
            rd = request.getRequestDispatcher("Empleados/Empleados.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("nuevoempleado".equals(vaccion)){
            if(!permisos.contains("C")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                rd = request.getRequestDispatcher("Empleados/NuevoEmpleado.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("eliminar".equals(vaccion)){
            if(!permisos.contains("E")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                int vid = Integer.valueOf(request.getParameter("vid"));
                e = ce.GetbyId(vid);
                int rol = e.getRol();
                if(rol!= 1){
                    ce.Elminar(e);
                }
                response.sendRedirect("EmpleadoServlet");
            }
        }
        if("mod".equals(vaccion)){
            if(!permisos.contains("M")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                rd = request.getRequestDispatcher("Empleados/ModEmpleados.jsp");
            }
            int vid = Integer.valueOf(request.getParameter("vid"));
            e = ce.GetbyId(vid);
            request.setAttribute("empleado", e);
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("modificarempleado".equals(vaccion)){
            int vid = Integer.valueOf(request.getParameter("vid"));
            e = ce.GetbyId(vid);
            String nombre = request.getParameter("nombre");
            e.setNombre(nombre);
            String apellido = request.getParameter("apellido");
            e.setApellido(apellido);
            String cedula = request.getParameter("cedula");
            e.setCedula(cedula);
            usuario = request.getParameter("name_user");
            e.setNombre_usuario(usuario);
            
            ce.Modificar(e);
            response.sendRedirect("EmpleadoServlet");
        }
        if("guardar".equals(vaccion)){
            String nombre = request.getParameter("nombre");
            e.setNombre(nombre);
            String apellido = request.getParameter("apellido");
            e.setApellido(apellido);
            String cedula = request.getParameter("cedula");
            e.setCedula(cedula);
            usuario = request.getParameter("name_user");
            e.setNombre_usuario(usuario);
//            contraseña = request.getParameter("pass");
            contraseña = "12345";
            e.setPassword(contraseña);
            Boolean b = ce.Insertar(e);
            response.sendRedirect("EmpleadoServlet");
        }
        if("arol".equals(vaccion)){
            int vid = Integer.valueOf(request.getParameter("vid"));
            e = ce.GetbyId(vid);
            request.setAttribute("empleado", e);
            
            ArrayList<Rol> roles;
            ControladorRol cr = new ControladorRol();
            roles = cr.GetAll();
            request.setAttribute("roles", roles);
            
            rd = request.getRequestDispatcher("Empleados/AgregarRol.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("emp_rol_guardar".equals(vaccion)){
            int vid = Integer.valueOf(request.getParameter("vid"));
            int rol = Integer.valueOf(request.getParameter("rol"));
            e = ce.GetbyId(vid);
            e.setRol(rol);
            ce.Modificar(e);
            response.sendRedirect("EmpleadoServlet");
            
        }
        if("cambiarcontraseña".equals(vaccion)){
            int vid = Integer.valueOf(request.getParameter("vid"));
            Empleado user = (Empleado) s.getAttribute("usuario");
            if(vid == user.getId_empleado()){
                if(vid!=1){
                    rd = request.getRequestDispatcher("Empleados/ModContraseña.jsp");
                    e = ce.GetbyId(vid);
                    request.setAttribute("empleado", e);
                }else{
                    rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                }
            }else{
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("passguardar".equals(vaccion)){
            int vid = Integer.valueOf(request.getParameter("vid"));
            contraseña = request.getParameter("pass");
            e = ce.GetbyId(vid);
            e.setPassword(contraseña);
            ce.Modificar(e);
            s.removeAttribute("usuario");
            s.setAttribute("usuario", e);
            response.sendRedirect("EmpleadoServlet");
            
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
