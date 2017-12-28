/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: ControlMembrecia
 * @autor: Jose Segovia
 * Año: 2017
 */
package Controladores;

import Tablas.Membrecia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @autor: Jose Segovia
 */
public class ControlMembrecia {
    
    /**
     * Esta clase recibe una clase Membrecia para insertarla en la BD
     * @param m Membrecia
     * @return Boolean
     */
    public Boolean Insertar(Membrecia m){
        Boolean a= false;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into membrecia values(default,?,?)");
            p.setInt(1, m.getId_cliente());
            p.setString( 2, m.getEstado());
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
     * Esta clase recibe una clase Membrecia para eliminarla de la BD
     * @param m Membrecia
     * @return Boolean
     */
    public Boolean Eliminar(Membrecia m){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from membrecia where id_membrecia=?");
            p.setInt( 1, m.getId_membrecia());
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
     * Esta clase recibe un entero para eliminar una Membrecia especifica de la BD
     * @param x int
     * @return Boolean
     */
    public Boolean Eliminar(int x){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from membrecia where id_membrecia=?");
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
     * Esta clase recibe una clase Membrecia para modificarla en la BD
     * @param m Membrecia
     * @return Boolean
     */
    public Boolean Modificar(Membrecia m){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update membrecia set estado=? where id_membrecia=?");
            p.setString( 1, m.getEstado());
            p.setInt (2, m.getId_membrecia());
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
     * Esta clase recibe un entero y devuelve una clase membrecia especifica
     * @param id int
     * @return Membrecia
     */
    public Membrecia GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        Membrecia m = new Membrecia();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from membrecia where id_membrecia=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                m.setId_membrecia(id);
                m.setId_cliente(rs.getInt(2));
                m.setEstado(rs.getString(3));
                
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            m = null;
            return m;
        }
        return m;
    }
    
    /**
     * Esta clase recibe un entero y devuelve una clase Membrecia por el id del cliente
     * @param id int 
     * @return Membrecia
     */
    public Membrecia GetbyIdSocio(int id){
        
        Connection con;
        PreparedStatement p;
        Membrecia m = new Membrecia();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from membrecia where cliente=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                m.setId_membrecia(rs.getInt(1));
                m.setId_cliente(rs.getInt(2));
                m.setEstado(rs.getString(3));
                
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            m = null;
            return m;
        }
        return m;
    }
    
    /**
     * Esta clase devuelve todas las Membrecias de la BD
     * @return ArrayList de Membrecia
     */
    public ArrayList<Membrecia> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Membrecia> membrecias = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from membrecia order by id_membrecia");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Membrecia m = new Membrecia();
                m.setId_membrecia(rs.getInt(1));
                m.setId_cliente(rs.getInt(2));
                m.setEstado(rs.getString(3));
                membrecias.add(m);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            membrecias = null;
            return membrecias;
        }
        return membrecias;
    }
    
}
