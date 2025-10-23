public class Mascota {
    // atributos
    private String nombre;
    // tipoMascota
    private String raza;
    private Cliente dueno; // dueño de la mascota
    private boolean vacunado;
    private int edad;
    private HistoriaClinica historiaClinica;

    // constructor
    public Mascota(String nombre, String raza, Cliente dueno, boolean vacunado, int edad) {
        this.nombre = nombre;
        this.raza = raza;
        this.dueno = dueno;
        this.vacunado = vacunado;
        this.edad = edad;
        this.historiaClinica = new HistoriaClinica();
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

    // metodos
    public void mostrarFichaClinica() {
        
    }

    public void vacunar() {
        this.vacunado = true;
    }

}
