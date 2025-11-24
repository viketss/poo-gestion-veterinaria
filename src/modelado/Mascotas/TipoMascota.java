package modelado.Mascotas;
// enum de especies de mascotas
public enum TipoMascota {
    PERRO("Perro"),
    GATO("Gato"),
    PAJARO("Pájaro"),
    ROEDOR("Roedor"),
    REPTIL("Reptil"),
    OTRO("Otro");

    private final String nombre;

    // CONSTRUCTOR
    TipoMascota(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
