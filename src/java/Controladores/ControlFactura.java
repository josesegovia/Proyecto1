/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: ControlFactura
 * @autor: Jose Segovia
 * Año: 2017
 */
package Controladores;

import Tablas.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @autor: Jose Segovia
 */
public class ControlFactura {
    
    /**
     * Esta clase recibe una clase Factura para insertarla en la BD
     * @param f Factura
     * @return Boolean
     */
    public Boolean Insertar(Factura f){
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into factura values(default,?,?,?,?,?,?,?,?)");
            p.setInt(1, f.getId_habitacion());
            p.setString(2, f.getFecha());
            p.setInt(3, f.getTotal());
            p.setString(4, f.getHora_entrada());
            p.setString(5, f.getHora_salida());
            p.setInt(6, f.getTarifa());
            p.setString(7, f.getCliente());
            p.setString(8, f.getTipo_pago());
            
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    /**
     * Esta clase recibe una clase Factura que usa para eliminarla de la BD
     * @param f Factura
     * @return Boolean
     */
    public Boolean Eliminar(Factura f){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from factura where id_factura=?");
            p.setInt( 1, f.getId_factura());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    /**
     * Esta clase recibe una clase Factura que usa para modificarla en la BD
     * @param f Factura
     * @return Boolean
     */
    public Boolean Modificar(Factura f){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update factura set fecha=?, total=?, hora_entrada=?, hora_salida=?, tarifa=?, cliente=?, tipo_pago=? where id_factura=?");
            p.setString( 1, f.getFecha() );
            p.setInt(2, f.getTotal() );
            p.setString( 3, f.getHora_entrada() );
            p.setString( 4, f.getHora_salida() );
            p.setInt(5, f.getTarifa());
            p.setString(6, f.getCliente() );
            p.setString(7, f.getTipo_pago());
            p.setInt(8, f.getId_factura());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }

    /**
     * Esta clase  recibe un entero que usa para devolver una factura especifica
     * @param id int
     * @return Factura
     */
    public Factura GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        Factura f = new Factura();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from factura where id_factura=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                f.setId_factura(id);
                f.setId_habitacion( rs.getInt(2) );
                f.setFecha( rs.getString(3) );
                f.setTotal( rs.getInt(4) );
                f.setHora_entrada( rs.getString(5) );
                f.setHora_salida( rs.getString(6) );
                f.setTarifa( rs.getInt(7) );
                f.setCliente( rs.getString(8) );
                f.setTipo_pago( rs.getString(9) );
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            f = null;
            return f;
        }
        return f;
    }
    
    /**
     * Esta clase regresa la ultima factura realizada de la BD
     * @return Factura
     */
    public Factura GetLast(){
        
        Connection con;
        PreparedStatement p;
        Factura f = new Factura();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from factura where id_factura=(select MAX(id_factura) from factura)");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                f.setId_factura( rs.getInt(1) );
                f.setId_habitacion( rs.getInt(2) );
                f.setFecha( rs.getString(3) );
                f.setTotal( rs.getInt(4) );
                f.setHora_entrada( rs.getString(5) );
                f.setHora_salida( rs.getString(6) );
                f.setTarifa( rs.getInt(7) );
                f.setCliente( rs.getString(8) );
                f.setTipo_pago( rs.getString(9) );
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            f = null;
            return f;
        }
        return f;
    }
    
    /**
     * Esta clase regresa todas las facturas de la BD
     * @return ArrayList de Factura
     */
    public ArrayList<Factura> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Factura> facturas = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from factura order by id_factura");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Factura f = new Factura();
                f.setId_factura( rs.getInt(1) );
                f.setId_habitacion( rs.getInt(2) );
                f.setFecha( rs.getString(3) );
                f.setTotal( rs.getInt(4) );
                f.setHora_entrada( rs.getString(5) );
                f.setHora_salida( rs.getString(6) );
                f.setTarifa( rs.getInt(7) );
                f.setCliente( rs.getString(8) );
                f.setTipo_pago( rs.getString(9) );
                facturas.add(f);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            facturas = null;
            return facturas;
        }
        return facturas;
    }
    
}
