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
    // ATRIBUTOS
    private final GestorPersistencia gp;
    private final GestorClientes gestorClientes;
    private final GestorVeterinarios gestorVeterinarios;
    private final GestorMedicamentos gestorMedicamentos;

    private List<Turno> listaTurnos;
    private static final int MAX_TURNOS_POR_DIA = 3;

    // CONSTRUCTOR
    public GestorTurnos(GestorPersistencia gp, GestorClientes gc, GestorVeterinarios gv, GestorMedicamentos gm) {
        this.gp = gp;
        this.gestorClientes = gc;
        this.gestorVeterinarios = gv;
        this.gestorMedicamentos = gm;
        this.listaTurnos = cargarYReconstruirTurnos();
    }

    // METODOS
    private boolean tieneCupoDisponible(Veterinario vet, String fecha) {
        int turnosHoy = 0;

        for (Turno t : listaTurnos) {
            if (t.getVeterinario().equals(vet) && t.getFecha().equals(fecha)) {
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


    private List<Turno> cargarYReconstruirTurnos() {
        List<Turno> turnosCargados = new ArrayList<>();
        List<String> lineasTurnos = this.gp.cargarTurnos();

        for (String linea : lineasTurnos) {
            String[] partes = linea.split(";");

            if (partes.length == 7) {
                try {
                    int idTurno = Integer.parseInt(partes[0]);
                    String fecha = partes[1];
                    HorarioTurno horario = HorarioTurno.valueOf(partes[2]);
                    EstadoTurno estado = EstadoTurno.valueOf(partes[3]);
                    long dniDueno = Long.parseLong(partes[4]);
                    String nombreMascota = partes[5];
                    long dniVeterinario = Long.parseLong(partes[6]);

                    Veterinario veterinario = gestorVeterinarios.buscarVeterinarioPorDni(dniVeterinario);
                    Cliente dueno = gestorClientes.buscarClientePorDni(dniDueno);

                    Mascota mascota = null;
                    if (dueno != null) {
                        mascota = dueno.buscarMascota(nombreMascota);
                    }

                    if (veterinario == null || mascota == null) {
                        System.err.println("ERROR Persistencia: Turno ID " + idTurno + " no reconstruido. Dependencia perdida.");
                        continue;
                    }

                    Turno turno = new Turno(
                            mascota,
                            "Motivo desconocido (Persistencia)", // El motivo no se persiste
                            new ArrayList<Tratamiento>(),
                            fecha,
                            idTurno,
                            veterinario,
                            horario,
                            20000, // Costo fijo del turno
                            estado
                    );
                    turnosCargados.add(turno);

                    if (estado == EstadoTurno.PENDIENTE) {
                        veterinario.setTurnoVeterinario(turno);
                    }

                } catch (Exception e) {
                    System.err.println("Error al parsear línea de turno: " + linea + " Error: " + e.getMessage());
                }
            }
        }
        return turnosCargados;
    }

    public String solicitarTurno(Cliente cliente, Mascota mascota, Veterinario veterinario, String fechaDeseada, HorarioTurno horario) {
        System.out.println("\n# SOLICITUD DE TURNO");

        if (cliente == null || mascota == null || veterinario == null || fechaDeseada.isEmpty() || horario == null) {
            return "Error: Faltan datos críticos para generar el turno.";
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
                EstadoTurno.PENDIENTE // NUEVOS TURNOS siempre inician PENDIENTES
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

    public void finalizarYMarcarComoPagado(Turno turno) {
        if (turno == null) {
            System.err.println("ERROR: No se puede finalizar un turno nulo.");
            return;
        }

        turno.setEstadoTurno(EstadoTurno.PAGADO);

        Veterinario veterinario = turno.getVeterinario();

        if (veterinario != null) {
            veterinario.setTurnoVeterinario(null);
            System.out.println("LOG: Turno ID " + turno.getIdTurno() + " marcado como PAGADO y cupo liberado para " + veterinario.getNombre());
        }

        try {
            this.gp.guardarTurnos(this.listaTurnos);
        } catch (Exception e) {
            System.err.println("ERROR al persistir los turnos después de marcar como pagado: " + e.getMessage());
        }
    }

    public List<Turno> getTurnosPendientesPorCliente(Cliente cliente) {
        List<Turno> pendientes = new ArrayList<>();

        if (listaTurnos == null) {
            return pendientes;
        }

        for (Turno t : listaTurnos) {
            if (t.getMascota().getDueno().getDni() == cliente.getDni() &&
                    t.getEstadoTurno() == EstadoTurno.PENDIENTE) {

                pendientes.add(t);
            }
        }
        return pendientes;
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