package Gestores;

import Persistencia.GestorPersistencia;
import modelado.HistoriaClinica.Tratamiento;
import modelado.Mascotas.Mascota;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import modelado.HistoriaClinica.Turno;

import java.util.ArrayList;
import java.util.List;

public class GestorTurnos {

    private final GestorPersistencia gp;

    private List<Turno> listaTurnos;

    // Constructor que recibe TRES gestores
    public GestorTurnos(GestorPersistencia gp) {
        this.gp = gp;

        try {
            this.listaTurnos = gp.cargarTurnos();
        } catch (Exception e) {
            System.err.println("Error al cargar turnos iniciales. Iniciando con lista vacía.");
            this.listaTurnos = new ArrayList<>();
        }
    }

    // metodos
    private boolean verificarDisponibilidad(Veterinario veterinario, String fecha) {
        System.out.println("Verificando agenda de " + veterinario.getNombre() + " para la fecha seleccionada.");
        // Lógica real de verificación iría aquí.
        return true;
    }

    public String solicitarTurno(Cliente cliente, Mascota mascota, Veterinario veterinario, String fechaDeseada) {
        System.out.println("\n# SOLICITUD DE TURNO");

        // --- VALIDACIÓN DE OBJETOS ---
        if (cliente == null || mascota == null || veterinario == null) {
            // Esto solo ocurriría por un error de lógica grave en el Main o la GUI.
            return "Error interno: Faltan datos críticos para generar el turno.";
        }

        System.out.println("# Cliente: " + cliente.getNombre() + ", Mascota: " + mascota.getNombre() + ", Veterinario: " + veterinario.getNombre());

        if (!verificarDisponibilidad(veterinario, fechaDeseada)){
            return "Turno no disponible para esa fecha.";
        }

        int nuevoIdTurno = this.listaTurnos.size() + 1;

        // 1. Crear el objeto Turno
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

        try {
            this.gp.guardarTurnos(this.listaTurnos);
            return "Turno solicitado y guardado. ID: " + nuevoIdTurno + " para el " + fechaDeseada;

        } catch (Exception e) {
            System.err.println("ERROR al guardar turnos en el archivo: " + e.getMessage());
            return "Turno solicitado en memoria, pero falló la persistencia en archivo.";
        }
    }

    public void confirmarTurno() {
        System.out.println("Turno confirmado");
    }

    public void cancelarTurno() {
        System.out.println("Turno cancelado");
    }

}