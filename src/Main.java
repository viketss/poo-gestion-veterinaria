import javax.swing.SwingUtilities;

import Gestores.GestorClientes;
import Gestores.GestorTurnos;
import Gestores.GestorVeterinarios;
import Gestores.GestorVentas;
import Gestores.GestorMascota;
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

    public static Cliente clienteActual = null;


    public Main() {

        gestorPersistencia = new GestorPersistencia();

        List<Cliente> clientesIniciales = gestorPersistencia.cargarClientes();
        List<Veterinario> veterinariosCargados = gestorPersistencia.cargarVeterinarios();

        gestorClientes = new GestorClientes(clientesIniciales);

        gestorVeterinarios = new GestorVeterinarios(veterinariosCargados, gestorPersistencia);

        gestorVentas = new GestorVentas(gestorPersistencia);

        // 1. Inicialización de GestorMascota
        gestorMascota = new GestorMascota(gestorClientes, gestorPersistencia);

        gestorTurnos = new GestorTurnos(gestorPersistencia, gestorClientes, gestorVeterinarios);

        if (clientesIniciales.isEmpty()) {
            Cliente clienteEmergencia = new Cliente("Cliente", "Demo", 12345678, new ArrayList<>(), 1123456789);
            gestorClientes.agregarCliente(clienteEmergencia);
            System.out.println("Cliente demo creado.");
        }
    }

    public static void main(String[] args) {

        Main app = new Main();

        SwingUtilities.invokeLater(() -> {

            DialogoLogin login = new DialogoLogin(null, app.gestorClientes, app.gestorPersistencia);
            login.setVisible(true);

            if (login.isAccesoExitoso()) {
                clienteActual = login.getClienteLogueado();

                // 2. Pasar gestorMascota como nuevo argumento
                new VentanaMenuCliente(
                        clienteActual,
                        app.gestorTurnos,
                        app.gestorVeterinarios,
                        app.gestorClientes,
                        app.gestorPersistencia,
                        app.gestorVentas,
                        app.gestorMascota // <- Nuevo argumento
                ).setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}