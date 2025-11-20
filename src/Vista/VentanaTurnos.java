package Vista;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import Gestores.GestorClientes;
import Gestores.GestorTurnos;
import Gestores.GestorVeterinarios;
import Gestores.GestorVentas;
import Gestores.GestorMascota;
import Persistencia.GestorPersistencia;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import modelado.Mascotas.Mascota;

public class VentanaTurnos extends JFrame {


    private final Cliente clienteActual;
    private final GestorTurnos gt;
    private final GestorVeterinarios gv;
    private final GestorClientes gc;
    private final GestorPersistencia gp;
    private final GestorVentas gvtas;
    private final GestorMascota gm; // <--- Nuevo Atributo agregado

    private List<Veterinario> veterinariosDisponibles;
    private List<Mascota> mascotasCliente;

    private JPanel contentPane;
    private JLabel lblBienvenida;
    private JComboBox<String> cmbMascota;
    private JComboBox<String> cmbVeterinario;
    private JTextField txtFecha;
    private JButton btnSolicitarTurno;
    private JButton btnCancelarTurno;



    public VentanaTurnos(Cliente clienteActual, GestorTurnos gt, GestorVeterinarios gv, GestorClientes gc, GestorPersistencia gp, GestorVentas gvtas, GestorMascota gm) {
        super("Solicitar Turno - Cliente: " + clienteActual.getNombre());
        this.clienteActual = clienteActual;
        this.gt = gt;
        this.gv = gv;
        this.gc = gc;
        this.gp = gp;
        this.gvtas = gvtas;
        this.gm = gm; // Asignación del nuevo gestor

        this.veterinariosDisponibles = new ArrayList<>();
        this.mascotasCliente = clienteActual.getMascotas() != null ? clienteActual.getMascotas() : new ArrayList<>();

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();

        btnSolicitarTurno.addActionListener(e -> onSolicitarTurno());
        btnCancelarTurno.addActionListener(e -> onCancelarTurno());

        pack();
        setLocationRelativeTo(null);
    }


    private void inicializarComponentes() {
        lblBienvenida.setText("Bienvenido/a, " + clienteActual.getNombre() + ". Su DNI es: " + clienteActual.getDni());


        cmbMascota.removeAllItems();
        if (!mascotasCliente.isEmpty()) {
            for (Mascota m : mascotasCliente) {
                cmbMascota.addItem(m.getNombre() + " (" + m.getEspecie() + ")");
            }
        } else {
            cmbMascota.addItem("No hay mascotas registradas");
        }


        cmbVeterinario.removeAllItems();
        veterinariosDisponibles = gv.getVeterinarios();
        if (veterinariosDisponibles != null && !veterinariosDisponibles.isEmpty()) {
            for (Veterinario v : veterinariosDisponibles) {
                cmbVeterinario.addItem(v.getNombre() + " " + v.getApellido() + " (" + v.getEspecialidad() + ")");
            }
        } else {
            cmbVeterinario.addItem("No hay veterinarios cargados.");
        }
    }


    private void onSolicitarTurno() {
        int mascotaIndex = cmbMascota.getSelectedIndex();
        int veterinarioIndex = cmbVeterinario.getSelectedIndex();
        String fecha = txtFecha.getText();

        if (mascotaIndex == -1 || veterinarioIndex == -1 || fecha.isEmpty() || mascotasCliente.isEmpty() || veterinariosDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar mascota, veterinario y fecha.", "Error de Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Mascota mascotaSeleccionada = mascotasCliente.get(mascotaIndex);
        Veterinario veterinarioSeleccionado = veterinariosDisponibles.get(veterinarioIndex);

        String resultado = gt.solicitarTurno(clienteActual, mascotaSeleccionada, veterinarioSeleccionado, fecha);

        JOptionPane.showMessageDialog(this,
                resultado,
                "Confirmación Final",
                JOptionPane.INFORMATION_MESSAGE);


        this.dispose();


        new VentanaMenuCliente(clienteActual, gt, gv, gc, gp, gvtas, gm).setVisible(true);
    }

    private void onCancelarTurno() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea cancelar y volver al menú principal?",
                "Volver al Menú",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            this.dispose();
            // Llamada corregida con los 7 argumentos
            new VentanaMenuCliente(clienteActual, gt, gv, gc, gp, gvtas, gm).setVisible(true);
        }
    }
}