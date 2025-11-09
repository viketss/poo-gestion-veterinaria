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

    private final GestorVentas gestorVentas;
    private final Cliente clienteLogueado;

    private JPanel contentPane;
    private JTextField txtMonto;
    private JComboBox<String> cmbMetodoPago;
    private JButton btnPagar;
    private JButton btnCancelar;

    public DialogoProcesoPago(JFrame parent, Cliente cliente, GestorVentas gestor) {
        super(parent, "Procesar Pago - Cliente: " + cliente.getNombre() + " " + cliente.getApellido(), true);

        this.gestorVentas = gestor;
        this.clienteLogueado = cliente;


        setContentPane(contentPane);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setupComponentData();
        setupListeners();

        pack();
        setLocationRelativeTo(parent);
    }

    private void setupComponentData() {

        String[] metodos = {"Seleccione Método", "Efectivo (10% Desc.)", "Tarjeta (5% Recargo)", "Transferencia (Sin costo)"};

        cmbMetodoPago.removeAllItems();
        for (String metodo : metodos) {
            cmbMetodoPago.addItem(metodo);
        }

        txtMonto.setText("0.0");
    }

    private void setupListeners() {
        btnPagar.addActionListener(this);
        if (btnCancelar != null) {
            btnCancelar.addActionListener(e -> dispose());
        }
    }


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

            double totalPagado = gestorVentas.calcularTotalFinal(monto, metodo);

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    String.format("El total final es $%.2f. ¿Confirmar el pago?", totalPagado),
                    "Confirmación de Pago",
                    JOptionPane.YES_NO_OPTION
            );

            if (respuesta == JOptionPane.YES_OPTION) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPagar) {
            realizarPago();
        }
    }

}