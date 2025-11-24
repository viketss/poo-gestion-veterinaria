package Gestores;

import modelado.HistoriaClinica.EstadoTurno;
import modelado.HistoriaClinica.TipoTratamiento;
import modelado.HistoriaClinica.Tratamiento;
import modelado.HistoriaClinica.Turno;
import modelado.Mascotas.Mascota;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GestorHistoriaClinica {
    // ATRIBUTOS
    private GestorMedicamentos gestorMedicamentos;
    private Random random;

    // CONSTRUCTOR
    public GestorHistoriaClinica(GestorMedicamentos gestorMed) {
        this.gestorMedicamentos = gestorMed;
        this.random = new Random();
    }

    // METODOS
    public void generarHistorial(Mascota mascota) {
        // validar si ya tiene historial
        if (!mascota.getHistoriaClinica().getHistorialDeTurnos().isEmpty()) {
            return;
        }

        // generar 4 a 6 registros (supuestamente administrados al sistema por el veterinario)
        int cantidadVisitas = random.nextInt(3) + 4;

        for (int i = 0; i < cantidadVisitas; i++) {
            // 1. dia (fecha de los turnos)
            LocalDate fechaRandom = LocalDate.now().minusDays(random.nextInt(700));
            String fechaStr = fechaRandom.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // formato de fecha

            // 2. tratamiento y medicamentos
            TipoTratamiento[] tipos = TipoTratamiento.values(); // los valores del enum de tratamiento
            TipoTratamiento tipoSeleccionado = tipos[random.nextInt(tipos.length)]; // elige tratamiento al azar

            // tratamiento con sus insumos (desde gestor de medicamentos)
            Tratamiento tratamientoSimulado = gestorMedicamentos.simularAsignacionTratamiento(
                    tipoSeleccionado.getDescripcion(),
                    0.0f
            );

            List<Tratamiento> listaTratamientos = new ArrayList<>();
            listaTratamientos.add(tratamientoSimulado);

            // --- C. CREAR EL REGISTRO (Turno) ---
            // Llenamos con NULL lo que no nos interesa (Veterinario, Horario)
            Turno turnoSimulado = new Turno(
                    mascota,
                    tipoSeleccionado.toString(), // Motivo = Nombre del tratamiento
                    listaTratamientos,
                    fechaStr,
                    0,
                    null,                   // Veterinario: NULL (No interesa)
                    null,                   // Horario: NULL (No interesa)
                    0,
                    EstadoTurno.PAGADO
            );
            // --- D. GUARDAR (ESTO FALTABA) ---
            // Importante: Usar el método que agrega el turno a la lista de la historia
            mascota.getHistoriaClinica().agregarTurno(turnoSimulado);
        }
    }
}
