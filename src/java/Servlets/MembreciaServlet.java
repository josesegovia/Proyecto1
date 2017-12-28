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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MembreciaServlet extends HttpServlet {

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
        
        
        ControlMembrecia cm = new ControlMembrecia();
        Membrecia m = new Membrecia();
        ArrayList<Membrecia> socios;
        
        String vaccion = request.getParameter("vaccion");
        
        RequestDispatcher rd;
        HttpSession s=request.getSession(true);
        
        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getSocios();
        if(!permisos.contains("V")){
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        
        if("ver".equals(vaccion) || vaccion == null){
            socios = cm.GetAll();
            ControlSociosPagos cs = new ControlSociosPagos();
            ArrayList<SociosPagos> pagos;
            
            for(Membrecia mx : socios){
                String estado = mx.getEstado();
                if("Activo".equals(estado)){
                    int id = mx.getId_cliente();
                    pagos = cs.GetAllBySocio(id);
                    if(!pagos.isEmpty()){
                        int tamaño = pagos.size();
                        SociosPagos s1 = pagos.get(tamaño-1);
                        String fecha_pago = s1.getFecha();
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        String fecha_actual = df.format(date);
                        int dias = 0;
                        try {
                            dias = Extras.Utiles.DiferenciaFechasenDias(fecha_pago , fecha_actual);
                        } catch (ParseException ex) {
                            Logger.getLogger(MembreciaServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(30<dias){
                            id = s1.getId_socio();
                            Membrecia m1 = cm.GetbyId(id);
                            m1.setEstado("Inactivo");
                            cm.Modificar(m1);
                        }
                    }
                    
                }
            }
            
            socios = cm.GetAll();
            request.setAttribute("socios", socios);
            rd = request.getRequestDispatcher("Membrecias/Socios.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("nuevo".equals(vaccion)){
            if(!permisos.contains("C")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                ControlCliente cc = new ControlCliente();
                ArrayList<Cliente> clientes = cc.GetAll();
                request.setAttribute("clientes", clientes);
                rd = request.getRequestDispatcher("Membrecias/NuevoSocio.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("guardar".equals(vaccion)){
            int cliente = Integer.valueOf(request.getParameter("cliente"));
            socios = cm.GetAll();
            boolean existe = false;
            for(Membrecia mx : socios){
                int id = mx.getId_cliente();
                if(id==cliente){
                    existe=true;
                }
            }
            if(existe){
                ControlCliente cc = new ControlCliente();
                ArrayList<Cliente> clientes = cc.GetAll();
                request.setAttribute("clientes", clientes);
                request.setAttribute("existe", existe);
                rd = request.getRequestDispatcher("Membrecias/NuevoSocio.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                m.setId_cliente(cliente);
                m.setEstado("Inactivo");
                cm.Insertar(m);
                response.sendRedirect("MembreciaServlet");
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
                m.setId_membrecia(vid);
                cm.Elminar(m);
                response.sendRedirect("MembreciaServlet");
            }
        }
        if("pagos".equals(vaccion)){
            ControlSociosPagos cs = new ControlSociosPagos();
            ArrayList<SociosPagos> pagos;
            
            socios = cm.GetAll();
            
            for(Membrecia mx : socios){
                String estado = mx.getEstado();
                if("Activo".equals(estado)){
                    int id = mx.getId_cliente();
                    pagos = cs.GetAllBySocio(id);
                    if(!pagos.isEmpty()){
                        int tamaño = pagos.size();
                        SociosPagos s1 = pagos.get(tamaño-1);
                        String fecha_pago = s1.getFecha();
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        String fecha_actual = df.format(date);
                        int dias = 0;
                        try {
                            dias = Extras.Utiles.DiferenciaFechasenDias(fecha_pago , fecha_actual);
                        } catch (ParseException ex) {
                            Logger.getLogger(MembreciaServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(30<dias){
                            id = s1.getId_socio();
                            Membrecia m1 = cm.GetbyId(id);
                            m1.setEstado("Inactivo");
                            cm.Modificar(m1);
                        }
                    }
                    
                }
            }
            
            pagos = cs.GetAll();
            request.setAttribute("pagos", pagos);
            
            rd = request.getRequestDispatcher("Membrecias/Pagos.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("nuevopago".equals(vaccion)){
            ControlCliente cc = new ControlCliente();
            ArrayList<Cliente> clientes_sin_paga = new ArrayList<>();
            
            socios = cm.GetAll();
            
            for(Membrecia mx : socios){
                
                String estado = mx.getEstado();
                if(!"Activo".equals(estado)){
                    Cliente c = cc.GetbyId(mx.getId_cliente());
                    clientes_sin_paga.add(c);
                }
                
            }
                
            request.setAttribute("clientes", clientes_sin_paga);
            rd = request.getRequestDispatcher("Membrecias/NuevoPago.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("pagoguardar".equals(vaccion)){
            if(!permisos.contains("M")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                SociosPagos sp = new SociosPagos();
                ControlSociosPagos cs = new ControlSociosPagos();

    //            String estado = request.getParameter("estado");
                String estado = "Pagado";
                sp.setEstado(estado);
                int socio = Integer.parseInt(request.getParameter("socio"));
                m = cm.GetbyIdSocio(socio);
                m.setEstado("Activo");
                cm.Modificar(m);
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String fecha = df.format(date);
                sp.setId_socio(socio);
                sp.setFecha(fecha);
                boolean ok = false;
                ok = cs.Insertar(sp);
                System.out.println(ok);
                response.sendRedirect("MembreciaServlet?vaccion=pagos");
            }
            
        }
        if("eliminarpago".equals(vaccion)){
            if(!permisos.contains("E")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                SociosPagos sp = new SociosPagos();
                ControlSociosPagos cs = new ControlSociosPagos();
                int vid = Integer.valueOf(request.getParameter("vid"));
                sp.setId_pagos(vid);
                cs.Elminar(sp);
                response.sendRedirect("MembreciaServlet?vaccion=pagos");
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
