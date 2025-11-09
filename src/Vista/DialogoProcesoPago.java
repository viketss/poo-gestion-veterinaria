package Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Gestores.GestorVentas;
import modelado.Personas.Cliente;
import SOLID.OyL.IMetodoPago;
import SOLID.OyL.PagoEfectivo;
import SOLID.OyL.PagoTarjeta;
import SOLID.OyL.PagoTranferencia;

public class DialogoProcesoPago extends JDialog implements ActionListener {

    // --- DEPENDENCIAS INYECTADAS ---
    private final GestorVentas gestorVentas;
    private final Cliente clienteLogueado;

    // --- COMPONENTES (Basado en tu .form) ---
    private JPanel contentPane;
    private JTextField txtMonto;
    private JComboBox<String> cmbMetodoPago;
    // Asumimos que los labels lblTotalFinal y lblClienteNombre NO existen
    private JButton btnPagar;
    private JButton btnCancelar;

    // --- CONSTRUCTOR ---
    public DialogoProcesoPago(JFrame parent, Cliente cliente, GestorVentas gestor) {
        // Muestra el nombre del cliente en el título
        super(parent, "Procesar Pago - Cliente: " + cliente.getNombre() + " " + cliente.getApellido(), true);

        this.gestorVentas = gestor;
        this.clienteLogueado = cliente;

        // 1. LLAMADA CRÍTICA: Inicializa los componentes del .form (contentPane, txtMonto, etc.)
        // Si su IDE usa un nombre diferente, debe ajustarlo.
        // initComponents();

        // 2. Establece el panel principal (asumiendo que initComponents lo inicializó)
        setContentPane(contentPane);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 3. Configuración de datos y listeners
        setupComponentData();
        setupListeners();

        pack();
        setLocationRelativeTo(parent);
    }

    private void setupComponentData() {
        // Inicialización de datos en el ComboBox y campo de texto.

        String[] metodos = {"Seleccione Método", "Efectivo (10% Desc.)", "Tarjeta (5% Recargo)", "Transferencia (Sin costo)"};

        cmbMetodoPago.removeAllItems();
        for (String metodo : metodos) {
            cmbMetodoPago.addItem(metodo);
        }

        txtMonto.setText("0.0");
    }

    private void setupListeners() {
        // Los Listeners se enfocan solo en el botón Pagar y Cancelar
        btnPagar.addActionListener(this);
        if (btnCancelar != null) {
            btnCancelar.addActionListener(e -> dispose());
        }
    }

    // --- LÓGICA DE NEGOCIO ---

    private IMetodoPago getMetodoPagoSeleccionado() {
        String seleccion = (String) cmbMetodoPago.getSelectedItem();
        if (seleccion == null || seleccion.startsWith("Seleccione")) return null;

        if (seleccion.startsWith("Efectivo")) {
            return new PagoEfectivo();
        } else if (seleccion.startsWith("Tarjeta")) {
            return new PagoTarjeta();
        } else if (seleccion.startsWith("Transferencia")) {
            return new PagoTranferencia();
        }
        return null;
    }

    private void realizarPago() {
        try {
            double monto = Double.parseDouble(txtMonto.getText());
            IMetodoPago metodo = getMetodoPagoSeleccionado();

            if (metodo == null || monto <= 0) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un monto > 0 y seleccionar un método.", "Error de Pago", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1. Calcula el monto final (con descuento/recargo)
            double totalPagado = gestorVentas.calcularTotalFinal(monto, metodo);

            // 2. Pide confirmación ANTES de guardar en el archivo
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    String.format("El total final es $%.2f. ¿Confirmar el pago?", totalPagado),
                    "Confirmación de Pago",
                    JOptionPane.YES_NO_OPTION
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                // 3. Procesa y persiste el pago
                boolean exito = gestorVentas.procesarYRegistrarPago(clienteLogueado, monto, metodo);

                if (exito) {
                    JOptionPane.showMessageDialog(this, String.format("Pago de $%.2f registrado con éxito.", totalPagado), "Pago Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un monto numérico válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- MANEJADOR DE EVENTOS ---
    @Override
    public void actionPerformed(ActionEvent e) {
        // Solo maneja el botón Pagar
        if (e.getSource() == btnPagar) {
            realizarPago();
        }
    }

    // 🚨 Si su IDE genera este método, asegúrese de que esté presente y que el constructor lo llame.
    // private void initComponents() { ... }
}