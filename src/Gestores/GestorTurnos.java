package Gestores;

import modelado.HistoriaClinica.Tratamiento;
import modelado.Mascotas.Mascota;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import modelado.HistoriaClinica.Turno;
import Persistencia.GestorPersistencia;

import java.util.ArrayList;
import java.util.List;

public class GestorTurnos {
    // TODO sacamos el gestor de persistencia de aca?
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

    // metodos
    private boolean verificarDisponibilidad(Veterinario veterinario, String fecha) {
        System.out.println("Verificando agenda de " + veterinario.getNombre() + " para la fecha seleccionada.");
        return true; // Asumimos que siempre está disponible para continuar con el flujo
        // return false
    }

    public String solicitarTurno(String nombreCliente, String nombreMascota, String nombreVeterinario, String fechaDeseada) {
        System.out.println("\n# SOLICITUD DE TURNO");
        System.out.println("Agregar datos para la solicitud de turno: ");

        // OBTENER Y VALIDAR AL CLIENTE
        Cliente cliente = gestorClientes.buscarCliente(nombreCliente);
        if (cliente == null) {
            return "El cliente no es parte del sistema.";
        }

        // OBTENER Y VALIDAR AL VETERINARIO
        Veterinario veterinario = gestorVeterinarios.buscarVeterinarioPorNombre(nombreVeterinario);
        if (veterinario == null) {
            return "El veterinario seleccionado no existe";
        }

        // OBTENER Y VALIDAR LA MASCOTA
        Mascota mascota = cliente.buscarMascota(nombreMascota);
        if (mascota == null) {
            return "La mascota no esta registrada a nombre del cliente";
        }
        System.out.println("# Datos del cliente: " + cliente.getNombre() + ", Mascota: " + mascota.getNombre() + ", Veterinario para la consulta: " + veterinario.getNombre());
    

        if (!verificarDisponibilidad(veterinario, fechaDeseada)){ //Se verifica la disponibilidad
            return "Turno no disponible";
        }

        int nuevoIdTurno = this.listaTurnos.size() + 1; // turno nuevo

        // Instanciar el objeto Turno
        Turno nuevoTurno = new Turno(
                mascota,
                "Motivo a determinar",
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