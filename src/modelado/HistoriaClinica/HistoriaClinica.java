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

    }
    
    public void agregarTurno(Turno turno) {

    }

    public void agregarTratamiento(Tratamiento tratamiento) {

    }
} 
