package ejemplo.jdbc;

import java.sql.*;

public class IntroduccionJDBC {
    public static void main(String[] args) {
        
        //paso1: creamos nuestra cadena de conexion a mysql
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC";
        
        try {
            //paso 2. Creamos el objeto de conexxion a la base de datos
            Connection conexion = DriverManager.getConnection(url,"root", "admin");
            //paso3. Creamos un objeto de tipo statement
            Statement instruccion = conexion.createStatement();
            //paso4. creamos el query (consulta q le hacemos a labase de datos)
            String sql = "select id_personas, nombre, apellido, email, telefono from personas";
            //paso5. ejecucion del query con la variable resultset
            ResultSet resultado = instruccion.executeQuery(sql);
            //paso 6. procesamos el resultado
            
            while(resultado.next()){
                System.out.print("id persona: " + resultado.getInt(1));
                System.out.print(" nombre: " + resultado.getString(2));
                System.out.print(" apellido: " + resultado.getString(3));
                System.out.print(" email: " + resultado.getString(4));
                System.out.println(" telefono: " + resultado.getString(5));
            }
            //cerramos cada objeto que hemos utilizado (conexiones a la base de datos)
            resultado.close();
            instruccion.close();
            conexion.close();
            
        } catch (SQLException ex) {
           ex.printStackTrace(System.out);
        }
    }
}
