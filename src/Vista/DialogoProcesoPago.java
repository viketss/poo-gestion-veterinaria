package Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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

    private List<Turno> turnosPendientes;


    private JPanel contentPane;
    private JTextField txtMonto;
    private JComboBox<String> cmbMetodoPago;
    private JComboBox<String> cmbElegirTurno; // <-- Nombre del componente
    private JButton btnPagar;
    private JButton btnCancelar;

    public DialogoProcesoPago(JFrame parent, Cliente cliente, GestorVentas gestorVentas, GestorTurnos gestorTurnos) {
        super(parent, "Procesar Pago - Cliente: " + cliente.getNombre() + " " + cliente.getApellido(), true);

        this.gestorVentas = gestorVentas;
        this.clienteLogueado = cliente;
        this.gestorTurnos = gestorTurnos;

        this.turnosPendientes = this.gestorTurnos.getTurnosPendientesPorCliente(clienteLogueado);

        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setupComponentData();
        setupListeners();

        pack();
        setLocationRelativeTo(parent);
    }

    private void setupComponentData() {
        // Inicializar ComboBox de Método de Pago
        String[] metodos = {"Seleccione Método", "Efectivo (10% Desc.)", "Tarjeta (5% Recargo)", "Transferencia (Sin costo)"};
        cmbMetodoPago.removeAllItems();
        for (String metodo : metodos) {
            cmbMetodoPago.addItem(metodo);
        }

        // Inicializar ComboBox de Turnos
        cmbElegirTurno.removeAllItems();
        txtMonto.setEditable(false);

        if (turnosPendientes.isEmpty()) {
            cmbElegirTurno.addItem("No hay turnos pendientes");
            btnPagar.setEnabled(false);
            txtMonto.setText("0.00");
        } else {
            for (Turno t : turnosPendientes) {


                String nombreVeterinario = t.getVeterinario().getNombre() + " " + t.getVeterinario().getApellido();
                String info = String.format("ID: %d - %s (%s) | Vet: %s",
                        t.getIdTurno(),
                        t.getFecha(),
                        t.getHorario().toString(),
                        nombreVeterinario);


                cmbElegirTurno.addItem(info);
            }
            btnPagar.setEnabled(true);

            recalcularMonto();
        }
    }

    private void setupListeners() {
        btnPagar.addActionListener(this);
        if (btnCancelar != null) {
            btnCancelar.addActionListener(e -> dispose());
        }

        cmbElegirTurno.addActionListener(e -> recalcularMonto());
    }

    private void recalcularMonto() {
        int selectedIndex = cmbElegirTurno.getSelectedIndex();
        if (selectedIndex != -1 && !turnosPendientes.isEmpty()) {
            Turno turnoSeleccionado = turnosPendientes.get(selectedIndex);

            float nuevoMonto = gestorTurnos.simularAtencionYAsignarCosto(turnoSeleccionado);
            txtMonto.setText(String.format("%.2f", nuevoMonto));
        } else {
            txtMonto.setText("0.00");
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
        int selectedIndex = cmbElegirTurno.getSelectedIndex();
        if (selectedIndex == -1 || turnosPendientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un turno pendiente.", "Error de Pago", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Turno turnoSeleccionado = turnosPendientes.get(selectedIndex);
        double monto = Double.parseDouble(txtMonto.getText().replace(",", "."));

        IMetodoPago metodo = getMetodoPagoSeleccionado();

        if (metodo == null || monto <= 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un método de pago. Monto: $" + String.format("%.2f", monto), "Error de Pago", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalPagado = gestorVentas.calcularTotalFinal(monto, metodo);

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                String.format("Confirma pago de $%.2f (Turno ID %d)?", totalPagado, turnoSeleccionado.getIdTurno()),
                "Confirmación de Pago",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            boolean exitoVentas = gestorVentas.procesarYRegistrarPago(clienteLogueado, totalPagado, metodo);

            if (exitoVentas) {

                this.gestorTurnos.finalizarYMarcarComoPagado(turnoSeleccionado);

                JOptionPane.showMessageDialog(this, String.format("Pago de $%.2f registrado con éxito. Turno Finalizado.", totalPagado), "Pago Exitoso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
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