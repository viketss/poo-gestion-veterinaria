package Vista;

import javax.swing.*;

import Gestores.GestorMascota;
import Gestores.GestorClientes;
import modelado.Personas.Cliente;
import modelado.Mascotas.TipoMascota;

public class VentanaAgregarMascota extends JFrame {

    // ATRIBUTOS DE LA VISTA
    private JPanel contentPane;
    private JTextField txtNomMascota;
    private JComboBox<String> cmbTipoMascota;
    private JTextField txtRaza;
    private JCheckBox chkVacunado;
    private JTextField txtEdad;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private final GestorMascota gestorMascota;
    private final GestorClientes gestorClientes;
    private final Cliente clienteActual;

    public VentanaAgregarMascota(Cliente clienteActual, GestorMascota gestorMascota, GestorClientes gestorClientes) {
        super("Agregar Mascota - Cliente: " + clienteActual.getNombre());
        this.clienteActual = clienteActual;
        this.gestorMascota = gestorMascota;
        this.gestorClientes = gestorClientes;


        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        setupListeners();

        pack();
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        cmbTipoMascota.removeAllItems();
        for (TipoMascota tipo : TipoMascota.values()) {
            cmbTipoMascota.addItem(tipo.toString());
        }
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> onGuardarMascota());

        if(btnCancelar != null) {
            btnCancelar.addActionListener(e -> dispose());
        }
    }

    private void onGuardarMascota() {
        try {
            String nombre = txtNomMascota.getText().trim();
            String raza = txtRaza.getText().trim();
            String tipoString = (String) cmbTipoMascota.getSelectedItem();
            boolean vacunado = chkVacunado.isSelected();
            int edad = Integer.parseInt(txtEdad.getText().trim());

            TipoMascota especie = TipoMascota.valueOf(tipoString);

            if (nombre.isEmpty() || raza.isEmpty() || tipoString == null) {
                JOptionPane.showMessageDialog(this, "Todos los campos obligatorios deben estar completos.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (gestorClientes.mascotaYaExiste(clienteActual, nombre)) {
                JOptionPane.showMessageDialog(this,
                        "Ya existe una mascota con el nombre '" + nombre + "' registrada a su nombre.",
                        "Error de Registro",
                        JOptionPane.ERROR_MESSAGE);
                return; // Detener el proceso
            }

            gestorMascota.agregarMascotaACliente(
                    clienteActual,
                    nombre,
                    raza,
                    especie,
                    vacunado,
                    edad
            );

            JOptionPane.showMessageDialog(this,
                    "Mascota '" + nombre + "' agregada con éxito.",
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error de validación: " + ex.getMessage(), "Error de Negocio", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado al registrar: " + ex.getMessage(), "Error Grave", JOptionPane.ERROR_MESSAGE);
        }
    }
}