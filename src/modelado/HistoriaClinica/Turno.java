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
    private HorarioTurno horario;
    private int costoConsulta;
    private EstadoTurno estadoTurno;

    // constructor
    public Turno(Mascota mascota, String motivoDeConsulta, List<Tratamiento> tratamientos, String fecha, int idTurno, Veterinario veterinario,HorarioTurno horario, int costoConsulta, EstadoTurno estadoTurno) {
        this.mascota = mascota;
        this.motivoDeConsulta = motivoDeConsulta;
        this.tratamientos = new ArrayList<>();
        this.fecha = fecha;
        this.idTurno = idTurno;
        this.veterinario = veterinario;
        this.horario = horario;
        this.costoConsulta = costoConsulta;
        this.estadoTurno = estadoTurno;
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
    public HorarioTurno getHorario() {
        return horario;
    }
    public void setHorario(HorarioTurno horario) {
        this.horario = horario;
    }

    public int getCostoConsulta() {
        return costoConsulta;
    }

    public float calcularCostoTurno(int costoConsulta, Tratamiento tratamiento){
        float costoFinal = costoConsulta + tratamiento.calcularCostoMedicamento();
        return costoFinal;
    }

    public void setCostoConsulta(int costoConsulta) {
        this.costoConsulta = costoConsulta;
    }

    public EstadoTurno getEstadoTurno() {
        return estadoTurno;
    }

    public void setEstadoTurno(EstadoTurno estadoTurno) {
        this.estadoTurno = estadoTurno;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "mascota=" + mascota +
                ", motivoDeConsulta='" + motivoDeConsulta + '\'' +
                ", tratamientos=" + tratamientos +
                ", fecha='" + fecha + '\'' +
                ", idTurno=" + idTurno +
                ", veterinario=" + veterinario.getNombre() +
                ", horario=" + horario +
                ", costoConsulta=" + costoConsulta +
                '}';
    }
}
