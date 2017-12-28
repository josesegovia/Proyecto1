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
public class InventarioServlet extends HttpServlet {

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
        
        ControlInventario ci = new ControlInventario();
        Inventario i;
        ArrayList<Inventario> productos;
        
//        String usuario = request.getParameter("nombre");
//        String contraseña = request.getParameter("pass");
        String vaccion = request.getParameter("vaccion");
        
        RequestDispatcher rd;
        HttpSession s=request.getSession(true);
        
        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getInventario();
        if(!permisos.contains("V")){
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        
        if("ver".equals(vaccion) || vaccion == null){
            
            productos = ci.GetAll();
            request.setAttribute("productos", productos);
            rd = request.getRequestDispatcher("Inventario/Inventario.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("nuevoproducto".equals(vaccion)){
            if(!permisos.contains("C")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                rd = request.getRequestDispatcher("Inventario/Nuevo Producto.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("guardarproducto".equals(vaccion)){
            String nombre = request.getParameter("nombre");
            int cantidad = Integer.valueOf(request.getParameter("cantidad"));
            int minimo = Integer.valueOf(request.getParameter("minimo"));
            int precio = Integer.valueOf(request.getParameter("precio"));
            i =new Inventario();
            i.setNombre(nombre);
            i.setCantidad(cantidad);
            i.setMinimo(minimo);
            i.setPrecio(precio);
            ci.Insertar(i);
            response.sendRedirect("InventarioServlet");
        }
        if("eliminar".equals(vaccion)){
            if(!permisos.contains("E")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                int vid = Integer.valueOf(request.getParameter("vid"));
                i =new Inventario();
                i.setId_inventario(vid);
                ci.Eliminar(i);
                response.sendRedirect("InventarioServlet");
            }
        }
        if("mod".equals(vaccion)){
            if(!permisos.contains("M")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                rd = request.getRequestDispatcher("Inventario/ModificarProducto.jsp");
            }
            int vid = Integer.valueOf(request.getParameter("vid"));
            i = ci.GetbyId(vid);
            request.setAttribute("producto", i);
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("modificarproducto".equals(vaccion)){
            int vid = Integer.valueOf(request.getParameter("vid"));
            String nombre = request.getParameter("nombre");
            int cantidad = Integer.valueOf(request.getParameter("cantidad"));
            int minimo = Integer.valueOf(request.getParameter("minimo"));
            int precio = Integer.valueOf(request.getParameter("precio"));
            i =new Inventario();
            i.setId_inventario(vid);
            i.setNombre(nombre);
            i.setCantidad(cantidad);
            i.setMinimo(minimo);
            i.setPrecio(precio);
            ci.Modificar(i);
            response.sendRedirect("InventarioServlet");
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
