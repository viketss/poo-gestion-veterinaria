package Gestores;

import modelado.Personas.Cliente;
import modelado.Mascotas.Mascota;
import modelado.Mascotas.TipoMascota;
import Persistencia.GestorPersistencia;

public class GestorMascota {

    private final GestorClientes gestorClientes;
    private final GestorPersistencia gestorPersistencia;

    public GestorMascota(GestorClientes gestorClientes, GestorPersistencia gestorPersistencia) {
        this.gestorClientes = gestorClientes;
        this.gestorPersistencia = gestorPersistencia;
    }


    public Mascota agregarMascotaACliente(
            Cliente dueno,
            String nombre,
            String raza,
            TipoMascota especie,
            boolean vacunado,
            int edad) throws IllegalArgumentException {
        if (dueno == null) {
            throw new IllegalArgumentException("El cliente dueño no puede ser nulo.");
        }
        if (nombre.trim().isEmpty() || raza.trim().isEmpty() || edad < 0) {
            throw new IllegalArgumentException("Datos de mascota incompletos o inválidos.");
        }

        Mascota nuevaMascota = new Mascota(nombre, raza, especie, dueno, vacunado, edad);
        dueno.agregarMascota(nuevaMascota);

        this.gestorPersistencia.guardarClientes(this.gestorClientes.getListaClientes());
        System.out.println("✅ Mascota '" + nombre + "' agregada con éxito y datos persistidos.");
        return nuevaMascota;
    }
}