/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Tablas.Membrecia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author Admin
 */
public class ControlMembrecia {
    
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
    
    public Boolean Elminar(Membrecia m){
        
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
    
    public Boolean Elminar(int x){
        
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
