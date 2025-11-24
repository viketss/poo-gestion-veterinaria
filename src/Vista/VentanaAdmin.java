package Vista;

import Gestores.GestorClientes;
import Gestores.GestorVentas;
import Persistencia.GestorPersistencia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;

public class VentanaAdmin extends JFrame {

    // --- DEPENDENCIAS DEL FLUJO ---
    private final GestorVentas gestorVentas;
    private final GestorClientes gestorClientes;
    private final GestorPersistencia gestorPersistencia;
    // ------------------------------

    // Componentes que deben ser inicializados por el diseñador de Forms
    private JTable tablaPagos;
    private JScrollPane scrollPane;
    private JPanel contentPane;
    private JLabel lblTitulo;

    // El botón que queremos mantener
    private JButton btnCerrarSesion;

    public VentanaAdmin(GestorVentas gvtas, GestorClientes gc, GestorPersistencia gp) {
        this.gestorVentas = gvtas;
        this.gestorClientes = gc;
        this.gestorPersistencia = gp;

        setTitle("Administración - Historial de Pagos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 1. Establecer el contentPane y añadir componentes no automáticos
        if (contentPane == null) {
            contentPane = new JPanel(new BorderLayout());
            setContentPane(contentPane);
        }
        setContentPane(contentPane);

        // 2. Configurar el modelo, cargar datos y añadir el botón
        initComponents();
        cargarDatosTabla();

        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        String[] columnas = {"DNI Cliente", "Monto Final ($)", "Método de Pago", "Fecha Turno"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Asignamos el modelo al JTable (tablaPagos debe estar inicializado por el diseñador)
        tablaPagos.setModel(model);

        btnCerrarSesion.addActionListener(e -> onCerrarSesion());

    }

    private void cargarDatosTabla() {
        DefaultTableModel model = (DefaultTableModel) tablaPagos.getModel();
            List<String[]> historial = gestorVentas.getHistorialPagosEstructurado();
            model.setRowCount(0);
            for (String[] fila : historial) {
                model.addRow(fila);
            }
    }

    private void onCerrarSesion() {
        this.dispose();


        SwingUtilities.invokeLater(() -> {
            DialogoLogin login = new DialogoLogin(
                    null,
                    this.gestorClientes,
                    this.gestorPersistencia,
                    this.gestorVentas
            );
            login.setVisible(true);

            // Si después de relanzar el login, el usuario lo cierra, el programa debería terminar
            if (!login.isAccesoExitoso()) {
                System.exit(0);
            }
        });
    }
}