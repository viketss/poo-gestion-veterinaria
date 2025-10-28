package modelado.Personas;
import modelado.HistoriaClinica.Tratamiento;
import modelado.HistoriaClinica.Turno;
import modelado.Mascotas.Mascota;

public class Veterinario extends Persona { // herencia de Persona
    // atributos
    private String especialidad;
    private float sueldo;
    private Turno turnoVeterinario; // 1 veterinario .. 1 turno - asociacion

    // constructor
    public Veterinario(String nombre, String apellido, long dni, String especialidad, float sueldo, Turno turnoVeterinario) {
        super(nombre, apellido, dni); // llamar al constructor de la clase padre
        this.especialidad = especialidad;
        this.sueldo = sueldo;
        this.turnoVeterinario = null;
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
    public Turno getTurnoVeterinario() {
        return turnoVeterinario;
    }
    public void setTurnoVeterinario(Turno turnoVeterinario) {
        this.turnoVeterinario = turnoVeterinario;
    }



    public void aplicarTratamiento(Tratamiento tratamiento, Mascota mascota) {
        System.out.println("El veterinario aplico el tratamiento:" + tratamiento+ " a la mascota" + mascota);
    }   //Funcion simple que imprime el tratamiento

    // tostring
    @Override
    public String toString() {
        return "Veterinario: \n- Especialidad: " + especialidad + "\n- Sueldo: " + sueldo + "\n- Turno: "
                + turnoVeterinario + "\n";
    }
}
