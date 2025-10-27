package modelado.HistoriaClinica;
import modelado.Mascotas.Mascota;
import modelado.Personas.Veterinario;

public class Turno {
    // atributos
    private Mascota mascota;
    private String motivoDeConsulta;
    private Tratamiento tratamiento;
    private String fecha;
    private int idTurno;
    private Veterinario veterinario;

    // constructor
    public Turno(Mascota mascota, String motivoDeConsulta, Tratamiento tratamiento, String fecha, int idTurno, Veterinario veterinario) {
        this.mascota = mascota;
        this.motivoDeConsulta = motivoDeConsulta;
        this.tratamiento = tratamiento;
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
    public Tratamiento getTratamiento() {
        return tratamiento;
    }
    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
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
    
}
