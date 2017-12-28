/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Tablas.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ControlCliente {
    
    public Boolean Insertar(Cliente c){
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into cliente values(default,?,?,?)");
            p.setString( 1, c.getNombre());
            p.setString( 2, c.getApellido());
            p.setString( 3, c.getCedula());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Elminar(Cliente c){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from cliente where id_cliente=?");
            p.setInt( 1, c.getId_cliente());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Modificar(Cliente c){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update cliente set nombre=?, apellido=?, cedula=? where id_cliente=?");
            p.setString( 1, c.getNombre());
            p.setString( 2, c.getApellido());
            p.setString( 3, c.getCedula() );
            p.setInt( 4, c.getId_cliente());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Cliente GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        Cliente c = new Cliente();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from cliente where id_cliente=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                c.setId_cliente( id );
                c.setNombre( rs.getString(2) );
                c.setApellido( rs.getString(3) );
                c.setCedula( rs.getString(4) );
                
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            c = null;
            return c;
        }
        return c;
    }
    
    public ArrayList<Cliente> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Cliente> clientes = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from cliente order by id_cliente");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Cliente c = new Cliente();
                c.setId_cliente( rs.getInt(1) );
                c.setNombre( rs.getString(2) );
                c.setApellido( rs.getString(3) );
                c.setCedula( rs.getString(4) );
                clientes.add(c);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            clientes = null;
            return clientes;
        }
        return clientes;
    }
    
}
