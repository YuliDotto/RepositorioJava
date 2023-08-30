package datos;

import domain.Persona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//el uso de transacciones con mysql nos permite hacer un COMMIT o un ROLLBACK (volver) cuando realizamos un cambio
//en la base de datos y nos genero algun problema, de esa forma mantenemos la integridad de nuestra
//base de datos, el rollback va en e bloque CATCH
//el commit hace queq los cambios se efectuen en la base de datos y ocurre cuando se cierra la coneccion close()
// las transaccions son uso de SELECT INSERT UPDATE O DELETE pero sobre cualquier tabla de la base de datos y no solo una
public class PersonaJDBC {

    private Connection conexionTransaccional;

    private static final String SQL_SELECT = "SELECT id_personas, nombre, apellido, email, telefono FROM personas";
    //en el query insert ponemos los valores en ? porque despues los vamos a reemplazar
    private static final String SQL_INSERT = "INSERT INTO personas(nombre, apellido, email, telefono) VALUES(?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE personas SET nombre=?, apellido=?, email=?,telefono=? WHERE id_personas =?";
    private static final String SQL_DELETE = "DELETE FROM personas WHERE id_personas=?";

    //la diferencia entre conexion transaccional con la comun es que la conexion queda abierta en caso de que
    //necesitemos hacer un rollback en la base de datos, para eso modificamos tambien los metodos creados
    //en la conexion comun
    public PersonaJDBC() {
    }

    public PersonaJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    public List<Persona> select() throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Persona persona = null;

        List<Persona> personas = new ArrayList<Persona>();

        try {
            //el uso de ?y : en java es como if else, pregunta si conexionTransaccional es distinto a null,
            //si es true entonces nos devuelve el valor antes de los ":", si es false nos devuelve el valor dsp de ":"
            //boolean result = a>b ? true : false;

            //aca modificacmos la conexion con para hacer un condicional y agregamos la conexion transaccional
            con = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = con.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            //en la iteracion nos lee la base de datos en el while, y por cada lectura completa nos crea un 
            //nuevo objeto persona y le va agregando a la lista de tipo persona (arraylist)
            while (rs.next()) {
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

            // el manejo de la excepcion esta realizado en el main (manejo personas) por lo tanto
            //quitamos el bloque catch de nuestros metodos y agregamos la clausula de throws sql exception
            //que nos da el IDE
            //  } catch (SQLException ex) {
            //    ex.printStackTrace(System.out);
        } //el bloque finally se va a ejecutar siempre dsp`del try catch por lo que aprovechamos aqui para cerrar
        // los objetos de conexion que abrimos anteriormente
        finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            //y ponemos el cierre de conexion dentro de un if por si no se uso la conexion transaccional se cierra la conexion
            if (this.conexionTransaccional == null) {
                Conexion.close(con);
            }
        }

        return personas;

    }

    //este metodo nos devuelve la cantidad de filas que han sido afectadas por lo tanto es un metodo int
    public int insert(Persona persona) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            con = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = con.prepareStatement(SQL_INSERT);
            //en esta parte del codigo se sustituyen los ? del string de insert en el orden que estan dados
            //ej el 1 es el nombre, el 2 es el apellido,etc
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());

            System.out.println("ejecutando query: " + SQL_INSERT);
            //aca guardamos los registros cambiados en la variable rows
            rows = stmt.executeUpdate();

            System.out.println("Registros afectados: " + rows);

            // el manejo de la excepcion esta realizado en el main (manejo personas) por lo tanto
            //quitamos el bloque catch de nuestros metodos y agregamos la clausula de throws sql exception
            //que nos da el IDE
            //    } catch (SQLException ex) {
            //      ex.printStackTrace(System.out);
        } //cerramos los objetos de conexion
        finally {
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(con);
            }
        }
        return rows;
    }

    //este metodo es para reescribir informacion dentro de la base de datos, toma como parametro un objeto de la clase
    //y el int rows nos dira la cantidad de filas que han sido modificados
    public int update(Persona persona) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            con = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("ejecutando query: " + SQL_UPDATE);
            stmt = con.prepareStatement(SQL_UPDATE);

            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            stmt.setInt(5, persona.getId_persona());

            rows = stmt.executeUpdate();
            System.out.println("registros actualizados: " + rows);

            // el manejo de la excepcion esta realizado en el main (manejo personas) por lo tanto
            //quitamos el bloque catch de nuestros metodos y agregamos la clausula de throws sql exception
            //que nos da el IDE
            
            
    //    } catch (SQLException ex) {
      //      ex.printStackTrace(System.out);
            
        
        } finally {
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(con);
            }
        }
        return rows;
    }

    public int delete(Persona persona) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            con = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("ejecutando query: " + SQL_DELETE);
            stmt = con.prepareStatement(SQL_DELETE);

            //en la sentencia delete solo debemos proporcionar 1 parametro para eliminar
            //es columna por lo tanto usamos el idpersona
            stmt.setInt(1, persona.getId_persona());

            rows = stmt.executeUpdate();
            System.out.println("registros elimnados: " + rows);

            // el manejo de la excepcion esta realizado en el main (manejo personas) por lo tanto
            //quitamos el bloque catch de nuestros metodos y agregamos la clausula de throws sql exception
            //que nos da el IDE
            //  } catch (SQLException ex) {
            //    ex.printStackTrace(System.out);
        } finally {
            if (this.conexionTransaccional == null) {
                Conexion.close(con);
            }
            Conexion.close(stmt);
        }
        return rows;
    }
}
