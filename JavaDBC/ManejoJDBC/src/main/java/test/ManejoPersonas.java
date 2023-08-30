package test;

import datos.PersonaJDBC;
import domain.Persona;
import java.util.List;

public class ManejoPersonas {
    public static void main(String[] args) {
        
        PersonaJDBC personaJDBC = new PersonaJDBC();
        //ejecutamos el query select que definimos en la clase personaJDBC y las guardamos en una variable
        //de tipo lista llamada personas
        List<Persona> personas = personaJDBC.select();
        
        //recorremos los objetos de la lista PERSONAS con el for
        for(Persona persona: personas){
            System.out.println("persona: " + persona);
        }
        
        //en esta parte del codigo probamos el metodo INSERT de persona para insertar a maria lara
//        Persona persona = new Persona();
//        persona.setNombre("Maria");
//        persona.setApellido("Lara");
//        persona.setEmail("mlara@mail.com");
//        persona.setTelefono("36723512");
        
       // personaJDBC.insert(persona);
       
       //aca probamos nuestro metodo update definido en personaJDBC, ponemos todos los datos de la persona que vamos a modificar
       //siendo solo el nombre el dato que vamos a cambiar, los demas campos permanecen igual
//      Persona persona = new Persona();
//      persona.setId_persona(3);
//      persona.setNombre("Mayra");
//      persona.setApellido("Lara");
//      persona.setEmail("mlara@mail.com");
//      persona.setTelefono("36723512");
      
      
      //una vez guardado los datos damos la sentencia update
//      personaJDBC.update(persona);

    //aca probamos el metodo delete, eliminando la ultima entrada en la base de datos (mayra)
    
    Persona persona = new Persona();
    persona.setId_persona(3);
    
     //solo con 1 parametro es suficiente para eliminar un registro, en este caso el id persona
    personaJDBC.delete(persona);
      
    }
}
