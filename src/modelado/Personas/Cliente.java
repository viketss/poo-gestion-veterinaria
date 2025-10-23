import java.util.List;

public class Cliente extends Persona {
    // atributos:
    private List<Mascota> mascotas;
    private long telefono;

    // constructor
    public Cliente(String nombre, String apellido, long dni, List<Mascota> mascotas, long telefono) {
        super(nombre, apellido, dni); // llamar al constructor de la clase padre
        this.mascotas = mascotas;
        this.telefono = telefono;
    }

    // getters y setters
    public List<Mascota> getMascotas() {
        return mascotas;
    }
    public long getTelefono() {
        return telefono;
    }
    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    // metodos
    public void agregarMascota(Mascota mascota) {

    }

    public void obtenerInfoDeContacto() {

    }

    public void solicitarTurno(Turno turno) {

    }

}
