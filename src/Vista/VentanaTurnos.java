package Vista;

import javax.swing.*;

import Gestores.GestorClientes;
import Persistencia.GestorPersistencia;
import modelado.Personas.Cliente;
import Gestores.GestorTurnos;
import Gestores.GestorVeterinarios;
import modelado.Personas.Veterinario;
import modelado.Mascotas.Mascota;
import java.util.List;
import java.util.ArrayList; // Necesario para inicialización

public class VentanaTurnos extends JFrame {

    // --- DEPENDENCIAS INYECTADAS ---
    private Cliente clienteActual;
    private GestorTurnos gt;
    private GestorVeterinarios gv;
    private GestorClientes gc;
    private GestorPersistencia gp;

    // --- VARIABLES DE APOYO PARA RECUPERAR OBJETOS ---
    private List<Veterinario> veterinariosDisponibles; // Lista cargada del gestor
    private List<Mascota> mascotasCliente; // Lista del cliente actual


    // --- COMPONENTES DE LA GUI ---
    private JPanel contentPane;
    private JLabel lblBienvenida;
    private JComboBox<String> cmbMascota;
    private JComboBox<String> cmbVeterinario;
    private JTextField txtFecha;
    private JButton btnSolicitarTurno;
    private JButton btnCancelarTurno;

    // --- CONSTRUCTOR ---
    public VentanaTurnos(Cliente clienteActual, GestorTurnos gt, GestorVeterinarios gv, GestorClientes gc, GestorPersistencia gp) {
        super("Solicitar Turno - Cliente: " + clienteActual.getNombre());
        this.clienteActual = clienteActual;
        this.gt = gt;
        this.gv = gv;
        this.gc = gc;
        this.gp = gp;

        // Inicialización de listas de apoyo
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

    // --- LÓGICA DE INICIALIZACIÓN DE DATOS ---
    private void inicializarComponentes() {
        lblBienvenida.setText("Bienvenido/a, " + clienteActual.getNombre() + ". Su DNI es: " + clienteActual.getDni());

        // 1. Llenar Mascota (Usando la lista local de mascotasCliente)
        cmbMascota.removeAllItems();

        if (!mascotasCliente.isEmpty()) {
            for (Mascota m : mascotasCliente) {
                // Usamos getEspecie() para el formato visual
                cmbMascota.addItem(m.getNombre() + " (" + m.getEspecie() + ")");
            }
        } else {
            cmbMascota.addItem("No hay mascotas registradas");
        }

        // 2. Llenar Veterinario (Usando la lista local de veterinariosDisponibles)
        cmbVeterinario.removeAllItems();
        veterinariosDisponibles = gv.getVeterinarios(); // Llenamos la lista de apoyo

        if (veterinariosDisponibles != null && !veterinariosDisponibles.isEmpty()) {
            for (Veterinario v : veterinariosDisponibles) {
                cmbVeterinario.addItem(v.getNombre() + " " + v.getApellido() + " (" + v.getEspecialidad() + ")");
            }
        } else {
            cmbVeterinario.addItem("No hay veterinarios cargados.");
        }
    }

    // --- LÓGICA FINAL ---
    private void onSolicitarTurno() {
        int mascotaIndex = cmbMascota.getSelectedIndex();
        int veterinarioIndex = cmbVeterinario.getSelectedIndex();
        String fecha = txtFecha.getText();

        if (mascotaIndex == -1 || veterinarioIndex == -1 || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar mascota, veterinario y fecha.", "Error de Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (mascotasCliente.isEmpty() || veterinariosDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay datos de mascota o veterinario cargados.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. Obtener los OBJETOS REALES por índice (CRUCIAL)
        Mascota mascotaSeleccionada = mascotasCliente.get(mascotaIndex);
        Veterinario veterinarioSeleccionado = veterinariosDisponibles.get(veterinarioIndex);

        // 2. Llamada al GestorTurnos
        gt.solicitarTurno(clienteActual, mascotaSeleccionada, veterinarioSeleccionado, fecha);

        JOptionPane.showMessageDialog(this,
                "Turno solicitado con éxito para el " + fecha + ". ¡Guardando datos!",
                "Confirmación Final",
                JOptionPane.INFORMATION_MESSAGE);

        // REQUISITO FINAL: Cierra la aplicación (y guarda si es necesario, aunque GT ya lo hace)
        System.exit(0);
    }

    // --- LÓGICA DE NAVEGACIÓN ---
    private void onCancelarTurno() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea cancelar y volver a la pantalla de inicio?",
                "Volver al Inicio",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            this.dispose();
            // ¡Relanza el Login Inyectando las dependencias completas!
            new DialogoLogin(null, this.gc, this.gp).setVisible(true);
        }
    }
}