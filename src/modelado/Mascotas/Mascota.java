package modelado.Mascotas;
import modelado.HistoriaClinica.HistoriaClinica;
import modelado.Personas.Cliente;

public class Mascota {
    // atributos
    private String nombre;
    private TipoMascota especie; // especie de la mascota
    private String raza;
    private Cliente dueno; // dueño de la mascota
    private boolean vacunado;
    private int edad;
    private HistoriaClinica historiaClinica;// cardinalidad 1..1

    // constructor
    public Mascota(String nombre, String raza, TipoMascota especie, Cliente dueno, boolean vacunado, int edad) {
        this.nombre = nombre;
        this.raza = raza;
        this.especie = especie;
        this.dueno = dueno;
        this.vacunado = vacunado;
        this.edad = edad;
        this.historiaClinica = new HistoriaClinica(0000);
    }

    // getters y setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getRaza() {
        return raza;
    }
    public void setRaza(String raza) {
        this.raza = raza;
    }
    public TipoMascota getEspecie() {
        return especie;
    }
    public void setEspecie(TipoMascota especie) {
        this.especie = especie;
    }
    public Cliente getDueno() {
        return dueno;
    }
    public void setDueno(Cliente dueno) {
        this.dueno = dueno;
    }
    public boolean isVacunado() {
        return vacunado;
    }
    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }
    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    // metodos
    public void mostrarFichaClinica() {
        System.out.println(getHistoriaClinica());
    }

    public void vacunar(Mascota mascota) {
        this.vacunado = true;
        System.out.println(mascota.getNombre() +" fue vacunado.");
    }

    // tostring
    @Override
    public String toString() {
        return "Nombre: " + nombre + 
                " - Especie: " + especie + 
                " - Raza: " + raza +
                " - Dueño: " + dueno.getNombre() + " " + dueno.getApellido() +
                " - Vacunado: " + (vacunado ? "Sí" : "No") +
                " - Edad: " + edad +
                " - ID historia clinica: " + historiaClinica.getId();
    }
}
