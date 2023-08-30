package datos;

import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioJDBC {
    
    
     private static final String SQL_SELECT = "SELECT id_usuario, nombre, password FROM usuario";
     private static final String SQL_INSERT = "INSERT INTO usuario(nombre, password) VALUES(?,?)";
     private static final String SQL_UPDATE = "UPDATE usuario SET nombre=?, password=? WHERE id_usuario =?";
     private static final String SQL_DELETE = "DELETE FROM usuario WHERE id_usuario=?";
     
     
         public List<Usuario> select(){
         Connection con = null;
         PreparedStatement stmt = null;
         ResultSet rs = null;
         Usuario usuario = null;
         
         List<Usuario>usuarios = new ArrayList<Usuario>();
         
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            //en la iteracion nos lee la base de datos en el while, y por cada lectura completa nos crea un 
            //nuevo objeto persona y le va agregando a la lista de tipo persona (arraylist)
            while(rs.next()){
                int id_usuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String password = rs.getString("password");
                
                usuario = new Usuario();
                usuario.setId_usuario(id_usuario);
                usuario.setNombre(nombre);
                usuario.setPassword(password);
                
                usuarios.add(usuario);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        //el bloque finally se va a ejecutar siempre dsp`del try catch por lo que aprovechamos aqui para cerrar
        // los objetos de conexion que abrimos anteriormente
        finally{
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(con);
        }
        
        return usuarios;
                
     }
         
         
          public int insert(Usuario usuario){
         Connection con = null;
         PreparedStatement stmt = null;
         int rows = 0;
         
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_INSERT);
            //en esta parte del codigo se sustituyen los ? del string de insert en el orden que estan dados
            //ej el 1 es el nombre, el 2 es el pass,etc
            stmt.setString(1,usuario.getNombre());
            stmt.setString(2, usuario.getPassword());
            
            System.out.println("ejecutando query: " + SQL_INSERT);
            //aca guardamos los registros cambiados en la variable rows
            rows = stmt.executeUpdate();
            
            System.out.println("Registros afectados: " + rows);
        } catch (SQLException ex) {
           ex.printStackTrace(System.out);
        }
        //cerramos los objetos de conexion
        finally{
            Conexion.close(stmt);
            Conexion.close(con);
        }
        return rows;
     }
          
          public int update(Usuario usuario){
         Connection con = null;
         PreparedStatement stmt = null;
         int rows = 0;
         
        try {
            con = Conexion.getConnection();
            System.out.println("ejecutando query: " +  SQL_UPDATE);
            stmt = con.prepareStatement(SQL_UPDATE);
            
            stmt.setString(1,usuario.getNombre());
            stmt.setString(2, usuario.getPassword());
            
            rows = stmt.executeUpdate();
            System.out.println("registros actualizados: " + rows);
            
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        finally{
            Conexion.close(con);
            Conexion.close(stmt);
        }
        return rows;
     }
     
      public int delete(Usuario usuario){
         Connection con = null;
         PreparedStatement stmt = null;
         int rows = 0;
         
        try {
            con = Conexion.getConnection();
            System.out.println("ejecutando query: " +  SQL_DELETE);
            stmt = con.prepareStatement(SQL_DELETE);
            
            //en la sentencia delete solo debemos proporcionar 1 parametro para eliminar
            //es columna por lo tanto usamos el idpersona
            stmt.setInt(1, usuario.getId_usuario());
            
            rows = stmt.executeUpdate();
            System.out.println("registros elimnados: " + rows);
            
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        finally{
            Conexion.close(con);
            Conexion.close(stmt);
        }
        return rows;
     }
     
     
}
