/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controladores.ControlCliente;
import Tablas.Cliente;
import Tablas.Rol;
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
        HttpSession s=request.getSession(true);
        
        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getCliente();
        if(!permisos.contains("V")){
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        
        if("ver".equals(vaccion) || vaccion == null){
            clientes = cc.GetAll();
            request.setAttribute("clientes", clientes);
            rd = request.getRequestDispatcher("Clientes/Clientes.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("nuevocliente".equals(vaccion)){
            if(!permisos.contains("C")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                rd = request.getRequestDispatcher("Clientes/NuevoCliente.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
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
        if("eliminar".equals(vaccion)){
            if(!permisos.contains("E")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                int vid = Integer.valueOf(request.getParameter("vid"));
                c = new Cliente();
                c.setId_cliente(vid);;
                cc.Elminar(c);
                response.sendRedirect("ClienteServlet");
            }
        }
        if("mod".equals(vaccion)){
            if(!permisos.contains("M")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                rd = request.getRequestDispatcher("Clientes/ModCliente.jsp");
            }
            int vid = Integer.valueOf(request.getParameter("vid"));
            c = cc.GetbyId(vid);
            request.setAttribute("cliente", c);
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("modificarcliente".equals(vaccion)){
            c = new Cliente();
            int vid = Integer.valueOf(request.getParameter("vid"));
            c.setId_cliente(vid);;
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
