public class Veterinario extends Persona { // herencia de Persona
    // atributos
    private String especialidad;
    private float sueldo;

    // constructor
    public Veterinario(String nombre, String apellido, long dni, String especialidad, float sueldo) {
        super(nombre, apellido, dni); // llamar al constructor de la clase padre
        this.especialidad = especialidad;
        this.sueldo = sueldo;
    }


}
