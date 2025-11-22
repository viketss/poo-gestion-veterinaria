package Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Gestores.GestorVentas;
import Gestores.GestorTurnos;
import modelado.Personas.Cliente;
import modelado.HistoriaClinica.Turno;
import SOLID.OyL.IMetodoPago;
import SOLID.OyL.PagoEfectivo;
import SOLID.OyL.PagoTarjeta;
import SOLID.OyL.PagoTranferencia;

public class DialogoProcesoPago extends JDialog implements ActionListener {

    private final GestorVentas gestorVentas;
    private final GestorTurnos gestorTurnos;
    private final Cliente clienteLogueado;
    private final Turno turnoAPagar;
    private float montoCalculado;

    private JPanel contentPane;
    private JTextField txtMonto;
    private JComboBox<String> cmbMetodoPago;
    private JButton btnPagar;
    private JButton btnCancelar;

    public DialogoProcesoPago(JFrame parent, Cliente cliente, GestorVentas gestorVentas, Turno turno, GestorTurnos gestorTurnos) {
        super(parent, "Procesar Pago - Cliente: " + cliente.getNombre() + " " + cliente.getApellido(), true);

        this.gestorVentas = gestorVentas;
        this.clienteLogueado = cliente;
        this.turnoAPagar = turno;
        this.gestorTurnos = gestorTurnos;

        if (turnoAPagar != null) {
            this.montoCalculado = this.gestorTurnos.simularAtencion(this.turnoAPagar);
        } else {
            this.montoCalculado = 0.0f;
        }

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

        txtMonto.setText(String.format("%.2f", this.montoCalculado));
        txtMonto.setEditable(false);
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
        double monto = this.montoCalculado;
        IMetodoPago metodo = getMetodoPagoSeleccionado();

        if (metodo == null || monto <= 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un método de pago. Monto: $" + String.format("%.2f", monto), "Error de Pago", JOptionPane.ERROR_MESSAGE);
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
            boolean exito = gestorVentas.procesarYRegistrarPago(clienteLogueado, totalPagado, metodo);

            if (exito) {
                JOptionPane.showMessageDialog(this, String.format("Pago de $%.2f registrado con éxito.", totalPagado), "Pago Exitoso", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPagar) {
            realizarPago();
        }
    }
}