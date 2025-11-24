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

    // componentes de la GUI
    private JPanel contentPane;
    private JTextField txtMonto;
    private JComboBox<String> cmbMetodoPago;
    private JComboBox<String> cmbElegirTurno;
    private JButton btnPagar;
    private JButton btnCancelar;
    private JTextArea txtDetalle; // componente para mostrar los medicamentos

    public DialogoProcesoPago(JFrame parent, Cliente cliente, GestorVentas gestorVentas, GestorTurnos gestorTurnos) {
        super(parent, "Procesar Pago - Cliente: " + cliente.getNombre() + " " + cliente.getApellido(), true);

        this.gestorVentas = gestorVentas;
        this.clienteLogueado = cliente;
        this.gestorTurnos = gestorTurnos;

        // obtener los turnos pendientes de este cliente
        this.turnosPendientes = this.gestorTurnos.getTurnosPendientesPorCliente(clienteLogueado);

        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setupComponentData();
        setupListeners();

        pack();
        setLocationRelativeTo(parent);
    }

    private void setupComponentData() {
        // configuracion de los metodos de pago
        String[] metodos = {"Seleccione Método", "Efectivo (10% Desc.)", "Tarjeta (5% Recargo)", "Transferencia (Sin costo)"};
        cmbMetodoPago.removeAllItems();
        for (String metodo : metodos) {
            cmbMetodoPago.addItem(metodo);
        }

        // configuracion componentes de texto
        txtMonto.setEditable(false);
        if (txtDetalle != null) {
            txtDetalle.setEditable(false);
            txtDetalle.setLineWrap(true);
            txtDetalle.setWrapStyleWord(true);
        }

        // cargar turnos pendientes
        cmbElegirTurno.removeAllItems();

        if (turnosPendientes.isEmpty()) {
            cmbElegirTurno.addItem("No hay turnos pendientes");
            btnPagar.setEnabled(false);
            txtMonto.setText("0.00");
            if (txtDetalle != null) txtDetalle.setText("Sin items a cobrar.");
        } else {
            for (Turno t : turnosPendientes) {
                // Formato: ID - fecha (horario) | vet: nombre apellido
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

        // recalcular monto y detalle al cambiar de turno
        cmbElegirTurno.addActionListener(e -> recalcularMonto());
    }

    /**
     * Simula la atención médica para el turno seleccionado, obtiene el costo total
     * y actualiza la caja de texto de monto y el area de detalle.
     */
    private void recalcularMonto() {
        int selectedIndex = cmbElegirTurno.getSelectedIndex();

        if (selectedIndex != -1 && !turnosPendientes.isEmpty()) {
            Turno turnoSeleccionado = turnosPendientes.get(selectedIndex);

            // calcular monto (logica en el gestor de turnos)
            float nuevoMonto = gestorTurnos.simularAtencionYAsignarCosto(turnoSeleccionado);
            txtMonto.setText(String.format("%.2f", nuevoMonto));

            // mostrar detalle de medicamentos
            if (txtDetalle != null) {
                txtDetalle.setText(turnoSeleccionado.obtenerDetalleMedicamentos());
            }

        } else {
            txtMonto.setText("0.00");
            if (txtDetalle != null) txtDetalle.setText("");
        }
    }

    private IMetodoPago getMetodoPagoSeleccionado() {
        String seleccion = (String) cmbMetodoPago.getSelectedItem();
        if (seleccion == null || seleccion.startsWith("Seleccione")) return null;

        if (seleccion.startsWith("Efectivo")) return new PagoEfectivo();
        else if (seleccion.startsWith("Tarjeta")) return new PagoTarjeta();
        else if (seleccion.startsWith("Transferencia")) return new PagoTranferencia();
        return null;
    }

    private void realizarPago() {
        int selectedIndex = cmbElegirTurno.getSelectedIndex();
        if (selectedIndex == -1 || turnosPendientes.isEmpty()) {
            return;
        }

        Turno turnoSeleccionado = turnosPendientes.get(selectedIndex);
        double monto = Double.parseDouble(txtMonto.getText().replace(",", "."));
        IMetodoPago metodo = getMetodoPagoSeleccionado();

        if (metodo == null || monto <= 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un método de pago válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // calculo final con el metodo de pago (SOLID OCP)
        double totalPagado = gestorVentas.calcularTotalFinal(monto, metodo);

        // confirmacion
        int respuesta = JOptionPane.showConfirmDialog(this,
                String.format("Total final: $%.2f\n¿Confirmar pago del Turno ID %d?", totalPagado, turnoSeleccionado.getIdTurno()),
                "Confirmar Pago", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            // registro en ventas
            boolean exito = gestorVentas.procesarYRegistrarPago(clienteLogueado, totalPagado, metodo);

            if (exito) {
                // finalización del turno (cambio de estado a PAGADO y se libera la agenda)
                gestorTurnos.finalizarYMarcarComoPagado(turnoSeleccionado);

                JOptionPane.showMessageDialog(this, "Pago exitoso. El turno ha sido finalizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el pago en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
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