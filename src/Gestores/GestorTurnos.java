package Gestores;

import Persistencia.GestorPersistencia;
import modelado.HistoriaClinica.Tratamiento;
import modelado.Mascotas.Mascota;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import modelado.HistoriaClinica.Turno;
import modelado.HistoriaClinica.HorarioTurno;
import modelado.HistoriaClinica.EstadoTurno;

import java.util.ArrayList;
import java.util.List;

public class GestorTurnos {

    private final GestorPersistencia gp;
    private final GestorClientes gestorClientes;
    private final GestorVeterinarios gestorVeterinarios;
    private final GestorMedicamentos gestorMedicamentos;

    private List<Turno> listaTurnos;
    private static final int MAX_TURNOS_POR_DIA = 3;

    public GestorTurnos(GestorPersistencia gp, GestorClientes gc, GestorVeterinarios gv, GestorMedicamentos gm) {
        this.gp = gp;
        this.gestorClientes = gc;
        this.gestorVeterinarios = gv;
        this.gestorMedicamentos = gm;

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
            if (t.getVeterinario().equals(vet) && t.getFecha().equals(fecha)) {
                // Solo contamos turnos PENDIENTES, asumiendo que un turno PAGADO/CANCELADO ya no cuenta como cupo
                if (t.getEstadoTurno() == EstadoTurno.PENDIENTE) {
                    turnosHoy++;
                }
            }
        }
        return turnosHoy < MAX_TURNOS_POR_DIA;
    }

    private boolean horarioEstaOcupado(Veterinario vet, String fecha, HorarioTurno horario) {
        for (Turno t : listaTurnos) {
            if (t.getVeterinario().equals(vet) && t.getFecha().equals(fecha) && t.getHorario().equals(horario) && t.getEstadoTurno() == EstadoTurno.PENDIENTE) {
                return true;
            }
        }
        return false;
    }
    public List<modelado.HistoriaClinica.Turno> getTurnosPendientesPorCliente(modelado.Personas.Cliente cliente) {
        List<modelado.HistoriaClinica.Turno> pendientes = new ArrayList<>();

        if (listaTurnos == null) {
            return pendientes; // Lista vacía si no hay turnos
        }

        for (modelado.HistoriaClinica.Turno t : listaTurnos) {
            // Verifica si el turno pertenece al cliente y si su estado es PENDIENTE
            if (t.getMascota().getDueno().getDni() == cliente.getDni() &&
                    t.getEstadoTurno() == modelado.HistoriaClinica.EstadoTurno.PENDIENTE) {

                pendientes.add(t);
            }
        }
        return pendientes;
    }

    public String solicitarTurno(Cliente cliente, Mascota mascota, Veterinario veterinario, String fechaDeseada, HorarioTurno horario) {
        System.out.println("\n# SOLICITUD DE TURNO");

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
                horario,
                20000,
                EstadoTurno.PENDIENTE
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

    public float simularAtencionYAsignarCosto(Turno turno) {

        int costoBaseConsulta = turno.getCostoConsulta();

        Tratamiento tratamientoAsignado = gestorMedicamentos.simularAsignacionTratamiento(
                "Diagnóstico y tratamiento simulado.",
                0.0f
        );

        turno.getTratamientos().add(tratamientoAsignado);

        float costoTotal = turno.calcularCostoTurno(costoBaseConsulta, tratamientoAsignado);

        System.out.println("LOG: Atención simulada. Turno ID: " + turno.getIdTurno() + " Costo Total: " + costoTotal);

        try {
            this.gp.guardarTurnos(this.listaTurnos);
        } catch (Exception e) {
            System.err.println("Error al guardar turno con tratamiento: " + e.getMessage());
        }

        return costoTotal;
    }

    /**
     * Marca un turno como PAGADO, actualiza su estado en la lista persistida
     * y limpia la agenda del veterinario (setea turnoVeterinario en null).
     * @param turno El objeto Turno a finalizar.
     */
    public void finalizarYMarcarComoPagado(Turno turno) {
        if (turno == null) {
            System.err.println("ERROR: No se puede finalizar un turno nulo.");
            return;
        }

        turno.setEstadoTurno(EstadoTurno.PAGADO);

        Veterinario veterinario = turno.getVeterinario();

        if (veterinario != null) {
            // Elimina el turno de la agenda del veterinario (libera el cupo)
            veterinario.setTurnoVeterinario(null);
            System.out.println("LOG: Turno ID " + turno.getIdTurno() + " marcado como PAGADO y cupo liberado para " + veterinario.getNombre());
        }

        try {
            this.gp.guardarTurnos(this.listaTurnos);
        } catch (Exception e) {
            System.err.println("ERROR al persistir los turnos después de marcar como pagado: " + e.getMessage());
        }
    }

    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void confirmarTurno() {
        System.out.println("Turno confirmado");
    }

    public void cancelarTurno() {
        System.out.println("Turno cancelado");
    }
}