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
        super(nombre, apellido, dni);
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
        System.out.println("Agregando mascota: " + mascota.getNombre());
        mascotas.add(mascota);
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
        return "Cliente: \n- Nombre: " + getNombre() +
                "\n- Apellido: " + getApellido() +
                "\n- DNI: " + getDni() +
                "\n- Mascotas: " + mascotas +
                "\n- Teléfono: " + telefono +
                "\n";
    }
}
