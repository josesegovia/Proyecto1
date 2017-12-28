/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: ControlHabitacion
 * @autor: Jose Segovia
 * Año: 2017
 */
package Controladores;

import Tablas.Habitaciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @autor: Jose Segovia
 */
public class ControlHabitacion {
    
    /**
     * Esta clase recibe una clase Habitaciones para insertarla en la BD
     * @param h Habitaciones
     * @return Boolean
     */
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
    
    /**
     * Esta clase recibe una clase Habitaciones para eliminarla de la BD
     * @param h Habitaciones
     * @return Boolean
     */
    public Boolean Eliminar(Habitaciones h){
        
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
    
    /**
     * Esta clase recibe una clase Habitaciones para modificarla en la BD
     * @param h Habitaciones
     * @return Boolean
     */
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
    
    /**
     * Esta clase recibe un entero y devuelve una clase Habitaciones con un id especifico
     * @param id int
     * @return Habitaciones
     */
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
    
    /**
     * Esta clase devuelve todas las habitaciones de la BD que no se encuentran ocupadas (estado libre)
     * @return ArrayList de Habitaciones
     */
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
    
    /**
     * Esta clase devuelve todas las habitaciones de la BD
     * @return ArrayList de Habitaciones
     */
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
