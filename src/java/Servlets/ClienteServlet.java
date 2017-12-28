/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: ClienteServlet
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

/**
 *
 * @autor: Jose Segovia
 */
public class ClienteServlet extends HttpServlet {

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
        
        
        ControlCliente cc = new ControlCliente();
        Cliente c;
        ArrayList<Cliente> clientes;
        
        String vaccion = request.getParameter("vaccion");
        
        RequestDispatcher rd;
        //Se obtiene el atributo isrol que contiene todos los permisos del usuario
        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getCliente();
        //Aqui se verifica que permisos tiene el usuario
        if(!permisos.contains("V")){
            //Si no tiene permiso para ver la tabla cliente se redirecciona a NoPermiso.jsp
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }//Si tiene permisos para ver, continua
        
        //En esta seccion es donde se controla la vista de la tabla Clientes
        if("ver".equals(vaccion) || vaccion == null){
            clientes = cc.GetAll();
            request.setAttribute("clientes", clientes);
            rd = request.getRequestDispatcher("Clientes/Clientes.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se verifica cuando se pide un Nuevo Cliente
        if("nuevocliente".equals(vaccion)){
            //Se controla que se tenga permiso para crear Clientes
            if(!permisos.contains("C")){
                //Si no tiene permiso se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                //Si se tiene se redireccionaa NuevoCliente.jsp
                rd = request.getRequestDispatcher("Clientes/NuevoCliente.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se reciban los parametros ingresados y se realiza la insercion del nuevo cliente
        if("guardar".equals(vaccion)){
            c = new Cliente();
            String usuario = request.getParameter("nombre");
            c.setNombre(usuario);
            String apellido = request.getParameter("apellido");
            c.setApellido(apellido);
            String cedula = request.getParameter("cedula");
            c.setCedula(cedula);
            cc.Insertar(c);
            response.sendRedirect("ClienteServlet");
        }
        //En esta seccion se realiza eliminacion del cliente
        if("eliminar".equals(vaccion)){
            //Aqui se controla que se tenga permiso para eliminar Clientes
            if(!permisos.contains("E")){
                //Si no tiene permiso se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                //Si se tiene se elimina de la BD
                //Asi como tambien el Horario correspondiente al empleado
                int vid = Integer.valueOf(request.getParameter("vid"));
                
                ControlHorario ch = new ControlHorario();
                Horario h = ch.GetbyId(vid);
                ch.Eliminar(h);
                
                c = new Cliente();
                c.setId_cliente(vid);
                cc.Eliminar(c);
                response.sendRedirect("ClienteServlet");
            }
        }
        //En esta seccion se verifica cuando se pide modificar el cliente
        if("mod".equals(vaccion)){
            //Aqui se controla que se tenga permiso para Modificar Clientes
            if(!permisos.contains("M")){
                //Si no tiene permiso se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                //Si se tiene se redirecciona a ModCliente.jsp
                rd = request.getRequestDispatcher("Clientes/ModCliente.jsp");
            }
            int vid = Integer.valueOf(request.getParameter("vid"));
            c = cc.GetbyId(vid);
            request.setAttribute("cliente", c);
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se reciben los parametro que se desean cambiar y se realiza la
        //modificacion del Cliente
        if("modificarcliente".equals(vaccion)){
            c = new Cliente();
            int vid = Integer.valueOf(request.getParameter("vid"));
            c.setId_cliente(vid);
            String usuario = request.getParameter("nombre");
            c.setNombre(usuario);
            String apellido = request.getParameter("apellido");
            c.setApellido(apellido);
            String cedula = request.getParameter("cedula");
            c.setCedula(cedula);
            cc.Modificar(c);
            response.sendRedirect("ClienteServlet");
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
