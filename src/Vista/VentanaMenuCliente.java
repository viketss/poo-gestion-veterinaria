package Vista;

import javax.swing.*;

import Gestores.*;
import Persistencia.GestorPersistencia;
import modelado.Mascotas.Mascota;
import modelado.Personas.Cliente;

import java.util.List;

public class VentanaMenuCliente extends JFrame {

    private final Cliente clienteActual;
    private final GestorTurnos gt;
    private final GestorVeterinarios gv;
    private final GestorClientes gc;
    private final GestorPersistencia gp;
    private final GestorVentas gvtas;
    private final GestorMascota gm;
    private final GestorHistoriaClinica ghc;

    private JPanel contentPane;
    private JLabel lblBienvenida;
    private JButton btnSolicitarTurno;
    private JButton btnPagar;
    private JButton btnCerrarSesion;
    private JButton btnAgregarMascota; // Asumiendo que este botón existe en el formulario
    private JButton btnVerHistorial;

    // Constructor modificado para recibir los 7 gestores
    public VentanaMenuCliente(Cliente clienteActual, GestorTurnos gt, GestorVeterinarios gv, GestorClientes gc, GestorPersistencia gp, GestorVentas gvtas, GestorMascota gm, GestorHistoriaClinica ghc) {
        super("Menú Principal - Cliente: " + clienteActual.getNombre());
        this.clienteActual = clienteActual;
        this.gt = gt;
        this.gv = gv;
        this.gc = gc;
        this.gp = gp;
        this.gvtas = gvtas;
        this.gm = gm; // Asignación del nuevo GestorMascota
        this.ghc = ghc;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarDatos();
        setupListeners();

        pack();
        setLocationRelativeTo(null);
    }

    private void inicializarDatos() {
        lblBienvenida.setText("Bienvenido/a, " + clienteActual.getNombre() + ". ¿Qué desea hacer?");
    }

    private void setupListeners() {
        btnSolicitarTurno.addActionListener(e -> onSolicitarTurno());
        btnPagar.addActionListener(e -> onPagar());
        btnCerrarSesion.addActionListener(e -> onCerrarSesion());

        // Listener para la nueva funcionalidad
        if (btnAgregarMascota != null) {
            btnAgregarMascota.addActionListener(e -> onAgregarMascota());
        }

        // Listener para el Historial
        if (btnVerHistorial != null) {
            btnVerHistorial.addActionListener(e -> onVerHistorial());
        }
    }

    private void onSolicitarTurno() {
        this.dispose();
        // Llamada corregida con los 7 argumentos
        new VentanaTurnos(clienteActual, gt, gv, gc, gp, gvtas, gm, this.ghc).setVisible(true);
    }

    private void onPagar() {
        new DialogoProcesoPago(this,clienteActual,gvtas,gt).setVisible(true);
    }

    private void onCerrarSesion() {
        this.dispose();
        new DialogoLogin(null, this.gc, this.gp).setVisible(true);
    }

    // Método para lanzar la ventana de registro de mascotas
    private void onAgregarMascota() {
        // Lanza la ventana de registro pasándole el Cliente actual y el GestorMascota
        VentanaAgregarMascota ventanaMascota = new VentanaAgregarMascota(this.clienteActual, this.gm, this.gc);
        ventanaMascota.setVisible(true);
    }

    // --- 4. NUEVA FUNCIONALIDAD: VER HISTORIAL ---
    private void onVerHistorial() {
        List<Mascota> mascotas = clienteActual.getMascotas();

        if (mascotas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes mascotas registradas.");
            return;
        }

        Mascota mascotaSeleccionada = mascotas.get(0);

        // Si hay más de una, preguntar cuál
        if (mascotas.size() > 1) {
            mascotaSeleccionada = (Mascota) JOptionPane.showInputDialog(
                    this,
                    "Seleccione la mascota:",
                    "Ver Historia Clínica",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    mascotas.toArray(),
                    mascotas.get(0)
            );
        }

        if (mascotaSeleccionada != null) {
            // Abrimos el diálogo pasando la mascota y el gestor simulador
            new DialogoHistoriaClinica(this, mascotaSeleccionada, this.ghc).setVisible(true);
        }
    }
}