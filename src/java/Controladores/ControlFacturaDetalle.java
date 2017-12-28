/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Tablas.FacturaDetalle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ControlFacturaDetalle {
    
    public Boolean Insertar(FacturaDetalle f){
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into facturadetalle values(default,?,?,?,?)");
            p.setInt(1, f.getId_factura());
            p.setString(2, f.getProducto());
            p.setInt(3, f.getCantidad());
            p.setInt(4, f.getPrecio());
            
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Elminar(FacturaDetalle f){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from facturadetalle where id_detalle=?");
            p.setInt( 1, f.getId_detalle());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Modificar(FacturaDetalle f){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update facturadetalle set producto=?, cantidad=?, precio=? where id_factura=?");
            p.setString( 1, f.getProducto());
            p.setInt(2, f.getCantidad());
            p.setInt(3, f.getPrecio());
            p.setInt(4, f.getId_factura());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }    
    
    public FacturaDetalle GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        FacturaDetalle f= new FacturaDetalle();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from facturadetalle where id_detalle=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                f.setId_detalle(id);
                f.setId_factura( rs.getInt(2) );
                f.setProducto( rs.getString(3) );
                f.setCantidad( rs.getInt(4) );
                f.setPrecio( rs.getInt(5) );
                
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            f = null;
            return f;
        }
        return f;
    }
    
    public ArrayList<FacturaDetalle> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<FacturaDetalle> facturasdetalles = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from facturadetalle order by id_detalle");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                FacturaDetalle f = new FacturaDetalle();
                f.setId_detalle( rs.getInt(1) );
                f.setId_factura( rs.getInt(2) );
                f.setProducto( rs.getString(3) );
                f.setCantidad( rs.getInt(4) );
                f.setPrecio( rs.getInt(5) );
                facturasdetalles.add(f);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            facturasdetalles = null;
            return facturasdetalles;
        }
        return facturasdetalles;
    }
    
    public ArrayList<FacturaDetalle> GetAllById(int id){
        
        Connection con;
        PreparedStatement p;
        ArrayList<FacturaDetalle> facturasdetalles = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from facturadetalle where id_Factura=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                FacturaDetalle f = new FacturaDetalle();
                f.setId_detalle( rs.getInt(1) );
                f.setId_factura( rs.getInt(2) );
                f.setProducto( rs.getString(3) );
                f.setCantidad( rs.getInt(4) );
                f.setPrecio( rs.getInt(5) );
                facturasdetalles.add(f);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            facturasdetalles = null;
            return facturasdetalles;
        }
        return facturasdetalles;
    }
    
}
