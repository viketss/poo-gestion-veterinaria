import javax.swing.SwingUtilities;

import Gestores.GestorClientes;
import Gestores.GestorTurnos;
import Gestores.GestorVeterinarios;
import Gestores.GestorVentas; // <-- ¡NUEVO GESTOR!
import Persistencia.GestorPersistencia;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import Vista.DialogoLogin;
import Vista.VentanaMenuCliente; // <-- ¡NUEVA VENTANA!

import java.util.ArrayList;
import java.util.List;

public class Main2 {

    // Instancias de los gestores
    public GestorClientes gestorClientes;
    public GestorTurnos gestorTurnos;
    public GestorVeterinarios gestorVeterinarios;
    public GestorPersistencia gestorPersistencia;
    public GestorVentas gestorVentas; // <-- ¡DECLARACIÓN DEL GESTOR VENTAS!

    // Referencia al cliente que ha ingresado exitosamente
    public static Cliente clienteActual = null;

    // Constructor de la clase Main2: Inicializa todos los datos en el ORDEN CORRECTO
    public Main2() {

        // 1. INICIALIZAR PERSISTENCIA
        gestorPersistencia = new GestorPersistencia();

        // 2. CARGAR CLIENTES Y VETERINARIOS DESDE ARCHIVO
        List<Cliente> clientesIniciales = gestorPersistencia.cargarClientes();
        List<Veterinario> veterinariosCargados = gestorPersistencia.cargarVeterinarios();

        // 3. INICIALIZAR GESTORES DE LÓGICA CON DATOS CARGADOS
        gestorClientes = new GestorClientes(clientesIniciales);
        gestorVeterinarios = new GestorVeterinarios(veterinariosCargados);

        // 4. INICIALIZAR GESTOR DE VENTAS (requiere persistencia) <-- PASO CLAVE 1
        gestorVentas = new GestorVentas(gestorPersistencia);

        // 5. INICIALIZAR TURNO (requiere los otros gestores)
        gestorTurnos = new GestorTurnos(gestorPersistencia, gestorClientes, gestorVeterinarios);

        // 6. CONTROL DE CLIENTE DEMO DE EMERGENCIA
        if (clientesIniciales.isEmpty()) {
            Cliente clienteEmergencia = new Cliente("Cliente", "Demo", 12345678, new ArrayList<>(), 1123456789);
            gestorClientes.agregarCliente(clienteEmergencia);
            System.out.println("Cliente demo creado.");
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

                // 4. Abrimos la nueva VENTANA DE MENÚ, pasando TODAS las dependencias. <-- PASO CLAVE 2
                new VentanaMenuCliente(
                        clienteActual,
                        app.gestorTurnos,
                        app.gestorVeterinarios,
                        app.gestorClientes,
                        app.gestorPersistencia,
                        app.gestorVentas // <-- ¡Inyectamos el GestorVentas!
                ).setVisible(true);
            } else {
                // Si el login se cancela, cerramos la aplicación.
                System.exit(0);
            }
        });
    }
}