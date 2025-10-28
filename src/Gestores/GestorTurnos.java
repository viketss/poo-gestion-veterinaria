package Gestores;

import modelado.HistoriaClinica.Tratamiento;
import modelado.Mascotas.Mascota;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import modelado.HistoriaClinica.Turno;
import Persistencia.GestorPersistencia;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException; // Importación necesaria para manejar la excepción de persistencia

public class GestorTurnos {

    // Necesitamos referencias a otros gestores para buscar y persistir
    private final GestorPersistencia gestorPersistencia;
    private final GestorClientes gestorClientes;
    private final GestorVeterinarios gestorVeterinarios;

    private List<Turno> listaTurnos;

    // Constructor
    public GestorTurnos(GestorPersistencia gp, GestorClientes gc, GestorVeterinarios gv) {
        this.gestorPersistencia = gp;
        this.gestorClientes = gc;
        this.gestorVeterinarios = gv;
        this.listaTurnos = new ArrayList<>();
    }

    private boolean verificarDisponibilidad(Veterinario veterinario, String fecha) {
        System.out.println("Verificando agenda de " + veterinario.getNombre() + " para la fecha " + fecha + "...");
        return true; // Asumimos que siempre está disponible para continuar con el flujo
        // return false
    }

    public String solicitarTurno(String nombreCliente, String nombreMascota, String nombreVeterinario, String fechaDeseada) {
        System.out.println("GESTION TURNOS: Solicitud recibida.");

        // OBTENER Y VALIDAR AL CLIENTE
        // Delegamos la búsqueda del objeto Cliente completo al GestorClientes.
        Cliente cliente = gestorClientes.buscarCliente(nombreCliente);
        if (cliente == null) {
            return "El cliente no es parte del sistema.";
        }

        // 2. OBTENER Y VALIDAR AL VETERINARIO
        // Delegamos la búsqueda del objeto Veterinario completo al GestorVeterinarios.
        Veterinario veterinario = gestorVeterinarios.buscarVeterinarioPorNombre(nombreVeterinario);
        if (veterinario == null) {
            return "El veterinario no existe";
        }

        // 3. OBTENER Y VALIDAR LA MASCOTA
        // La Mascota pertenece al Cliente, por lo que el objeto Cliente la busca internamente.
        Mascota mascota = cliente.buscarMascota(nombreMascota);
        if (mascota == null) {
            return "La mascota no es del cliente";
        }
        System.out.println("Datos conseguidos");
        System.out.println("   -----   ");

        if (!verificarDisponibilidad(veterinario, fechaDeseada)){ //Se verifica la disponibilidad
            return "Turno no disponible";
        }

        int nuevoIdTurno = this.listaTurnos.size() + 1;

        // Instanciar el objeto Turno
        Turno nuevoTurno = new Turno(
                mascota,
                "Motivo a determinar",
                // CORRECCIÓN CLAVE: Especificar que la lista es de Tratamiento
                new ArrayList<Tratamiento>(),
                fechaDeseada,
                nuevoIdTurno,
                veterinario
        );
        this.listaTurnos.add(nuevoTurno);
        veterinario.setTurnoVeterinario(nuevoTurno);
        mascota.getHistoriaClinica().agregarTurno(nuevoTurno);

     /*   try {
            gestorPersistencia.guardarTurnos();
            // 2. Notificación de éxito
            return "Turno solicitado y guardado. ID: " + nuevoIdTurno + " para el " + fechaDeseada;

        } catch (IOException e) {
            // 3. Manejo de la excepción de persistencia (si no se pudo escribir en el archivo)
            // Es un error grave, pero la operación de negocio SÍ se completó en memoria.
            System.err.println("ERROR");
        }
        El gestor de persistencia guardaria el nuevo Turno
      */
        return "El turno fue solicitado con exito";
    }
}