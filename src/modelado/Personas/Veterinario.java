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

    // getters y setters
    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public float getSueldo() {
        return sueldo;
    }
    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }

    // metodos
    public void registrarTurno(Turno turno) {

    }

    public void aplicarTratamiento(Tratamiento tratamiento, Mascota mascota) {

    }   

}
