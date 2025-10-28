package modelado.HistoriaClinica;

import java.util.ArrayList;
import java.util.List;

public class HistoriaClinica {
    // atributos
    private List<Turno> historialDeTurnos; // cardinalidad 1..n
    private List<Tratamiento> historialDeTratamientos;
    private int id;

    // constructor
    public HistoriaClinica(int id) {
        this.id = id;
        this.historialDeTurnos = new ArrayList<>(); // composicion
        this.historialDeTratamientos = new ArrayList<>();
    }

    //getters y setters
    public List<Turno> getHistorialDeTurnos() {
        return historialDeTurnos;
    }
    public void setHistorialDeTurnos(List<Turno> historialDeTurnos) {
        this.historialDeTurnos = historialDeTurnos;
    }
    public List<Tratamiento> getHistorialDeTratamientos() {
        return historialDeTratamientos;
    }
    public void setHistorialDeTratamientos(List<Tratamiento> historialDeTratamientos) {
        this.historialDeTratamientos = historialDeTratamientos;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // metodos
    public void generarReporte() {
        System.out.println("Mi reporte del animal es...");
    }
    
    public void agregarTurno(Turno turno) {
        historialDeTurnos.add(turno);
        System.out.println("Turno registrado con exito.\n");
    }

    public void agregarTratamiento(Tratamiento tratamiento) {
        historialDeTratamientos.add(tratamiento);
        System.out.println("Tratamiento registrado");
    }

    // tostring
    @Override
    public String toString() {
        return "# HistoriaClinica de la mascota: " + 
        "\n- ID de la historia clinica: " + id +
        "\n- Historial de turnos: " + historialDeTurnos +
        "\n- Historial de tratamientos: " + historialDeTratamientos; 
    }
} 
