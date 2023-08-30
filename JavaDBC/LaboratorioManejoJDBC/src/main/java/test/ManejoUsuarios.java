package test;

import datos.UsuarioJDBC;
import domain.Usuario;
import java.util.List;

public class ManejoUsuarios {
    public static void main(String[] args) {
        UsuarioJDBC usuarioJDBC = new UsuarioJDBC();
        
        List<Usuario>usuarios = usuarioJDBC.select();
        
           for(Usuario usuario : usuarios){
            System.out.println("usuario: " + usuario);
        }
    }
}
