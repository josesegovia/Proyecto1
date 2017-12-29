/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: RolServlet
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
        
        //Se obtiene el atributo isrol que contiene todos los permisos del usuario
        Rol ro = (Rol) request.getAttribute("isrol");
        //Aqui se verifica que permisos tiene el usuario
        String permiso = ro.getRoles();
        if(!permiso.contains("V")){
            //Si no tiene permiso para ver los roles se envia a NoPermiso.jsp
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }//Si se tiene permiso continua
        
        //En esta seccion es donde se controla la vista de la tabla Rol
        if("ver".equals(vaccion) || vaccion == null){
            roles = cr.GetAll();
            request.setAttribute("roles", roles);
            rd = request.getRequestDispatcher("Roles/Roles.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se verifica cuando se pide ingresar un Nuevo Producto
        if("nuevorol".equals(vaccion)){
            //Se controla que se tenga permiso para crear un Nuevo Rol
            if(!permiso.contains("C")){
                //Si no se tiene se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                //Si se tiene se redireccionaa NuevoRol.jsp
                rd = request.getRequestDispatcher("Roles/NuevoRol.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se realiza la insercion del Nuevo Rol en la BD
        if("guardar".equals(vaccion)){
            r = new Rol();
            String nombre = request.getParameter("nombre");
            r.setNombre_rol(nombre);
            cr.Insertar(r);
            response.sendRedirect("RolServlet");
        }
        //En esta seccion se realiza la eliminacion de un Rol especifico
        if("eliminar".equals(vaccion)){
            if(!permiso.contains("E")){
                //Se controla que se tenga permiso para eliminar un Rol
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    //Si no se tiene se redirecciona a NoPermiso.jsp
                    rd.forward(request, response);
                }
            }else{
                //Si se tiene permiso se elimina el Rol de la BD
                int vid = Integer.valueOf(request.getParameter("vid"));
                r = new Rol();
                r.setId_rol(vid);
                if(vid != 1){
                    cr.Eliminar(r);
                }
                response.sendRedirect("RolServlet");
            }
        }
        //En esta seccion se verifica cuando se pide modificar un Rol especifico
        if("mod".equals(vaccion)){
            //Se controla que se tenga permiso para modificar un Producto
            if(!permiso.contains("M")){
                //Si no se tiene se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                //Si se tiene permiso se continua
                int vid = Integer.valueOf(request.getParameter("vid"));
                r = cr.GetbyId(vid);
                //Se verifica que no sea el usuario Admin
                if(vid != 1){
                    //Si no es Admin  se redirecciona a ModRoles.jsp
                    rd = request.getRequestDispatcher("Roles/ModRoles.jsp");
                    request.setAttribute("rol", r);
                    if(rd != null){
                        rd.forward(request, response);
                    }
                }else{
                    //Si es Admin  se regresa a la vista de Roles
                    response.sendRedirect("RolServlet");
                }
            }
        }
        //En esta seccion se reciben los parametros que se desean cambiar y se realizan la actualizacion del Rol
        if("modificarRol".equals(vaccion)){
            //Se obtienen los parametros recibidos
            int id = Integer.valueOf(request.getParameter("vid"));
            String nombre = request.getParameter("nombre");
            //Se obtiene el rol que se quiere midificar
            r = cr.GetbyId(id);
            //Se guarda el nombre que se recibio (puede ser un nombre nuevo o el mismo)
            r.setNombre_rol(nombre);
            //Aqui se muestran todas las tablas que tienen permisos
            String[] tablas={"cliente","empleado","habitacion","inventario","roles","socios","historial","factura"};
            //Se repite esto para cada tabla que posee un permiso
            for(int i = 0;i<8;i++ ){
                //Se obtiene el permisos para Ver
                String permisos = "";
                String c1 = request.getParameter(tablas[i]+"1");
                //Si tiene permiso para Ver se inserta V
                //Si no inserta0
                if("V".equals(c1)){
                    permisos = permisos+"V";
                }else{
                    permisos = permisos+"0";
                }
                //Se obtiene el permisos para Crear
                String c2 = request.getParameter(tablas[i]+"2");
                //Si tiene permiso para Crear se inserta C
                //Si no inserta 0
                if("C".equals(c2)){
                    permisos = permisos+"C";
                }else{
                    permisos = permisos+"0";
                }
                //Se obtiene el permisos para Modificar
                String c3 = request.getParameter(tablas[i]+"3");
                //Si tiene permiso para modificar se inserta M
                //Si no inserta 0
                if("M".equals(c3)){
                    permisos = permisos+"M";
                }else{
                    permisos = permisos+"0";
                }
                //Se obtiene el permisos para Eliminar
                String c4 = request.getParameter(tablas[i]+"4");
                //Si tiene permiso para Eliminar se inserta E
                //Si no inserta 0
                if("E".equals(c4)){
                    permisos = permisos+"E";
                }else{
                    permisos = permisos+"0";
                }
                //El String permiso debe quedar como una concatenacion de tods los permisos
                //Ej: permisos = "VCME" si tiene todos los permisos
                //permisos = "0000" si no tiene ningun permiso
                //Se elige la tabla a la cual se ingresan los permisos actuales
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
            //Al terminar al For todas la tabls deberian tener sus nuevos permisos
            //Se actualiza el rol
            cr.Modificar(r);
            //Se redirecciona a la vista de los Roles
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
