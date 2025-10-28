package modelado.Personas;

import java.util.ArrayList;
import java.util.List;
import modelado.Mascotas.Mascota;

public class Cliente extends Persona {
    // atributos:
    private List<Mascota> mascotas; // 1 cliente .. n mascotas - composicion
    private long telefono;

    // constructor
    public Cliente(String nombre, String apellido, long dni, List<Mascota> mascotas, long telefono) {
        super(nombre, apellido, dni); // llamar al constructor de la clase padre
        this.mascotas = new ArrayList<>();
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
        mascotas.add(mascota);  // agrega una mascota a la lista de mascotas
    }

    public void obtenerInfoDeContacto() {
        System.out.println(getTelefono());
    }

    public Mascota buscarMascota(String nombreMascota) {
        for (Mascota mascota : this.mascotas) {
            if (mascota.getNombre().equalsIgnoreCase(nombreMascota)) {
                System.out.println("Mascota encontrada.");
                return mascota; // Retorna el objeto Mascota completo
            }
        }
        System.out.println("Mascota no encontrada en esta lista.");
        return null; // Retorna null si no se encontró coincidencia
    }

    // tostring
    @Override
    public String toString() {
        return "Cliente [nombre=" + getNombre() +
                ", mascotas=" + mascotas +
                ", telefono=" + telefono +
                "]";
    }
}
