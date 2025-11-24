import javax.swing.SwingUtilities;

import Gestores.*;
import Persistencia.GestorPersistencia;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import Vista.DialogoLogin;
import Vista.VentanaMenuCliente;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public GestorClientes gestorClientes;
    public GestorTurnos gestorTurnos;
    public GestorVeterinarios gestorVeterinarios;
    public GestorPersistencia gestorPersistencia;
    public GestorVentas gestorVentas;
    public GestorMascota gestorMascota;
    public GestorMedicamentos gestorMedicamentos;

    public static Cliente clienteActual = null;


    public Main() {

        gestorPersistencia = new GestorPersistencia();

        List<Cliente> clientesIniciales = gestorPersistencia.cargarClientes();
        List<Veterinario> veterinariosCargados = gestorPersistencia.cargarVeterinarios();

        // Inicialización dentro del constructor
        this.gestorMedicamentos = new GestorMedicamentos();

        gestorClientes = new GestorClientes(clientesIniciales, gestorPersistencia);

        gestorVeterinarios = new GestorVeterinarios(veterinariosCargados, gestorPersistencia);

        gestorVentas = new GestorVentas(gestorPersistencia);

        gestorMascota = new GestorMascota(gestorClientes, gestorPersistencia);

        gestorTurnos = new GestorTurnos(gestorPersistencia, gestorClientes, gestorVeterinarios, gestorMedicamentos);

        if (clientesIniciales.isEmpty()) {
            Cliente clienteEmergencia = new Cliente("Cliente", "Demo", 12345678, new ArrayList<>(), 1123456789);
            gestorClientes.agregarCliente(clienteEmergencia);
            System.out.println("Cliente demo creado.");
        }
    }

    public static void main(String[] args) {

        Main app = new Main();

        SwingUtilities.invokeLater(() -> {

            DialogoLogin login = new DialogoLogin(null, app.gestorClientes, app.gestorPersistencia, app.gestorVentas);
            login.setVisible(true);

            // Verificamos si el login fue marcado como exitoso (Admin o Cliente)
            if (login.isAccesoExitoso()) {

                clienteActual = login.getClienteLogueado();

                // --- CORRECCIÓN AQUÍ ---
                // Solo abrimos el menú de CLIENTE si realmente hay un cliente logueado.
                // Si clienteActual es null, significa que entró el Admin (y su ventana ya se abrió en DialogoLogin).
                if (clienteActual != null) {
                    new VentanaMenuCliente(
                            clienteActual,
                            app.gestorTurnos,
                            app.gestorVeterinarios,
                            app.gestorClientes,
                            app.gestorPersistencia,
                            app.gestorVentas,
                            app.gestorMascota
                    ).setVisible(true);
                }
                // Si es Admin, no hacemos nada aquí, dejamos que el programa siga corriendo con la VentanaAdmin.

            } else {
                System.exit(0);
            }
        });
    }
}