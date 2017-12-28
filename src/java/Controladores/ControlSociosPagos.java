/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Tablas.SociosPagos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ControlSociosPagos {
    
    public Boolean Insertar(SociosPagos s){
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into socios_pagos values(default,?,?,?)");
            p.setInt(1, s.getId_socio() );
            p.setString( 2, s.getEstado() );
            p.setString( 3, s.getFecha() );
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Elminar(SociosPagos s){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from socios_pagos where id_pagos=?");
            p.setInt( 1, s.getId_pagos() );
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Modificar(SociosPagos s){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update socios_pagos set estado=?, fecha=? where id_pagos=?");
            p.setString( 1, s.getEstado() );
            p.setString( 2, s.getFecha() );
            p.setInt(3, s.getId_pagos() );
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public SociosPagos GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        SociosPagos s = new SociosPagos();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from socios_pagos where id_pagos=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                s.setId_pagos( id );
                s.setId_socio( rs.getInt(2) );
                s.setEstado( rs.getString(3) );
                s.setFecha(rs.getString(4) );
                
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            s = null;
            return s;
        }
        return s;
    }
    
    public ArrayList<SociosPagos> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<SociosPagos> pagos = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from socios_pagos order by id_pagos");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                SociosPagos s = new SociosPagos();
                s.setId_pagos( rs.getInt(1) );
                s.setId_socio( rs.getInt(2) );
                s.setEstado( rs.getString(3) );
                s.setFecha(rs.getString(4) );
                pagos.add(s);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            pagos = null;
            return pagos;
        }
        return pagos;
    }
    
    public ArrayList<SociosPagos> GetAllBySocio(int id){
        
        Connection con;
        PreparedStatement p;
        ArrayList<SociosPagos> pagos = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from socios_pagos where id_socio=? order by id_pagos");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                SociosPagos s = new SociosPagos();
                s.setId_pagos( rs.getInt(1) );
                s.setId_socio( rs.getInt(2) );
                s.setEstado( rs.getString(3) );
                s.setFecha(rs.getString(4) );
                pagos.add(s);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            pagos = null;
            return pagos;
        }
        return pagos;
    }
}
