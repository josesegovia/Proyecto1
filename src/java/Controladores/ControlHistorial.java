/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: ControlHistorial
 * @autor: Jose Segovia
 * Año: 2017
 */
package Controladores;

import Tablas.Historial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @autor: Jose Segovia
 */
public class ControlHistorial {
    
    /**
     * Esta clase recibe una clase Historial para insertarla en la BD
     * @param h Historial
     * @return Boolean
     */
    public Boolean Insertar(Historial h){
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into historial values(default,?,?,?,?,?,?,?)");
            p.setInt(1, h.getId_habitacion());
            p.setString(2, h.getHora_entrada());
            p.setString(3, h.getHora_salida());
            p.setString(4, h.getDia_semana());
            p.setString(5, h.getFecha_entrada());
            p.setString(6, h.getFecha_salida());
            p.setInt(7, h.getCliente() );
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
     * Esta clase recibe una clase Historial para eliminarla en la BD
     * @param h Historial
     * @return Boolean
     */
    public Boolean Eliminar(Historial h){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from historial where id_historial=?");
            p.setInt( 1, h.getId_historial());
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
     * Esta clase recibe una clase Historial para modificarla en la BD
     * @param h Historial
     * @return Boolean
     */
    public Boolean Modificar(Historial h){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update historial set hora_salida=?, fecha_salida=? where id_historial=?");
            p.setString( 1, h.getHora_salida());
            p.setString( 2, h.getFecha_salida());
            p.setInt( 3, h.getId_historial());
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
     * Esta clase recibe un entero y devuelve una clase Historial especifica
     * @param id int
     * @return Historial
     */
    public Historial GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        Historial h = new Historial();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from historial where id_historial=?");
            p.setInt( 1, id);
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                h.setId_historial(id);
                h.setId_habitacion( rs.getInt(2) );
                h.setHora_entrada( rs.getString(3) );
                h.setHora_salida( rs.getString(4) );
                h.setDia_semana(rs.getString(5) );
                h.setFecha_entrada(rs.getString(6));
                h.setFecha_salida(rs.getString(7));
                h.setCliente( rs.getInt(8) );
                
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            h = null;
            return h;
        }
        return h;
    }
    
    /**
     * Esta clase devuelve todas las Hsitorias de las BD
     * @return ArrayList de Historial
     */
    public ArrayList<Historial> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Historial> historias = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from historial order by id_historial");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Historial h = new Historial();
                h.setId_historial( rs.getInt(1));
                h.setId_habitacion( rs.getInt(2) );
                h.setHora_entrada( rs.getString(3) );
                h.setHora_salida( rs.getString(4) );
                h.setDia_semana(rs.getString(5) );
                h.setFecha_entrada(rs.getString(6));
                h.setFecha_salida(rs.getString(7));
                h.setCliente( rs.getInt(8) );
                historias.add(h);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            historias = null;
            return historias;
        }
        return historias;
    }
    
    /**
     * Esta clase devuelve todas las Historias incompletas, que son las que todabia no han finalizado
     * @return ArrayList de Historial
     */
    public ArrayList<Historial> GetAllOcupados(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Historial> historias = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from historial where hora_salida=''");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Historial h = new Historial();
                h.setId_historial( rs.getInt(1));
                h.setId_habitacion( rs.getInt(2) );
                h.setHora_entrada( rs.getString(3) );
                h.setHora_salida( rs.getString(4) );
                h.setDia_semana(rs.getString(5) );
                h.setFecha_entrada(rs.getString(6));
                h.setFecha_salida(rs.getString(7));
                h.setCliente( rs.getInt(8) );
                historias.add(h);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            historias = null;
            return historias;
        }
        return historias;
    }
    
}
