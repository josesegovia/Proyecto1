/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: ControlInventario
 * @autor: Jose Segovia
 * Año: 2017
 */
package Controladores;

import Tablas.Inventario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @autor: Jose Segovia
 */
public class ControlInventario {
    
    /**
     * Esta clase recibe una clase inventario para insertarla en la BD
     * @param i Inventario
     * @return Boolean
     */
    public Boolean Insertar(Inventario i){
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into inventario values(default,?,?,?,?)");
            p.setString( 1, i.getNombre());
            p.setInt(2, i.getCantidad());
            p.setInt(3, i.getMinimo());
            p.setInt(4, i.getPrecio());
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
     * Esta clase recibe una clase inventario para eliminarla de la BD
     * @param i Inventario
     * @return Boolean
     */
    public Boolean Eliminar(Inventario i){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from inventario where id_inventario=?");
            p.setInt( 1, i.getId_inventario());
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
     * Esta clase recibe una clase inventario para modificarla en la BD
     * @param i Inventario
     * @return Boolean
     */
    public Boolean Modificar(Inventario i){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update inventario set nombre=?, cantidad=?, minimo=?, precio=? where id_inventario=?");
            p.setString( 1, i.getNombre());
            p.setInt(2, i.getCantidad());
            p.setInt(3, i.getMinimo());
            p.setInt( 4, i.getPrecio());
            p.setInt( 5, i.getId_inventario());
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
     * Esta clase recibe un entero y devuelve una clase Inventario especifica
     * @param id int
     * @return Inventario
     */
    public Inventario GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        Inventario i = new Inventario();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from inventario where id_inventario=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                i.setId_inventario( id );
                i.setNombre( rs.getString(2) );
                i.setCantidad( rs.getInt(3) );
                i.setMinimo( rs.getInt(4) );
                i.setPrecio(rs.getInt(5) );
                
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            i = null;
            return i;
        }
        return i;
    }
    
    /**
     * Esta clase devuelve todos los productos de la tabla Inventario
     * @return ArrayList de Inventario
     */
    public ArrayList<Inventario> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Inventario> clientes = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from inventario order by id_inventario");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Inventario i = new Inventario();
                i.setId_inventario( rs.getInt(1) );
                i.setNombre( rs.getString(2) );
                i.setCantidad( rs.getInt(3) );
                i.setMinimo(rs.getInt(4) );
                i.setPrecio(rs.getInt(5) );
                clientes.add(i);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            clientes = null;
            return clientes;
        }
        return clientes;
    }
    
}
