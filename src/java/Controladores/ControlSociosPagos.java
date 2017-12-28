/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: ControlSociosPagos
 * @autor: Jose Segovia
 * Año: 2017
 */
package Controladores;

import Tablas.SociosPagos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @autor: Jose Segovia
 */
public class ControlSociosPagos {
    
    /**
     * Esta clase recibe una clase SociosPagos para insertarla en la BD
     * @param s SociosPagos
     * @return Boolean
     */
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
    
    /**
     * Esta clase recibe una clase SociosPagos para eliminarla de la BD
     * @param s SociosPagos
     * @return Boolean
     */
    public Boolean Eliminar(SociosPagos s){
        
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
    
    /**
     * Esta clase recibe una clase SociosPagos para modificarla en la BD
     * @param s SociosPagos
     * @return Boolean
     */
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
    
    /**
     * Esta clase Recibe un entero y devuelve una clase SociosPagos especifica
     * @param id int
     * @return SociosPagos
     */
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
    
    /**
     * Esta clase devuelve todos los Pagos de Socios en la BD
     * @return ArrayList de SociosPagos
     */
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
    
    /**
     * Esta clase devuelve todos los Pagos de un Socio en especifico de la BD
     * @param id
     * @return ArrayList de SociosPagos
     */
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
