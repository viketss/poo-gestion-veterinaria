package Vista;

import modelado.Personas.Cliente;
import modelado.Mascotas.Mascota;
import modelado.Mascotas.TipoMascota;
import Gestores.GestorClientes;
import Persistencia.GestorPersistencia;

import javax.swing.*;
import java.util.ArrayList;

public class DialogoRegistrar extends JDialog {

    private JPanel panelPrincipal;

    private JTextField txtNombreCliente;
    private JTextField txtApellidoCliente;
    private JTextField txtDniCliente;
    private JTextField txtTelefonoCliente;

    private JTextField txtMascotaNombre;
    private JTextField txtRazaMascota;
    private JComboBox<TipoMascota> cmbTipoMascota;
    private JCheckBox chkVacunado;
    private JTextField txtEdadMascota;

    private JButton btnRegistrar;
    private JButton btnCancelar;

    private boolean registroExitoso = false;

    private GestorClientes gc;
    private GestorPersistencia gp;

    public DialogoRegistrar(JFrame parent, GestorClientes gc, GestorPersistencia gp) {
        super(parent, "Registro Nuevo Cliente y Mascota", true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.gc = gc;
        this.gp = gp;

        cmbTipoMascota.addItem(TipoMascota.PERRO);
        cmbTipoMascota.addItem(TipoMascota.GATO);
        cmbTipoMascota.addItem(TipoMascota.PAJARO);
        cmbTipoMascota.addItem(TipoMascota.ROEDOR);
        cmbTipoMascota.addItem(TipoMascota.REPTIL);
        cmbTipoMascota.addItem(TipoMascota.OTRO);

        btnRegistrar.addActionListener(e -> onRegistrar());
        btnCancelar.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    private void onRegistrar() {
        try {
            String nombre = txtNombreCliente.getText();
            String apellido = txtApellidoCliente.getText();
            long dni = Long.parseLong(txtDniCliente.getText());
            long telefono = Long.parseLong(txtTelefonoCliente.getText());

            String nomMascota = txtMascotaNombre.getText();
            String razaMascota = txtRazaMascota.getText();
            TipoMascota tipoMascota = (TipoMascota) cmbTipoMascota.getSelectedItem();
            boolean vacunado = chkVacunado.isSelected();
            int edad = Integer.parseInt(txtEdadMascota.getText());

            if (nombre.isEmpty() || dni == 0 || nomMascota.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre, DNI y Nombre de Mascota son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Cliente nuevoCliente = new Cliente(nombre, apellido, dni, new ArrayList<>(), telefono);
            Mascota nuevaMascota = new Mascota(nomMascota, razaMascota, tipoMascota, nuevoCliente, vacunado, edad);
            nuevoCliente.agregarMascota(nuevaMascota);

            boolean registroExitoso = this.gc.agregarCliente(nuevoCliente);

            if (registroExitoso) {
                JOptionPane.showMessageDialog(this, "¡Registro Exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                registroExitoso = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "ERROR: Ya existe un cliente registrado con ese DNI (" + dni + ").", "DNI Duplicado", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "DNI, Teléfono y Edad deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + e.getMessage(), "Error Grave", JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean isRegistroExitoso() {
        return registroExitoso;
    }
}