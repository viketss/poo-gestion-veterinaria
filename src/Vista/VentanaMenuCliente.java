package Vista;

import javax.swing.*;

import Gestores.GestorTurnos;
import Gestores.GestorVeterinarios;
import Gestores.GestorClientes;
import Gestores.GestorVentas;
import Persistencia.GestorPersistencia;
import modelado.Personas.Cliente;

public class VentanaMenuCliente extends JFrame {

    // --- DEPENDENCIAS INYECTADAS ---
    private final Cliente clienteActual;
    private final GestorTurnos gt;
    private final GestorVeterinarios gv;
    private final GestorClientes gc;
    private final GestorPersistencia gp;
    private final GestorVentas gvtas; // ¡Nuevo Gestor!

    // --- COMPONENTES (Asumimos que están definidos en el .form) ---
    private JPanel contentPane;
    private JButton btnSolicitarTurno;
    private JButton btnPagar;
    private JButton btnCerrarSesion;

    // --- CONSTRUCTOR ---
    public VentanaMenuCliente(Cliente clienteActual, GestorTurnos gt, GestorVeterinarios gv, GestorClientes gc, GestorPersistencia gp, GestorVentas gvtas) {
        super("Menú Principal - Cliente: " + clienteActual.getNombre());
        this.clienteActual = clienteActual;
        this.gt = gt;
        this.gv = gv;
        this.gc = gc;
        this.gp = gp;
        this.gvtas = gvtas;

        // NOTA: El contentPane y sus componentes (lblBienvenida, btn...)
        // deben inicializarse automáticamente por el .form o ser enlazados aquí.
        // Asumo que 'contentPane' es el contenedor principal.

        setContentPane(contentPane); // Establecer el panel principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarDatos(); // Lógica de texto y listeners

        pack();
        setLocationRelativeTo(null);
    }

    private void inicializarDatos() {

        btnSolicitarTurno.addActionListener(e -> onSolicitarTurno());
        btnPagar.addActionListener(e -> onPagar());
        btnCerrarSesion.addActionListener(e -> onCerrarSesion());
    }

    // --- NAVEGACIÓN ---

    private void onSolicitarTurno() {
        this.dispose(); // Cierra el menú
        // Abre la ventana existente para solicitar turno
        new VentanaTurnos(clienteActual, gt, gv, gc, gp).setVisible(true);
    }

    private void onPagar() {
        DialogoProcesoPago dialogoPago = new DialogoProcesoPago(this, clienteActual, gvtas);

        dialogoPago.setVisible(true);

    }


    private void onCerrarSesion() {
        this.dispose();
        // Vuelve a abrir el login inyectando las dependencias necesarias
        new DialogoLogin(null, this.gc, this.gp).setVisible(true);
    }
}