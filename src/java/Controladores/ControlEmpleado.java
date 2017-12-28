/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;
import Tablas.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author Admin
 */
public class ControlEmpleado {
    
    public Boolean Insertar(Empleado e){
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("insert into empleado values(default,?,?,?,?,?,default)");
            p.setString( 1, e.getNombre());
            p.setString( 2, e.getApellido());
            p.setString( 3, e.getCedula());
            p.setString( 4, e.getNombre_usuario());
            p.setString( 5, e.getPassword());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Elminar(Empleado e){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("delete from empleado where id_empleado=?");
            p.setInt( 1, e.getId_empleado());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean Modificar(Empleado e){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update empleado set nombre=?, apellido=?, cedula=?, nombre_usuario=?, password=?, rol=? where id_empleado=?");
            p.setString( 1, e.getNombre());
            p.setString( 2, e.getApellido());
            p.setString( 3, e.getCedula() );
            p.setString( 4, e.getNombre_usuario());
            p.setString( 5, e.getPassword());
            p.setInt( 6, e.getRol());
            p.setInt( 7, e.getId_empleado());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public Boolean CambiarContraseña(Empleado e){
        
        Boolean a;
        Connection con;
        PreparedStatement p;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("update empleado password=? where id_empleado=?");
            p.setString( 1, e.getPassword());
            p.setInt( 2, e.getId_empleado());
            p.execute();
        
            a = true;
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            a=false;
            return a;
        }
        return a;
    }
    
    public int Verificar(Empleado e){
        Boolean a;
        int id=0;
        Connection con;
        PreparedStatement p;
        ResultSet rs;

        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from empleado where nombre_usuario=? and password=?");
            String nombre = e.getNombre_usuario();
            String pass = e.getPassword();
            p.setString(1 , nombre );
            p.setString(2 , pass );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                id = rs.getInt(1);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            return id;
        }
        return id;
    }
    
    public Empleado GetbyId(int id){
        
        Connection con;
        PreparedStatement p;
        Empleado e = new Empleado();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from empleado where id_empleado=?");
            p.setInt( 1, id );
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                e.setId_empleado(id );
                e.setNombre( rs.getString(2) );
                e.setApellido( rs.getString(3) );
                e.setCedula( rs.getString(4) );
                e.setNombre_usuario( rs.getString(5) );
                e.setPassword(rs.getString(6) );
                e.setRol( rs.getInt("rol") );
                
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            e = null;
            return e;
        }
        return e;
    }
    
    public ArrayList<Empleado> GetAll(){
        
        Connection con;
        PreparedStatement p;
        ArrayList<Empleado> clientes = new ArrayList<>();
        ResultSet rs;
        
        try{
            con = DBUtils.getConnection();

            p = con.prepareStatement("select * from empleado order by id_empleado");
            rs = p.executeQuery();
            while ( rs.next() ) {                
                
                Empleado e = new Empleado();
                e.setId_empleado(rs.getInt(1) );
                e.setNombre( rs.getString(2) );
                e.setApellido( rs.getString(3) );
                e.setCedula( rs.getString(4) );
                e.setNombre_usuario( rs.getString(5) );
                e.setPassword(rs.getString(6) );
                e.setRol( rs.getInt("rol") );
                clientes.add(e);
            }
            DBUtils.closeConnection(con);
        }catch (Exception ex){
            clientes = null;
            return clientes;
        }
        return clientes;
    }
    
}
