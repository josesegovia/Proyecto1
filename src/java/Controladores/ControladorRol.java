/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: ControladorRol
 * @autor: Jose Segovia
 * Año: 2017
 */
package Controladores;

import Tablas.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @autor: Jose Segovia
 */
public class ControladorRol {
    
    /**
     * Esta clase recibe una clase Rol para Insertarla en la BD
     * @param r Rol
     * @return Boolean
     */
    public Boolean Insertar(Rol r){
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into rol values(default,?,default,default,default,default,default,default,default,default)");
            p.setString( 1, r.getNombre_rol());
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
     * Esta clase recibe una clase Rol para eliminarla de la BD
     * @param r Rol
     * @return Boolean
     */
    public Boolean Eliminar(Rol r){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from rol where id_rol=?");
            p.setInt( 1, r.getId_rol());
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
     * Esta clase recibe una clase Rol para modificarla en la BD
     * @param r Rol
     * @return Boolean
     */
    public Boolean Modificar(Rol r){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update rol set nombre_rol=?, cliente=?, empleado=?, habitacion=?, inventario=?, roles=?, socios=?, historial=?, factura=? where id_rol=?");
            p.setString( 1, r.getNombre_rol());
            p.setString( 2, r.getCliente());
            p.setString( 3, r.getEmpleado());
            p.setString( 4, r.getHabitacion());
            p.setString( 5, r.getInventario());
            p.setString( 6, r.getRoles());
            p.setString( 7, r.getSocios());
            p.setString( 8, r.getHistorial());
            p.setString( 9, r.getFactura());
            p.setInt(10, r.getId_rol());
            
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
     * Esta clase recibe un entero y devuelve una clase Rol especifica
     * @param id int
     * @return Rol
     */
    public Rol GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        Rol r = new Rol();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from rol where id_rol=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                r.setId_rol(id );
                r.setNombre_rol(rs.getString(2) );
                r.setCliente(rs.getString(3) );
                r.setEmpleado(rs.getString(4) );
                r.setHabitacion(rs.getString(5) );
                r.setInventario(rs.getString(6) );
                r.setRoles(rs.getString(7));
                r.setSocios(rs.getString(8));
                r.setHistorial(rs.getString(9));
                r.setFactura(rs.getString(10));
                
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            r = null;
            return r;
        }
        return r;
    }
    
    /**
     * Esta clase devuelve todos los Roles de la BD
     * @return ArrayList de Rol
     */
    public ArrayList<Rol> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Rol> clientes = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from rol order by id_rol");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Rol r = new Rol();
                r.setId_rol(rs.getInt(1));
                r.setNombre_rol(rs.getString(2) );
                r.setCliente(rs.getString(3) );
                r.setEmpleado(rs.getString(4) );
                r.setHabitacion(rs.getString(5) );
                r.setInventario(rs.getString(6) );
                r.setRoles(rs.getString(7));
                r.setSocios(rs.getString(8));
                r.setHistorial(rs.getString(9));
                r.setFactura(rs.getString(10));
                clientes.add(r);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            clientes = null;
            return clientes;
        }
        return clientes;
    }
    
}
