/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Tablas.Inventario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ControlInventario {
    
    public Boolean Insertar(Inventario i){
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into inventario values(default,?,?,?,?)");
            p.setString( 1, i.getNombre());
            p.setInt(2, i.getCantidad());
            p.setInt(3, i.getMinimo());
            p.setInt(4, i.getPrecio());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Elminar(Inventario i){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from inventario where id_inventario=?");
            p.setInt( 1, i.getId_inventario());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Modificar(Inventario i){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update inventario set nombre=?, cantidad=?, minimo=?, precio=? where id_inventario=?");
            p.setString( 1, i.getNombre());
            p.setInt(2, i.getCantidad());
            p.setInt(3, i.getMinimo());
            p.setInt( 4, i.getPrecio());
            p.setInt( 5, i.getId_inventario());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Inventario GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        Inventario i = new Inventario();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from inventario where id_inventario=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                i.setId_inventario( id );
                i.setNombre( rs.getString(2) );
                i.setCantidad( rs.getInt(3) );
                i.setMinimo( rs.getInt(4) );
                i.setPrecio(rs.getInt(5) );
                
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            i = null;
            return i;
        }
        return i;
    }
    
    public ArrayList<Inventario> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Inventario> clientes = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from inventario order by id_inventario");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Inventario i = new Inventario();
                i.setId_inventario( rs.getInt(1) );
                i.setNombre( rs.getString(2) );
                i.setCantidad( rs.getInt(3) );
                i.setMinimo(rs.getInt(4) );
                i.setPrecio(rs.getInt(5) );
                clientes.add(i);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            clientes = null;
            return clientes;
        }
        return clientes;
    }
    
}
