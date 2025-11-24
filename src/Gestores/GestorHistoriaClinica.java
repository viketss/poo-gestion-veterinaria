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
    private GestorMedicamentos gestorMedicamentos;
    private Random random;

    // Constructor simplificado: Ya no pide GestorVeterinarios
    public GestorHistoriaClinica(GestorMedicamentos gestorMed) {
        this.gestorMedicamentos = gestorMed;
        this.random = new Random();
    }

    public void generarHistorialSimulado(Mascota mascota) {
        // 1. Validar si ya tiene historial
        if (!mascota.getHistoriaClinica().getHistorialDeTurnos().isEmpty()) {
            return;
        }

        // 2. Generar entre 1 y 4 registros
        int cantidadVisitas = random.nextInt(4) + 1;

        for (int i = 0; i < cantidadVisitas; i++) {
            // --- A. DÍA (FECHA) ---
            LocalDate fechaRandom = LocalDate.now().minusDays(random.nextInt(700));
            String fechaStr = fechaRandom.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // --- B. TRATAMIENTO Y MEDICAMENTOS ---
            TipoTratamiento[] tipos = TipoTratamiento.values();
            TipoTratamiento tipoSeleccionado = tipos[random.nextInt(tipos.length)]; // elige tratamiento al azar

            // Usamos el Gestor de Medicamentos para crear el tratamiento con sus insumos
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
