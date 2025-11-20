package Gestores;

import Persistencia.GestorPersistencia;
import modelado.HistoriaClinica.Tratamiento;
import modelado.Mascotas.Mascota;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import modelado.HistoriaClinica.Turno;
import modelado.HistoriaClinica.HorarioTurno; // <-- NUEVA IMPORTACIÓN

import java.util.ArrayList;
import java.util.List;

public class GestorTurnos {

    private final GestorPersistencia gp;
    private final GestorClientes gestorClientes;
    private final GestorVeterinarios gestorVeterinarios;

    private List<Turno> listaTurnos;
    private static final int MAX_TURNOS_POR_DIA = 3; // Límite por día


    public GestorTurnos(GestorPersistencia gp, GestorClientes gc, GestorVeterinarios gv) {
        this.gp = gp;
        this.gestorClientes = gc;
        this.gestorVeterinarios = gv;


        try {
            this.listaTurnos = gp.cargarTurnos();
        } catch (Exception e) {
            System.err.println("Error al cargar turnos iniciales. Iniciando con lista vacía.");
            this.listaTurnos = new ArrayList<>();
        }
    }

    private boolean tieneCupoDisponible(Veterinario vet, String fecha) {
        int turnosHoy = 0;

        for (Turno t : listaTurnos) {
            // Se compara la referencia del objeto Veterinario y la fecha
            if (t.getVeterinario().equals(vet) && t.getFecha().equals(fecha)) {
                turnosHoy++;
            }
        }
        return turnosHoy < MAX_TURNOS_POR_DIA;
    }


    private boolean horarioEstaOcupado(Veterinario vet, String fecha, HorarioTurno horario) {
        for (Turno t : listaTurnos) {
            // Se compara la referencia del objeto Veterinario, la fecha Y el Horario
            if (t.getVeterinario().equals(vet) && t.getFecha().equals(fecha) && t.getHorario().equals(horario)) {
                return true; // Horario ocupado
            }
        }
        return false;
    }

    public String solicitarTurno(Cliente cliente, Mascota mascota, Veterinario veterinario, String fechaDeseada, HorarioTurno horario) {
        System.out.println("\n# SOLICITUD DE TURNO");

        // --- VALIDACIONES ---
        if (cliente == null || mascota == null || veterinario == null || fechaDeseada.isEmpty() || horario == null) {
            return "Error: Faltan datos críticos para generar el turno (cliente, mascota, veterinario, fecha u horario).";
        }

        if (!tieneCupoDisponible(veterinario, fechaDeseada)) {
            return "El Dr./a " + veterinario.getNombre() + " ya tiene el cupo máximo de " + MAX_TURNOS_POR_DIA + " turnos para la fecha " + fechaDeseada + ".";
        }

        if (horarioEstaOcupado(veterinario, fechaDeseada, horario)) {
            return "El Dr./a " + veterinario.getNombre() + " ya tiene un turno reservado para la fecha " + fechaDeseada + " a las " + horario.toString() + ".";
        }

        int nuevoIdTurno = this.listaTurnos.size() + 1;

        Turno nuevoTurno = new Turno(
                mascota,
                "Motivo a determinar",
                new ArrayList<Tratamiento>(),
                fechaDeseada,
                nuevoIdTurno,
                veterinario,
                horario
        );
        this.listaTurnos.add(nuevoTurno);

        veterinario.setTurnoVeterinario(nuevoTurno);
        mascota.getHistoriaClinica().agregarTurno(nuevoTurno);

        try {
            this.gp.guardarTurnos(this.listaTurnos);
            return "Turno solicitado y guardado. ID: " + nuevoIdTurno + " para el " + fechaDeseada + " a las " + horario.toString();

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