package test;

import datos.Conexion;
import datos.UsuarioJDBC;
import domain.Usuario;
import java.sql.Connection;
import java.sql.SQLException;

public class ManejoUsuarios {

    public static void main(String[] args) {
        
        Connection conexion = null;

        try {
            conexion = Conexion.getConnection();

            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            
            UsuarioJDBC usuarioJdbc = new UsuarioJDBC(conexion);
            
            Usuario nuevoUsuario = new Usuario();
            
            nuevoUsuario.setUsername("Carlos");
            nuevoUsuario.setPassword("123456");
            
            usuarioJdbc.insert(nuevoUsuario);
            
            conexion.commit();
            
            System.out.println("se ha hecho un commit de la transaccion");
            

        } catch (SQLException ex) {
            try {
                ex.printStackTrace(System.out);
                System.out.println("se entro al rollback");
                
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }

    }
}
