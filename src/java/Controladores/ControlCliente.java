/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: ControlCliente
 * @autor: Jose Segovia
 * Año: 2017
 */
package Controladores;

import Tablas.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @autor: Jose Segovia
 */
public class ControlCliente {
    
    /**
     * Esta clase recibe una clase Cliente para insertarla en la BD
     * @param c Cliente
     * @return booblean
     */
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
    
    /**
     * Esta clase recibe una clase cliente para eliminarla de la BD
     * @param c Cliente
     * @return boolean
     */
    public Boolean Eliminar(Cliente c){
        
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
    
    /**
     * Esta clase recibe una clace Cliente para modificarla en la BD
     * @param c Cliente
     * @return Boolean
     */
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
    
    /**
     * Esta clase recibe un entero para buscar y devolver una clase Cliente por su id
     * @param id
     * @return Cliente
     */
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
    
    /**
     * Esta clase regresa todos los clientes de la BD
     * @return ArrayList de Cliente
     */
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
