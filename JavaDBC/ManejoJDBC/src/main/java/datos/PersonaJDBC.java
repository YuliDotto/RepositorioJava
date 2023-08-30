package datos;

import domain.Persona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaJDBC {
    private static final String SQL_SELECT = "SELECT id_personas, nombre, apellido, email, telefono FROM personas";
    //en el query insert ponemos los valores en ? porque despues los vamos a reemplazar
     private static final String SQL_INSERT = "INSERT INTO personas(nombre, apellido, email, telefono) VALUES(?,?,?,?)";
     private static final String SQL_UPDATE = "UPDATE personas SET nombre=?, apellido=?, email=?,telefono=? WHERE id_personas =?";
     private static final String SQL_DELETE = "DELETE FROM personas WHERE id_personas=?";
     
     public List<Persona> select(){
         Connection con = null;
         PreparedStatement stmt = null;
         ResultSet rs = null;
         Persona persona = null;
         
         List<Persona>personas = new ArrayList<Persona>();
         
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            //en la iteracion nos lee la base de datos en el while, y por cada lectura completa nos crea un 
            //nuevo objeto persona y le va agregando a la lista de tipo persona (arraylist)
            while(rs.next()){
                int id_personas = rs.getInt("id_personas");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                
                persona = new Persona();
                persona.setId_persona(id_personas);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                persona.setEmail(email);
                persona.setTelefono(telefono);
                
                personas.add(persona);
                        
                
                
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
        
        return personas;
                
     }
     
     //este metodo nos devuelve la cantidad de filas que han sido afectadas por lo tanto es un metodo int
     public int insert(Persona persona){
         Connection con = null;
         PreparedStatement stmt = null;
         ResultSet rs = null;
         int rows = 0;
         
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_INSERT);
            //en esta parte del codigo se sustituyen los ? del string de insert en el orden que estan dados
            //ej el 1 es el nombre, el 2 es el apellido,etc
            stmt.setString(1,persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            
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
     
     //este metodo es para reescribir informacion dentro de la base de datos, toma como parametro un objeto de la clase
     //y el int rows nos dira la cantidad de filas que han sido modificados
     public int update(Persona persona){
         Connection con = null;
         PreparedStatement stmt = null;
         int rows = 0;
         
        try {
            con = Conexion.getConnection();
            System.out.println("ejecutando query: " +  SQL_UPDATE);
            stmt = con.prepareStatement(SQL_UPDATE);
            
            
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            stmt.setInt(5, persona.getId_persona());
            
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
     
     public int delete(Persona persona){
         Connection con = null;
         PreparedStatement stmt = null;
         int rows = 0;
         
        try {
            con = Conexion.getConnection();
            System.out.println("ejecutando query: " +  SQL_DELETE);
            stmt = con.prepareStatement(SQL_DELETE);
            
            //en la sentencia delete solo debemos proporcionar 1 parametro para eliminar
            //es columna por lo tanto usamos el idpersona
            stmt.setInt(1, persona.getId_persona());
            
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
