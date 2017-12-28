/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controladores.*;
import Extras.Utiles;
import Tablas.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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
public class FacturacionServlet extends HttpServlet {

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
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        
        Factura f = new Factura();
        ControlFactura cf = new ControlFactura();
        FacturaDetalle fd;
        ControlFacturaDetalle cfd = new ControlFacturaDetalle();
        ArrayList<Factura> facturas;
        ArrayList<FacturaDetalle> detalles;
        
        String vaccion = request.getParameter("vaccion");
        
        RequestDispatcher rd;
        HttpSession s=request.getSession();
        
        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getFactura();
        if(!permisos.contains("V")){
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        
        if("ver".equals(vaccion) || vaccion == null){
            
            facturas = cf.GetAll();
            request.setAttribute("facturas", facturas);
            
            detalles = cfd.GetAll();
            request.setAttribute("detalles", detalles);
            
            ControlCliente cc = new ControlCliente();
            ArrayList<Cliente> clientes = cc.GetAll();
            request.setAttribute("clientes", clientes);
            
            rd = request.getRequestDispatcher("Facturas/Facturas.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        
        if("sal".equals(vaccion)){
            
            if(!permisos.contains("C")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                int vidhisto = 0;  
                if(s.getAttribute("vid") != null){
                    vidhisto = (int) s.getAttribute("vid");    
                    s.removeAttribute("vid");
                }
                ControlHistorial cht = new ControlHistorial();
                Historial ht = cht.GetbyId(vidhisto);
                f.setId_habitacion(ht.getId_habitacion());
                DateFormat df;
                df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String fecha = df.format(date);
                f.setFecha(fecha);
                fecha= ht.getHora_entrada()+" "+ht.getFecha_entrada();
                f.setHora_entrada(fecha);
                fecha= ht.getHora_salida()+" "+ht.getFecha_salida();
                f.setHora_salida(fecha);
                
                ControlCliente cc = new ControlCliente();
                if(ht.getCliente() != 0){
                    Cliente c = cc.GetbyId(ht.getCliente());
                    String nombre = c.getNombre()+" "+c.getApellido();
                    f.setCliente(nombre);
                }
                f.setCliente("");
                s.setAttribute("factura", f);
                
                ControlInventario ci = new ControlInventario();
                ArrayList<Inventario> productos;
                productos = ci.GetAll();
                request.setAttribute("productos", productos);
                
                ArrayList<Cliente> clientes = cc.GetAll();
                request.setAttribute("clientes", clientes);
                
                rd = request.getRequestDispatcher("Facturas/NuevaFacturaProductos.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        if("guardar1".equals(vaccion)){
            
            if(s.getAttribute("factura") != null){
                f = (Factura) s.getAttribute("factura");    
            }
            String tipo_pago = request.getParameter("tipo_pago");
            f.setTipo_pago(tipo_pago);
            
            
            
            String fecha_entrada = f.getHora_entrada();
            String fecha_salida = f.getHora_salida();
            String tiempo = Utiles.DiferenciaFechas2(fecha_salida, fecha_salida);
            ControlHabitacion ch = new ControlHabitacion();
            Habitaciones h = ch.GetbyId(f.getId_habitacion());
            String tipo_habitacion = h.getTipo();
            int tarifa = Utiles.CalcularTarifa(fecha_entrada ,tiempo ,tipo_habitacion);
            f.setTarifa(tarifa);
            
            ControlInventario ci = new ControlInventario();
            ArrayList<Inventario> productos = ci.GetAll();
            ArrayList<Inventario> productosconsumidos = new ArrayList<>();
            int total = tarifa;
            for(Inventario x: productos){
                String nombre = x.getNombre();
                int cant = Integer.parseInt(request.getParameter(nombre));
                if( cant >0 ){
                    int precio = x.getPrecio();
                    total= total+(precio*cant);
                    x.setCantidad(cant);
                    productosconsumidos.add(x);
                }
            }
            f.setTotal(total);
            cf.Insertar(f);
            s.removeAttribute("factura");
            f = cf.GetLast();
            for(Inventario x: productosconsumidos){
                fd = new FacturaDetalle();
                fd.setId_factura(f.getId_factura());
                fd.setProducto(x.getNombre());
                fd.setCantidad(x.getCantidad());
                fd.setPrecio(x.getPrecio());
                Inventario i = ci.GetbyId(x.getId_inventario());
                int cant_total = i.getCantidad();
                cant_total = cant_total - x.getCantidad();
                i.setCantidad(cant_total);
                ci.Modificar(i);
                cfd.Insertar(fd);
                
            }
            
            response.sendRedirect("FacturacionServlet");
        }
        if("eliminar".equals(vaccion)){
            
            if(!permisos.contains("E")){
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
                if(rd != null){
                    rd.forward(request, response);
                }
            }else{
                int vid = Integer.valueOf(request.getParameter("vid"));
                f = new Factura();
                f.setId_factura(vid);;
                cf.Elminar(f);
                response.sendRedirect("FacturacionServlet");
            }
        }
        if("verdetalle".equals(vaccion)){

            int vid = Integer.valueOf(request.getParameter("vid"));
            rd = request.getRequestDispatcher("Facturas/FacturasDetalle.jsp");
            detalles = cfd.GetAllById(vid);
            request.setAttribute("detalles", detalles);
            if(rd != null){
                rd.forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(FacturacionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(FacturacionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
