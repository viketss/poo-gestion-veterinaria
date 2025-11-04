import javax.swing.SwingUtilities;
import Gestores.GestorClientes;
import Gestores.GestorTurnos;
import Gestores.GestorVeterinarios;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import Vista.DialogoLogin;
import Vista.VentanaTurnos;
import java.util.ArrayList;

public class Main2 { // La clase se llama Main2

    // Instancias de los gestores
    public GestorClientes gestorClientes;
    public GestorTurnos gestorTurnos;
    public GestorVeterinarios gestorVeterinarios;

    // Referencia al cliente que ha ingresado exitosamente
    public static Cliente clienteActual = null;

    // Constructor de la clase Main2: Inicializa todos los datos
    public Main2() {
        // Inicialización de los gestores
        gestorClientes = new GestorClientes(new ArrayList<Cliente>());
        gestorVeterinarios = new GestorVeterinarios(new ArrayList<Veterinario>());
        gestorTurnos = new GestorTurnos(gestorClientes, gestorVeterinarios);

        // SIMULACIÓN DE DATOS MÍNIMOS
        // 1. Cliente de acceso (Nombre: Cliente, DNI: 12345678)
        Cliente clienteSimulado = new Cliente("Cliente", "Demo", 12345678, new ArrayList<>(), 1123456789);
        gestorClientes.agregarCliente(clienteSimulado);

        // 2. Veterinario para que haya a quién pedir turno
        Veterinario esteban = new Veterinario("Esteban","Hernandez",98765432, "Cirugia Canina",20000,null);
        gestorVeterinarios.agregarVeterinario(esteban);
    }

    public static void main(String[] args) {

        // 1. Instanciamos la clase Main2 para inicializar los gestores (la inyección de dependencias)
        Main2 app = new Main2();

        // 2. Lanzamiento de la GUI en el hilo correcto (EDT)
        SwingUtilities.invokeLater(() -> {
            // Pasamos el gestorClientes a la ventana de login
            DialogoLogin login = new DialogoLogin(null, app.gestorClientes);
            login.setVisible(true);

            // 3. Verificación después del cierre del login
            if (login.isAccesoExitoso()) {
                clienteActual = login.getClienteLogueado();

                // Abrimos VentanaTurnos, pasando TODAS las dependencias necesarias
                new VentanaTurnos(
                        clienteActual,
                        app.gestorTurnos,
                        app.gestorVeterinarios
                ).setVisible(true);
            } else {
                // Si el login se cancela, cerramos la aplicación.
                System.exit(0);
            }
        });
    }
}