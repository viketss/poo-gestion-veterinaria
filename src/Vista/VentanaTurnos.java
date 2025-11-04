package Vista;

import javax.swing.*;
import modelado.Personas.Cliente;
import Gestores.GestorTurnos;
import Gestores.GestorVeterinarios;
import modelado.Personas.Veterinario;
import modelado.Mascotas.Mascota;
import java.util.List;

public class VentanaTurnos extends JFrame {

    // --- DEPENDENCIAS INYECTADAS ---
    private Cliente clienteActual;
    private GestorTurnos gt;
    private GestorVeterinarios gv;

    // --- COMPONENTES DE LA GUI (Asegura la vinculación con el .form) ---
    private JPanel contentPane;
    private JLabel lblBienvenida;
    private JComboBox<String> cmbMascota;
    private JComboBox<String> cmbVeterinario;
    private JTextField txtFecha;
    private JButton btnSolicitarTurno;

    // --- CONSTRUCTOR ---
    public VentanaTurnos(Cliente clienteActual, GestorTurnos gt, GestorVeterinarios gv) {
        super("Solicitar Turno - Cliente: " + clienteActual.getNombre());
        this.clienteActual = clienteActual;
        this.gt = gt;
        this.gv = gv;

        // La vinculación del contentPane debe hacerse en un getPanel() si usas el diseñador
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();

        btnSolicitarTurno.addActionListener(e -> onSolicitarTurno());

        pack();
        setLocationRelativeTo(null);
    }

    // --- LÓGICA DE INICIALIZACIÓN DE DATOS ---
    private void inicializarComponentes() {
        lblBienvenida.setText("Bienvenido/a, " + clienteActual.getNombre() + ". Su DNI es: " + clienteActual.getDni());

        // 1. Llenar Mascota
        cmbMascota.removeAllItems();
        List<Mascota> mascotas = clienteActual.getMascotas();

        if (mascotas != null && !mascotas.isEmpty()) {
            for (Mascota m : mascotas) {
                // Usamos getEspecie() o getRaza() si getTipo() no funciona en el toString del enum
                cmbMascota.addItem(m.getNombre() + " (" + m.getEspecie() + ")");
            }
        } else {
            cmbMascota.addItem("No hay mascotas registradas (¡Regístrese de nuevo si es necesario!)");
        }

        // 2. Llenar Veterinario (Usa el getVeterinarios() que arreglamos)
        cmbVeterinario.removeAllItems();
        for (Veterinario v : gv.getVeterinarios()) {
            cmbVeterinario.addItem(v.getNombre() + " " + v.getApellido() + " (" + v.getEspecialidad() + ")");
        }
    }

    // --- LÓGICA FINAL ---
    private void onSolicitarTurno() {
        String mascotaSeleccionada = (String) cmbMascota.getSelectedItem();
        String veterinarioSeleccionado = (String) cmbVeterinario.getSelectedItem();
        String fecha = txtFecha.getText();

        if (mascotaSeleccionada == null || veterinarioSeleccionado == null || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Error de Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Llama al GestorTurnos
        // NOTA: El método solicita el nombre de la mascota y el veterinario; usamos la cadena seleccionada.
        gt.solicitarTurno(clienteActual.getNombre(), mascotaSeleccionada, veterinarioSeleccionado, fecha);

        JOptionPane.showMessageDialog(this,
                "Turno solicitado con éxito para el " + fecha + ". ¡Gracias por usar Patitas Felices!",
                "Confirmación Final",
                JOptionPane.INFORMATION_MESSAGE);

        // REQUISITO FINAL: Cierra la aplicación
        System.exit(0);
    }
}