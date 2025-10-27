import modelado.Mascotas.Mascota;
import modelado.Mascotas.TipoMascota;

public class Main {
    public static void main(String[] args) {
        Mascota nuevaMascota = new Mascota("Aras", "Callejero", TipoMascota.PERRO, null, true, 7);
        nuevaMascota.setVacunado(false); // prueba
    }
}
