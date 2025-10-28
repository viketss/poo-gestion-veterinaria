import Persistencia.GestorPersistencia;
import modelado.Mascotas.Mascota;
import modelado.Mascotas.TipoMascota;
import modelado.Personas.Cliente;

public class Main {
    // instanciar los gestores
    private static final GestorPersistencia PERSISTENCIA = new GestorPersistencia();
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Tomas Flory", "Gonzalez", 40456789, null, 1122334455L); // TODO falta agregr lista de mascotas
        Mascota nuevaMascota = new Mascota("Aras", "Callejero", TipoMascota.PERRO, null, true, 7);
        nuevaMascota.setVacunado(false); // prueba
    }
}
