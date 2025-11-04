import javax.swing.SwingUtilities;

import Gestores.GestorClientes;
import Gestores.GestorTurnos;
import Gestores.GestorVeterinarios;
import Persistencia.GestorPersistencia;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import Vista.DialogoLogin;
import Vista.VentanaTurnos;

import java.util.ArrayList;
import java.util.List;

public class Main2 {

    // Instancias de los gestores (Ya no es necesario que sean públicas, pero las mantenemos)
    public GestorClientes gestorClientes;
    public GestorTurnos gestorTurnos;
    public GestorVeterinarios gestorVeterinarios;
    public GestorPersistencia gestorPersistencia;

    // Referencia al cliente que ha ingresado exitosamente
    public static Cliente clienteActual = null;

    // Constructor de la clase Main2: Inicializa todos los datos en el orden correcto
    public Main2() {

        // 1. INICIALIZAR PERSISTENCIA (¡DEBE SER PRIMERO!)
        gestorPersistencia = new GestorPersistencia();

        // 2. CARGAR CLIENTES DESDE ARCHIVO
        // NOTA: Asumimos que cargarClientes() devuelve List<Cliente>
        List<Cliente> clientesIniciales = gestorPersistencia.cargarClientes();

        // 3. INICIALIZAR GESTORES DE LÓGICA (Usando los datos cargados)
        gestorClientes = new GestorClientes(clientesIniciales);
        gestorVeterinarios = new GestorVeterinarios(new ArrayList<Veterinario>());
        gestorTurnos = new GestorTurnos(gestorClientes, gestorVeterinarios);

        // 4. SIMULACIÓN DE DATOS MÍNIMOS (Asegura que siempre haya datos básicos)

        // Veterinario
        Veterinario esteban = new Veterinario("Esteban","Hernandez",98765432, "Cirugia Canina",20000,null);
        gestorVeterinarios.agregarVeterinario(esteban);

        if (clientesIniciales.isEmpty()) {
            Cliente clienteEmergencia = new Cliente("Cliente", "Demo", 12345678, new ArrayList<>(), 1123456789);
            gestorClientes.agregarCliente(clienteEmergencia);
        }

    }

    public static void main(String[] args) {

        // 1. Instanciamos la clase Main2 para inicializar los gestores (y cargar datos)
        Main2 app = new Main2();

        // 2. Lanzamiento de la GUI en el hilo correcto (EDT)
        SwingUtilities.invokeLater(() -> {

            // Inyección de dependencias para el Login/Registro
            DialogoLogin login = new DialogoLogin(null, app.gestorClientes, app.gestorPersistencia);
            login.setVisible(true);

            // 3. Verificación después del cierre del login
            if (login.isAccesoExitoso()) {
                clienteActual = login.getClienteLogueado();

                // Abrimos VentanaTurnos, pasando TODAS las dependencias
                new VentanaTurnos(
                        clienteActual,
                        app.gestorTurnos,
                        app.gestorVeterinarios,
                        app.gestorClientes,
                        app.gestorPersistencia
                ).setVisible(true);
            } else {
                // Si el login se cancela, cerramos la aplicación.
                System.exit(0);
            }
        });
    }
}