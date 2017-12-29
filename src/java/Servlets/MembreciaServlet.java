/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: MembreciaServlet
 * @autor: Jose Segovia
 * Año: 2017
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
        ArrayList<Membrecia> socios;
        
        String vaccion = request.getParameter("vaccion");
        
        RequestDispatcher rd;
        //Se obtiene el atributo isrol que contiene todos los permisos del usuario
        Rol r = (Rol) request.getAttribute("isrol");
        //Aqui se verifica que permisos tiene el usuario
        String permisos = r.getSocios();
        if(!permisos.contains("V")){
            //Si no tiene permiso para ver las Membrecias se envia a NoPermiso.jsp
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }//Si se tiene permiso continua
        
        //En esta seccion es donde se controla la vista de la tabla Membrecia
        if("ver".equals(vaccion) || vaccion == null){
            //Se obtienen todos los Socios
            socios = cm.GetAll();
            //Se obtienen todos los pagos realizados
            ControlSociosPagos cs = new ControlSociosPagos();
            ArrayList<SociosPagos> pagos;
            
            for(Membrecia mx : socios){
                String estado = mx.getEstado();
                //Si el Socio tiene una Membrecia activa verifica que no se haya vencido
                if("Activo".equals(estado)){
                    int id = mx.getId_cliente();
                    //Se obtienen todos los pagos realizados por el socio
                    pagos = cs.GetAllBySocio(id);
                    if(!pagos.isEmpty()){
                        //Se obtiene la fecha del ultimo pago
                        int tamaño = pagos.size();
                        SociosPagos s1 = pagos.get(tamaño-1);
                        String fecha_pago = s1.getFecha();
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        String fecha_actual = df.format(date);
                        int dias = 0;
                        //Se obtiene la cant. de dias pasados desde el ultimo pago
                        try {
                            dias = Extras.Utiles.DiferenciaFechasenDias(fecha_pago , fecha_actual);
                        } catch (ParseException ex) {
                            Logger.getLogger(MembreciaServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //Si la cant. de dias pasados es mayor que 30 se desacctiva la Membrecia
                        if(30<dias){
                            id = s1.getId_socio();
                            Membrecia m1 = cm.GetbyId(id);
                            m1.setEstado("Inactivo");
                            cm.Modificar(m1);
                        }
                    }
                    
                }
            }
            //Se envian todas Membrecias de los Socios para verse en pantalla
            request.setAttribute("socios", socios);
            rd = request.getRequestDispatcher("Membrecias/Socios.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se verifica cuando se pide ingresar una Nueva Membrecia
        if("nuevo".equals(vaccion)){
            //Se controla que se tenga permiso para crear una Nueva Membrecia
            if(!permisos.contains("C")){
                //Si no se tiene se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                //Si se tiene se redireccionaa NuevoSocio.jsp
                ControlCliente cc = new ControlCliente();
                ArrayList<Cliente> clientes = cc.GetAll();
                //Se elimina de clientes todos los Clientes con Membrecia
                socios = cm.GetAll();
                for(Membrecia m1 : socios){
                    int id_cliente = m1.getId_cliente();
                    Cliente c = cc.GetbyId(id_cliente);
                    clientes.remove(c);
                }
                
                request.setAttribute("clientes", clientes);
                rd = request.getRequestDispatcher("Membrecias/NuevoSocio.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se realiza la insercion de la Nueva Membrecia en la BD
        if("guardar".equals(vaccion)){
            Membrecia m = new Membrecia();
            int cliente = Integer.valueOf(request.getParameter("cliente"));
            //Se obtienen todos los Socios
            socios = cm.GetAll();
            //Si no existe se crea una Nueva Membrecia
            //Se inserta el cliente y se pone en estado Inactivo
            m.setId_cliente(cliente);
            m.setEstado("Inactivo");
            //Se inserta en la BD
            cm.Insertar(m);
            response.sendRedirect("MembreciaServlet");
        }
        //En esta seccion se realiza la eliminacion de una Membrecia especifica
        if("eliminar".equals(vaccion)){
            //Se controla que se tenga permiso para eliminar una Membrecia especifica
            if(!permisos.contains("E")){
                //Si no se tiene se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                //Si se tiene la Membrecia se elimina de la BD
                int vid = Integer.valueOf(request.getParameter("vid"));
                Membrecia m = new Membrecia();
                m.setId_membrecia(vid);
                cm.Eliminar(m);
                response.sendRedirect("MembreciaServlet");
            }
        }
        //En esta seccion es donde se controla la viste de la tabla PagosSocios
        if("pagos".equals(vaccion)){
            ControlSociosPagos cs = new ControlSociosPagos();
            ArrayList<SociosPagos> pagos;
            //Se obtienen todos los Socios
            socios = cm.GetAll();
            
            for(Membrecia mx : socios){
                String estado = mx.getEstado();
                //Si el Socio tiene una Membrecia activa verifica que no se haya vencido
                if("Activo".equals(estado)){
                    int id = mx.getId_cliente();
                    //Se obtienen todos los pagos realizados por el socio
                    pagos = cs.GetAllBySocio(id);
                    if(!pagos.isEmpty()){
                        //Se obtiene la fecha del ultimo pago
                        int tamaño = pagos.size();
                        SociosPagos s1 = pagos.get(tamaño-1);
                        String fecha_pago = s1.getFecha();
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        String fecha_actual = df.format(date);
                        int dias = 0;
                        //Se obtiene la cant. de dias pasados desde el ultimo pago
                        try {
                            dias = Extras.Utiles.DiferenciaFechasenDias(fecha_pago , fecha_actual);
                        } catch (ParseException ex) {
                            Logger.getLogger(MembreciaServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //Si la cant. de dias pasados es mayor que 30 se desacctiva la Membrecia
                        if(30<dias){
                            id = s1.getId_socio();
                            Membrecia m = cm.GetbyId(id);
                            m.setEstado("Inactivo");
                            cm.Modificar(m);
                        }
                    }
                    
                }
            }
            //Se envian todos los Pagos de Membrecias de los Socios para verse en pantalla
            pagos = cs.GetAll();
            request.setAttribute("pagos", pagos);
            
            rd = request.getRequestDispatcher("Membrecias/Pagos.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se verifica cuando se pide ingresar una Nueva Membrecia
        if("nuevopago".equals(vaccion)){
            //Se controla que se tenga permiso para crear un Nuevo pago
            if(!permisos.contains("M")){
                //Si no tiene permiso para crear un Nuevo Pago se envia a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                //Si se tiene permisos se continua
                ControlCliente cc = new ControlCliente();
                ArrayList<Cliente> clientes_sin_paga = new ArrayList<>();
                //Se obtiene todos los Socios
                socios = cm.GetAll();

                for(Membrecia mx : socios){
                    String estado = mx.getEstado();
                    //Se obtiene todos los socios que no estan activos
                    //Solo pueden pagar los usuarios inactivos
                    if(!"Activo".equals(estado)){
                        Cliente c = cc.GetbyId(mx.getId_cliente());
                        clientes_sin_paga.add(c);
                    }

                }
                //Se envian los usuarios que les falta pagar
                request.setAttribute("clientes", clientes_sin_paga);
                rd = request.getRequestDispatcher("Membrecias/NuevoPago.jsp");
                
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se realiza la insercion del Nuevo Pago en la BD
        if("pagoguardar".equals(vaccion)){
            
            SociosPagos sp = new SociosPagos();
            ControlSociosPagos cs = new ControlSociosPagos();
            int socio = Integer.parseInt(request.getParameter("socio"));
            //Se obtiene el Socio y se actualiza su estado a Activo
            Membrecia m = new Membrecia();
            m = cm.GetbyIdSocio(socio);
            m.setEstado("Activo");
            cm.Modificar(m);
            //Se obtiene y guarda la fecha de Pago
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String fecha = df.format(date);
            String estado = "Pagado";
            //Se guardan todos los parametros
            sp.setEstado(estado);
            sp.setId_socio(socio);
            sp.setFecha(fecha);
            //Se inserta en la BD
            cs.Insertar(sp);
            response.sendRedirect("MembreciaServlet?vaccion=pagos");
        }
        //En esta seccion se realiza la eliminacion de un Pago de Socio especifico
        if("eliminarpago".equals(vaccion)){
            //Se controla que se tenga permiso para eliminar un Pago
            if(!permisos.contains("E")){
                //Si no se tiene se redirecciona a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                //Si se tiene el Pago se elimina de la BD
                SociosPagos sp = new SociosPagos();
                ControlSociosPagos cs = new ControlSociosPagos();
                int vid = Integer.valueOf(request.getParameter("vid"));
                sp.setId_pagos(vid);
                cs.Eliminar(sp);
                //Se llama a la vista de los Pagos
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
