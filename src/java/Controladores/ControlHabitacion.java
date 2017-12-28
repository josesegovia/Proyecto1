/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Tablas.Habitaciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author Admin
 */
public class ControlHabitacion {
    
    
    public Boolean Insertar(Habitaciones h){
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into habitaciones values(default,?,?)");
            p.setString( 1, h.getTipo());
            p.setString( 2, h.getEstado());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Elminar(Habitaciones h){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from habitaciones where id_habitacion=?");
            p.setInt( 1, h.getId_habitacion());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Modificar(Habitaciones h){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update habitaciones set tipo=?, estado=? where id_habitacion=?");
            p.setString( 1, h.getTipo());
            p.setString( 2, h.getEstado());
            p.setInt( 3, h.getId_habitacion());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Liberar(){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update habitaciones set estado=? ");
            p.setString( 1, "L");
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Habitaciones GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        Habitaciones h = new Habitaciones();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from habitaciones where id_habitacion=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                h.setId_habitacion(id );
                h.setTipo(rs.getString(2) );
                h.setEstado(rs.getString(3) );
                
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            h = null;
            return h;
        }
        return h;
    }
    
    public ArrayList<Habitaciones> GetAllLibres(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Habitaciones> habitaciones = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from habitaciones where estado = 'L'");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Habitaciones h = new Habitaciones();
                h.setId_habitacion(rs.getInt(1));
                h.setTipo(rs.getString(2) );
                h.setEstado(rs.getString(3) );
                habitaciones.add(h);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            habitaciones = null;
            return habitaciones;
        }
        return habitaciones;
    }
    
    public ArrayList<Habitaciones> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Habitaciones> habitaciones = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from habitaciones order by id_habitacion");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Habitaciones h = new Habitaciones();
                h.setId_habitacion(rs.getInt(1));
                h.setTipo(rs.getString(2) );
                h.setEstado(rs.getString(3) );
                habitaciones.add(h);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            habitaciones = null;
            return habitaciones;
        }
        return habitaciones;
    }
    
}
