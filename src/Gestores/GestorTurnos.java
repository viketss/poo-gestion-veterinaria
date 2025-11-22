package Gestores;

import Persistencia.GestorPersistencia;
import modelado.HistoriaClinica.Tratamiento;
import modelado.Mascotas.Mascota;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import modelado.HistoriaClinica.Turno;
import modelado.HistoriaClinica.HorarioTurno;

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
                turnosHoy++;
            }
        }
        return turnosHoy < MAX_TURNOS_POR_DIA;
    }

    private boolean horarioEstaOcupado(Veterinario vet, String fecha, HorarioTurno horario) {
        for (Turno t : listaTurnos) {
            if (t.getVeterinario().equals(vet) && t.getFecha().equals(fecha) && t.getHorario().equals(horario)) {
                return true;
            }
        }
        return false;
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
                20000
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

    public float simularAtencion(Turno turno) {

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

    public void confirmarTurno() {
        System.out.println("Turno confirmado");
    }

    public void cancelarTurno() {
        System.out.println("Turno cancelado");
    }

    public GestorPersistencia getGp() {
        return gp;
    }

    public GestorClientes getGestorClientes() {
        return gestorClientes;
    }

    public GestorVeterinarios getGestorVeterinarios() {
        return gestorVeterinarios;
    }

    public GestorMedicamentos getGestorMedicamentos() {
        return gestorMedicamentos;
    }

    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }
}