package test;

import datos.Conexion;
import datos.PersonaJDBC;
import domain.Persona;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManejoPersonas {

    public static void main(String[] args) {
        
        //en este caso la variable conexion se instancia fuera del bloque con valor null y si se logra la conexion
        //entonces se ejecuta el getConnection
    Connection conexion = null;
        try {
            //aca creamos una conexion a la base de datos y preguntamos si el autocommit es verdadero
            //su valor predeterminado es true asique lo cambiamos a false para que no haga un autocommit
            //ya que nosotros lo haremos manualmente
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            PersonaJDBC personaJdbc = new PersonaJDBC(conexion);
            
            //hacemos un update cambiando el campo que queremos y dejamos los demas con los mismos valores
            //en este caso cambiamos el nombre
            
            Persona cambioPersona = new Persona();
            cambioPersona.setId_persona(2);
            cambioPersona.setNombre("Karla ivonne");
            cambioPersona.setApellido("Gomez");
            cambioPersona.setEmail("kgomez@mail.com");
            cambioPersona.setTelefono("35364856");
            
            personaJdbc.update(cambioPersona);
            
            
            //en una operacion transaccional podemos hacer mas de 1 query para modificar la base de datos,
            //en este caso hicimos un update anterior y ahora un insert, todo en la misma operacion transaccional
            Persona nuevaPersona = new Persona();
            
            nuevaPersona.setNombre("Carlos");
            // (prueba sobrepasando el limite de caracteres)nuevaPersona.setApellido("Ramirez111111111111111111111111111111111111111111");
            nuevaPersona.setApellido("Ramirez");
            
            personaJdbc.insert(nuevaPersona);
            
            conexion.commit();
            System.out.println("Se ha hecho un commit de la transaccion");
            

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("entramos al rollback");
        try {
            //aca hacemos la llamada al rollback si es que hbubo un problema en la ejecucion, el mismo se envuelve en otro
            //metodo try catch
            conexion.rollback();
        } catch (SQLException ex1) {
            ex1.printStackTrace(System.out);
        }
        }
    }
}
