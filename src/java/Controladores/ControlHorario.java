/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: ControlHorario
 * @autor: Jose Segovia
 * Año: 2017
 */
package Controladores;

import Tablas.Horario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @autor: Jose Segovia
 */
public class ControlHorario {
    
    /**
     * Esta clase recibe una clase Horario para insertarla en la BD
     * @param h Horario
     * @return Boolean
     */
    public Boolean Insertar(Horario h){
        Boolean a= false;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into horario values(?,?,?,?,?,?,?,?)");
            p.setInt(1, h.getId_empleado());
            p.setString( 2, h.getLunes() );
            p.setString( 3, h.getMartes() );
            p.setString( 4, h.getMiercoles() );
            p.setString( 5, h.getJueves() );
            p.setString( 6, h.getViernes() );
            p.setString( 7, h.getSabado() );
            p.setString( 8, h.getDomingo());
            
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            return a;
        }
        return a;
    }
    
    /**
     * Esta clase recibe una clase Horario para eliminarla de la BD
     * @param h Horario
     * @return Boolean
     */
    public Boolean Eliminar(Horario h){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from horario where id_empleado=?");
            p.setInt( 1, h.getId_empleado());
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
     * Esta clase recibe un entero para eliminar un Horario especifico de la BD
     * @param x int
     * @return Boolean
     */
    public Boolean Eliminar(int x){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from horario where id_empleado=?");
            p.setInt( 1, x);
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
     * Esta clase recibe una clase Horario para modificarla en la BD
     * @param h Horario
     * @return Boolean
     */
    public Boolean Modificar(Horario h){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update horario set lunes=?, martes=?, miercoles=?, jueves=?, viernes=?, sabado=?, domingo=? where id_empleado=?");
            p.setString( 1, h.getLunes() );
            p.setString( 2, h.getMartes() );
            p.setString( 3, h.getMiercoles() );
            p.setString( 4, h.getJueves() );
            p.setString( 5, h.getViernes() );
            p.setString( 6, h.getSabado() );
            p.setString( 7, h.getDomingo());
            p.setInt(8, h.getId_empleado());
            
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
     * Esta clase recibe un entero y devuelve una Horario especifico
     * @param id int
     * @return Horario
     */
    public Horario GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        Horario h = new Horario();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from horario where id_empleado=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                h.setId_empleado(id);
                h.setLunes(rs.getString(2));
                h.setMartes(rs.getString(3));
                h.setMiercoles(rs.getString(4));
                h.setJueves(rs.getString(5));
                h.setViernes(rs.getString(6));
                h.setSabado(rs.getString(7));
                h.setDomingo(rs.getString(8));
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            h = null;
            return h;
        }
        return h;
    }
    
    /**
     * Esta clase regresa todos los horarios de la BD
     * @return ArrayList de Horario
     */
    public ArrayList<Horario> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Horario> horarios = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from horario order by id_empleado");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Horario h = new Horario();
                h.setId_empleado(rs.getInt(1));
                h.setLunes(rs.getString(2));
                h.setMartes(rs.getString(3));
                h.setMiercoles(rs.getString(4));
                h.setJueves(rs.getString(5));
                h.setViernes(rs.getString(6));
                h.setSabado(rs.getString(7));
                h.setDomingo(rs.getString(8));
                horarios.add(h);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            horarios = null;
            return horarios;
        }
        return horarios;
    }
    
}
