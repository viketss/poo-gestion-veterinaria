package Vista;

import Gestores.*;
import Persistencia.GestorPersistencia;

import modelado.Mascotas.Mascota;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;
import modelado.HistoriaClinica.HorarioTurno; // <-- Importación correcta

import javax.swing.*;
import java.util.List;

public class VentanaTurnos extends JFrame {

    private final Cliente clienteActual;
    private final GestorTurnos gt;
    private final GestorVeterinarios gv;
    private final GestorClientes gc;
    private final GestorPersistencia gp;
    private final GestorVentas gvtas;
    private final GestorMascota gm;
    private final GestorHistoriaClinica ghc; // <--- 1. AGREGAR ESTO

    private List<Mascota> mascotasCliente;
    private List<Veterinario> veterinariosDisponibles;

    private JPanel contentPane;
    private JComboBox<String> cmbMascota;
    private JComboBox<String> cmbVeterinario;
    private JTextField txtFecha;
    private JComboBox<String> cmbHorario; // <-- Ya existe y está enlazado
    private JButton btnSolicitarTurno;
    private JButton btnCancelar;
    private JLabel lblBienvenida;
    private JLabel lblMascota;
    private JLabel lblVeterinario;
    private JLabel lblFecha;
    private JLabel lblHorario;

    // constructor
    public VentanaTurnos(Cliente clienteActual, GestorTurnos gt, GestorVeterinarios gv, GestorClientes gc, GestorPersistencia gp, GestorVentas gvtas, GestorMascota gm, GestorHistoriaClinica ghc) {
        this.clienteActual = clienteActual;
        this.gt = gt;
        this.gv = gv;
        this.gc = gc;
        this.gp = gp;
        this.gvtas = gvtas;
        this.gm = gm;


        this.mascotasCliente = clienteActual.getMascotas();
        this.veterinariosDisponibles = gv.getVeterinarios();
        this.ghc = ghc;

        setTitle("Solicitar Turno - Cliente: " + clienteActual.getNombre());
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        agregarListeners();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarComponentes() {
        lblBienvenida.setText("Cliente: " + clienteActual.getNombre() + ". Solicite un Turno");

        cmbMascota.removeAllItems();
        if (mascotasCliente != null) {
            for (Mascota m : mascotasCliente) {
                cmbMascota.addItem(m.getNombre());
            }
        }

        cmbVeterinario.removeAllItems();
        if (veterinariosDisponibles != null) {
            for (Veterinario v : veterinariosDisponibles) {
                cmbVeterinario.addItem(v.getNombre() + " (" + v.getEspecialidad() + ")");
            }
        }

        cmbHorario.removeAllItems();
        for (HorarioTurno horario : HorarioTurno.values()) {
            cmbHorario.addItem(horario.toString());
        }
    }

    private void agregarListeners() {
        btnSolicitarTurno.addActionListener(e -> onSolicitarTurno());

        if (btnCancelar != null) {
            btnCancelar.addActionListener(e -> dispose());
        }
    }

    private void onSolicitarTurno() {
        int mascotaIndex = cmbMascota.getSelectedIndex();
        int veterinarioIndex = cmbVeterinario.getSelectedIndex();
        String fecha = txtFecha.getText().trim();
        String horarioString = (String) cmbHorario.getSelectedItem();

        if (mascotaIndex == -1 || veterinarioIndex == -1 || fecha.isEmpty() || horarioString == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar mascota, veterinario, fecha y horario.", "Error de Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String enumName = "H_" + horarioString.replace(":", "_");
            HorarioTurno horarioSeleccionado = HorarioTurno.valueOf(enumName);

            Mascota mascotaSeleccionada = mascotasCliente.get(mascotaIndex);
            Veterinario veterinarioSeleccionado = veterinariosDisponibles.get(veterinarioIndex);

            String resultado = gt.solicitarTurno(clienteActual, mascotaSeleccionada, veterinarioSeleccionado, fecha, horarioSeleccionado);

            JOptionPane.showMessageDialog(this, resultado, "Resultado de la Solicitud", JOptionPane.INFORMATION_MESSAGE);

            if (!resultado.startsWith("Error")) {
                dispose();
                new VentanaMenuCliente(clienteActual, gt, gv, gc, gp, gvtas, gm, this.ghc).setVisible(true);
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error al procesar el horario o datos inválidos. Verifique el formato de la fecha.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado al solicitar el turno: " + ex.getMessage(), "Error Grave", JOptionPane.ERROR_MESSAGE);
        }
    }
}