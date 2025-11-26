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
    public void generarHistoriaClinica(Mascota mascota) {
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

            // 3. crear el turno
            // null para lo que no se incluye (veterinario, horario)
            Turno turnoSimulado = new Turno(
                    mascota,
                    tipoSeleccionado.toString(), //nombre del tratamiento
                    listaTratamientos,
                    fechaStr,
                    0,
                    null,
                    null,
                    0,
                    EstadoTurno.PAGADO
            );
            // 4. guardar
            mascota.getHistoriaClinica().agregarTurno(turnoSimulado);
        }
        System.out.println("Historia clínica de " + mascota.getNombre() + " creada con éxito.");
    }
}
