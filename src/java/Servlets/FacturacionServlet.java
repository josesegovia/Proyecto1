/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: FacturacionServlet
 * @autor: Jose Segovia
 * Año: 2017
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
 * @autor: Jose Segovia
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
        
        //Se obtiene el atributo isrol que contiene todos los permisos del usuario
        Rol r = (Rol) request.getAttribute("isrol");
        String permisos = r.getFactura();
        //Aqui se verifica que permisos tiene el usuario
        if(!permisos.contains("V")){
            //Si no tiene permiso para ver los Facturas se envia a NoPermiso.jsp
            rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }//Si se tiene permiso continua
        
        //En esta seccion es donde se controla la vista de la tabla Factura
        if("ver".equals(vaccion) || vaccion == null){
            //Se envian todas las facturas
            facturas = cf.GetAll();
            request.setAttribute("facturas", facturas);
            
            rd = request.getRequestDispatcher("Facturas/Facturas.jsp");
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se crea la factura con la desocupacion de una habitacion
        //luego se redirecciona a NuevaFacturaProductos.jsp donde se ingresan los detalles
        if("sal".equals(vaccion)){
            //Se verifica si se tiene los permisos para realizar la factura
            if(!permisos.contains("C")){
                //Si no se tiene permiso se envia a NoPermiso.jsp
                rd = request.getRequestDispatcher("Error/NoPermiso.jsp");
            }else{
                //Si se tiene permiso continua
                int vidhisto = 0;  
                //Se obtiene la id de la Historia que guarda los datos
                if(s.getAttribute("vid") != null){
                    vidhisto = (int) s.getAttribute("vid");    
                    s.removeAttribute("vid");
                }
                //Se trae el historial usando el id
                ControlHistorial cht = new ControlHistorial();
                Historial ht = cht.GetbyId(vidhisto);
                //Se ingresa la habitacion a la factura
                f.setId_habitacion(ht.getId_habitacion());
                //Se obtiene la fecha actual, la cual deberia ser la fecha de salida de la habitacion
                DateFormat df;
                df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String fecha = df.format(date);
                //Se ingresa la fecha decreacion de la factura
                f.setFecha(fecha);
                //La fecha y hora de entrada de la Historia se prepara para ser ingresada en la Factura
                fecha= ht.getHora_entrada()+" "+ht.getFecha_entrada();
                f.setHora_entrada(fecha);
                //La fecha y hora de salida de la Historia se prepara para ser ingresada en la Factura
                fecha= ht.getHora_salida()+" "+ht.getFecha_salida();
                f.setHora_salida(fecha);
                ControlCliente cc = new ControlCliente();
                //Se verifica si fue un cliente o un anonimo
                if(ht.getCliente() != 0){
                    //Si fue un cliente se recupera su Nombre y Apellido para inghresar a la Factura
                    Cliente c = cc.GetbyId(ht.getCliente());
                    String nombre = c.getNombre()+" "+c.getApellido();
                    f.setCliente(nombre);
                }else{
                    //Si fue un anonimo se ingreso como un espacio en blanco
                    f.setCliente("");
                }
                //La Factura se guarda en la session para su posterior uso
                s.setAttribute("factura", f);
                //Se recuperan todos los productos para usarse en la seleccion de productos consumidos
                ControlInventario ci = new ControlInventario();
                ArrayList<Inventario> productos;
                productos = ci.GetAll();
                request.setAttribute("productos", productos);
                
                rd = request.getRequestDispatcher("Facturas/NuevaFacturaProductos.jsp");
            }
            if(rd != null){
                rd.forward(request, response);
            }
        }
        //En esta seccion se guarda la factura con todos sus detalles
        if("guardar1".equals(vaccion)){
            
            //Se obtiene la Factura anteriormente guardada
            if(s.getAttribute("factura") != null){
                f = (Factura) s.getAttribute("factura");    
            }
            //Se obtiene el parametro tipo de pago
            String tipo_pago = request.getParameter("tipo_pago");
            f.setTipo_pago(tipo_pago);
            
            //Se obtiene el tiempo en el que la habitaciopn fue usada
            String fecha_entrada = f.getHora_entrada();
            String fecha_salida = f.getHora_salida();
            String tiempo = Utiles.DiferenciaFechas2(fecha_salida, fecha_salida);
            
            //Se obtiene el tipo de habitacion usada
            ControlHabitacion ch = new ControlHabitacion();
            Habitaciones h = ch.GetbyId(f.getId_habitacion());
            String tipo_habitacion = h.getTipo();
            
            //Se calcula la tarifa usando la fecha de entrada, el tiempo usado y el tipo de habitacion
            int tarifa = Utiles.CalcularTarifa(fecha_entrada ,tiempo ,tipo_habitacion);
            f.setTarifa(tarifa);
            ControlInventario ci = new ControlInventario();
            //Se obtiene todos los productos
            ArrayList<Inventario> productos = ci.GetAll();
            //Se calcula el total de los productos consumidos y se suma a la tarifa
            //Y se crea una lista de los productos consumidos para su uso posterior
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
            //Se ingresa el Total a la Factura
            f.setTotal(total);
            //La factura se ingresa a la BD
            cf.Insertar(f);
            //Se remueve la variable de la session s
            s.removeAttribute("factura");
            //Se obtiene la Factura que se acaba de Crear
            f = cf.GetLast();
            //Se crea la Factura Detalle para cada producto consumido
            for(Inventario x: productosconsumidos){
                fd = new FacturaDetalle();
                fd.setId_factura(f.getId_factura());
                fd.setProducto(x.getNombre());
                fd.setCantidad(x.getCantidad());
                fd.setPrecio(x.getPrecio());
                //Se obtiene el producto que se va a ingresar al Detalles
                //y se extrae la cantidad consumida del inventario
                Inventario i = ci.GetbyId(x.getId_inventario());
                int cant_total = i.getCantidad();
                cant_total = cant_total - x.getCantidad();
                i.setCantidad(cant_total);
                ci.Modificar(i);
                //Se ingresa a la BD el Detalle de la Factura
                cfd.Insertar(fd);
                
            }
            
            response.sendRedirect("FacturacionServlet");
        }
        
        //En esta seccion es donde se controla la vista de la tabla Factura Detalle
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
