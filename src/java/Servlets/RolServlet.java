/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: 
 * @autor: Jose Segovia
 * Año: 2017
 */
package Servlets;

import Controladores.*;
import Tablas.*;
import java.util.ArrayList;
import java.io.IOException;
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
public class RolServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("empty-statement")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ControladorRol cr = new ControladorRol();
        Rol r;
        ArrayList<Rol> roles;
        
        String vaccion = request.getParameter("vaccion");
        
        RequestDispatcher rd;
        HttpSession s=request.getSession(true);
        
        Rol ro = (Rol) request.getAttribute("isrol");
        String permiso = ro.getRoles();
        if(!permiso.contains("V")){
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        
        if("ver".equals(vaccion) || vaccion == null){
            roles = cr.GetAll();
            request.setAttribute("roles", roles);
            rd = request.getRequestDispatcher("Roles/Roles.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("nuevorol".equals(vaccion)){
            if(!permiso.contains("C")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                rd = request.getRequestDispatcher("Roles/NuevoRol.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("guardar".equals(vaccion)){
            r = new Rol();
            String nombre = request.getParameter("nombre");
            r.setNombre_rol(nombre);
            cr.Insertar(r);
            response.sendRedirect("RolServlet");
            
        }
        if("eliminar".equals(vaccion)){
            if(!permiso.contains("E")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                int vid = Integer.valueOf(request.getParameter("vid"));
                r = new Rol();
                r.setId_rol(vid);
                if(vid != 1){
                    cr.Eliminar(r);
                }
                response.sendRedirect("RolServlet");
            }
        }
        if("mod".equals(vaccion)){
            if(!permiso.contains("M")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                int vid = Integer.valueOf(request.getParameter("vid"));
                r = cr.GetbyId(vid);
                if(vid != 1){
                    rd = request.getRequestDispatcher("Roles/ModRoles.jsp");
                    request.setAttribute("rol", r);
                    if(rd != null){
                        rd.forward(request, response);
                    }
                }else{
                    response.sendRedirect("RolServlet");
                }
            }
        }
        if("modificarRol".equals(vaccion)){
            int vid = Integer.valueOf(request.getParameter("vid"));
            String nombre = request.getParameter("nombre");
            r = cr.GetbyId(vid);
            r.setNombre_rol(nombre);
            String[] tablas={"cliente","empleado","habitacion","inventario","roles","socios","historial","factura"};
            for(int i = 0;i<8;i++ ){
                String permisos = "";
                String c1 = request.getParameter(tablas[i]+"1");
                if("V".equals(c1)){
                    permisos = permisos+"V";
                }else{
                    permisos = permisos+"0";
                }
                String c2 = request.getParameter(tablas[i]+"2");
                if("C".equals(c2)){
                    permisos = permisos+"C";
                }else{
                    permisos = permisos+"0";
                }
                String c3 = request.getParameter(tablas[i]+"3");
                if("M".equals(c3)){
                    permisos = permisos+"M";
                }else{
                    permisos = permisos+"0";
                }
                String c4 = request.getParameter(tablas[i]+"4");
                if("E".equals(c4)){
                    permisos = permisos+"E";
                }else{
                    permisos = permisos+"0";
                }
                switch(i){
                    case 0:
                        r.setCliente(permisos);
                        break;
                    case 1:
                        r.setEmpleado(permisos);
                        break;
                    case 2:
                        r.setHabitacion(permisos);
                        break;
                    case 3:
                        r.setInventario(permisos);
                        break;
                    case 4:
                        r.setRoles(permisos);
                        break;
                    case 5:
                        r.setSocios(permisos);
                        break;
                    case 6:
                        r.setHistorial(permisos);
                        break;
                    case 7:
                        r.setFactura(permisos);
                        break;
                }
            }
            
            cr.Modificar(r);
            response.sendRedirect("RolServlet");
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
