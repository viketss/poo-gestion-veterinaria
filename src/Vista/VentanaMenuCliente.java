package Vista;

import javax.swing.*;
import java.awt.event.ActionListener;
import Gestores.*;
import Persistencia.GestorPersistencia;
import modelado.Personas.Cliente;

public class VentanaMenuCliente extends JFrame {

    private final Cliente clienteActual;
    private final GestorTurnos gt;
    private final GestorVeterinarios gv;
    private final GestorClientes gc;
    private final GestorPersistencia gp;
    private final GestorVentas gvtas;

    private JPanel contentPane;
    private JLabel lblBienvenida;
    private JButton btnSolicitarTurno;
    private JButton btnPagar;
    private JButton btnCerrarSesion;

    public VentanaMenuCliente(Cliente clienteActual, GestorTurnos gt, GestorVeterinarios gv, GestorClientes gc, GestorPersistencia gp, GestorVentas gvtas) {
        super("Menú Principal - Cliente: " + clienteActual.getNombre());
        this.clienteActual = clienteActual;
        this.gt = gt;
        this.gv = gv;
        this.gc = gc;
        this.gp = gp;
        this.gvtas = gvtas;

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
    }

    private void onSolicitarTurno() {
        this.dispose();
        new VentanaTurnos(clienteActual, gt, gv, gc, gp, gvtas).setVisible(true);
    }

    private void onPagar() {
        new DialogoProcesoPago(this, clienteActual, gvtas).setVisible(true);
    }

    private void onCerrarSesion() {
        this.dispose();
        new DialogoLogin(null, this.gc, this.gp).setVisible(true);
    }
}