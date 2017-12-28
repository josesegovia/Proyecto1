/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: EmpleadoServlet
 * @autor: Jose Segovia
 * Año: 2017
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
 * @autor: Jose Segovia
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
        
        //Se obtiene el atributo isrol que contiene todos los permisos del usuario
        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getEmpleado();
        //Aqui se verifica que permisos tiene el usuario
        if(!permisos.contains("V")){
            //Si no tiene permiso para ver los Empleados se envia a NoPermiso.jsp
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }//Si se tiene permiso continua
        
        //En esta seccion es donde se controla la vista de la tabla Empleado
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
        //En esta seccion se verifica cuando se pide un Nuevo Empleado
        if("nuevoempleado".equals(vaccion)){
            //Se controla que se tenga permiso para crear Clientes
            if(!permisos.contains("C")){
                //Si no se tiene se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                //Si se tiene se redireccionaa NuevoEmpleado.jsp
                rd = request.getRequestDispatcher("Empleados/NuevoEmpleado.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se realiza la insercion de un Nuevo Empleado
        if("guardar".equals(vaccion)){
            String nombre = request.getParameter("nombre");
            e.setNombre(nombre);
            String apellido = request.getParameter("apellido");
            e.setApellido(apellido);
            String cedula = request.getParameter("cedula");
            e.setCedula(cedula);
            usuario = request.getParameter("name_user");
            e.setNombre_usuario(usuario);
            contraseña = "12345";
            e.setPassword(contraseña);
            Boolean b = ce.Insertar(e);
            response.sendRedirect("EmpleadoServlet");
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
                e = ce.GetbyId(vid);
                int rol = e.getRol();
                if(rol!= 1){
                    ce.Eliminar(e);
                }
                response.sendRedirect("EmpleadoServlet");
            }
        }
        //En esta seccion se verifica cuando se pide modificar un empleado
        if("mod".equals(vaccion)){
            //Se controla que se tenga permiso para modificar Clientes
            if(!permisos.contains("M")){
                //Si no tiene permiso se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                //Si tiene permiso se redirecciona a ModEmpleados.jsp
                rd = request.getRequestDispatcher("Empleados/ModEmpleados.jsp");
            }
            int vid = Integer.valueOf(request.getParameter("vid"));
            e = ce.GetbyId(vid);
            request.setAttribute("empleado", e);
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se reciben los parametros que se desean cambiar y se realizan la modificacion del empleado
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
        //En esta seccion se obtiene todos los roles, para elegir cual sera 
        //asignado en la pagina AgregarRol.jsp
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
        //En esta seccion se recibe el rol elegido y se asigna al Empleado
        if("emp_rol_guardar".equals(vaccion)){
            int vid = Integer.valueOf(request.getParameter("vid"));
            int rol = Integer.valueOf(request.getParameter("rol"));
            e = ce.GetbyId(vid);
            e.setRol(rol);
            ce.Modificar(e);
            response.sendRedirect("EmpleadoServlet");
            
        }
        //En esta seccion se verifica la peticion de cambio de contraseña
        if("cambiarcontraseña".equals(vaccion)){
            int vid = Integer.valueOf(request.getParameter("vid"));
            Empleado user = (Empleado) s.getAttribute("usuario");
            
            //Se controla que solo se pida cambiar la contraseña del usuario actual
            if(vid == user.getId_empleado()){
                //Se controla que el Empleado no sea el Admin
                if(vid!=1){
                    //Si el empleado no es Admin se envia a ModContraseña.jsp
                    rd = request.getRequestDispatcher("Empleados/ModContraseña.jsp");
                    e = ce.GetbyId(vid);
                    request.setAttribute("empleado", e);
                }else{
                    rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                }
            }else{
                //SI se trata de cambiar la contraseña de otro usuario se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se reciben los parametros y se realiza el cambio de contraseñas
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
