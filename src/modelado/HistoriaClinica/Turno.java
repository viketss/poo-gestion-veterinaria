package modelado.HistoriaClinica;
import java.util.ArrayList;
import java.util.List;

import modelado.Mascotas.Mascota;
import modelado.Personas.Veterinario;

public class Turno {
    // atributos
    private Mascota mascota;
    private String motivoDeConsulta;
    private List<Tratamiento> tratamientos;
    private String fecha;
    private int idTurno;
    private Veterinario veterinario;

    // constructor
    public Turno(Mascota mascota, String motivoDeConsulta, List<Tratamiento> tratamientos, String fecha, int idTurno, Veterinario veterinario) {
        this.mascota = mascota;
        this.motivoDeConsulta = motivoDeConsulta;
        this.tratamientos = new ArrayList<>();
        this.fecha = fecha;
        this.idTurno = idTurno;
        this.veterinario = veterinario;
    }

    // getters y setters
    public Mascota getMascota() {
        return mascota;
    }
    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
    public String getMotivoDeConsulta() {
        return motivoDeConsulta;
    }
    public void setMotivoDeConsulta(String motivoDeConsulta) {
        this.motivoDeConsulta = motivoDeConsulta;
    }
    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }
    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public int getIdTurno() {
        return idTurno;
    }
    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }
    public Veterinario getVeterinario() {
        return veterinario;
    }
    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    // metodos:
    public void confirmarTurno() {
        
    }

    public void cancelarTurno() {
        
    }
    
    // tostring
    @Override
    public String toString() {
        return "Turno [mascota=" + mascota + ", motivoDeConsulta=" + motivoDeConsulta + ", tratamientos=" + tratamientos
                + ", fecha=" + fecha + ", idTurno=" + idTurno + ", veterinario=" + veterinario + "]";
    }
}
