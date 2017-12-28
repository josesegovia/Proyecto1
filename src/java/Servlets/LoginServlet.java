/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: LoginServlet
 * @autor: Jose Segovia
 * Año: 2017
 */
package Servlets;

import Tablas.Empleado;
import Controladores.ControlEmpleado;
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
public class LoginServlet extends HttpServlet {

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
        Empleado e;
        e = new Empleado();

        String usuario = request.getParameter("nombre");
        String contraseña = request.getParameter("pass");
        String vaccion = request.getParameter("vaccion");
        
        RequestDispatcher rd;
        HttpSession s=request.getSession(true);
        //Es esta seccion se controla el ingreso del usuario
        if("ingresar".equals(vaccion)){
            //Se recibe el Nombre de Usuario y la Contraseña
            e.setNombre_usuario(usuario);
            e.setPassword(contraseña);
            //Verifica si coinciden con algun Empleado
            //Si no coincide id =0
            int id = ce.Verificar(e);
            if(id!=0){
                //Si hay uno que coincide se loguea
                e = ce.GetbyId(id);
                s.setAttribute("usuario", e);
                rd = request.getRequestDispatcher("Principal.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }
            else {
                response.sendRedirect("/Proyecto1");
            }
        }
        //En esta seccion se desconecta la sesion
        if("logout".equals(vaccion)){
            s.invalidate();
            response.sendRedirect("Login.jsp");
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
