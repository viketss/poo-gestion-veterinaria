package Gestores;

import Persistencia.GestorPersistencia;
import modelado.HistoriaClinica.Tratamiento;
import modelado.Mascotas.Mascota;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import modelado.HistoriaClinica.Turno;
//import Persistencia.GestorPersistencia;

import java.util.ArrayList;
import java.util.List;

public class GestorTurnos {
    
    private final GestorPersistencia gp;
    private final GestorClientes gestorClientes;
    private final GestorVeterinarios gestorVeterinarios;

    private List<Turno> listaTurnos;

    // Constructor
    public GestorTurnos(GestorPersistencia gp, GestorClientes gc, GestorVeterinarios gv) {
        this.gp = gp;
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

    public String solicitarTurno(Cliente cliente, Mascota mascota, Veterinario veterinario, String fechaDeseada) {
        System.out.println("\n# SOLICITUD DE TURNO");

        // --- 1. VALIDACIÓN MÍNIMA ---
        if (cliente == null || mascota == null || veterinario == null) {
            return "Error interno: Faltan datos del cliente, mascota o veterinario.";
        }

        System.out.println("# Datos del cliente: " + cliente.getNombre() +
                ", Mascota: " + mascota.getNombre() +
                ", Veterinario: " + veterinario.getNombre());

        if (!verificarDisponibilidad(veterinario, fechaDeseada)){
            return "Turno no disponible";
        }

        int nuevoIdTurno = this.listaTurnos.size() + 1;

        Turno nuevoTurno = new Turno(
                mascota,
                "Motivo a determinar", // Puedes actualizar esto si la GUI añade un campo
                new ArrayList<Tratamiento>(),
                fechaDeseada,
                nuevoIdTurno,
                veterinario
        );

        // 3. Actualizar el estado del sistema
        this.listaTurnos.add(nuevoTurno);
        veterinario.setTurnoVeterinario(nuevoTurno); // Asumo que el veterinario solo puede tener 1 turno a la vez
        mascota.getHistoriaClinica().agregarTurno(nuevoTurno);

        // --- 4. PERSISTENCIA FINAL (Con el objeto 'this.gp' inyectado) ---
        try {
            // Necesitas que la clase Turno esté importada correctamente y que tengas una listaTurnos
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