package Vista;

import modelado.Personas.Cliente;
import Gestores.GestorClientes;
import Persistencia.GestorPersistencia;
import Gestores.GestorVentas;
import javax.swing.*;

public class DialogoLogin extends JDialog {

    private JPanel contentPane;
    private JTextField txtUsuario;
    private JPasswordField pwdDni;
    private JButton btnIngresar;
    private JButton btnRegistrarme;
    private JButton IngresarAdmin; // Botón para el flujo de administrador

    private GestorClientes gc;
    private GestorPersistencia gp;
    private GestorVentas gvtas;

    private boolean accesoExitoso = false;

    private Cliente clienteLogueado = null;


    public DialogoLogin(JFrame parent, GestorClientes gc, GestorPersistencia gp, GestorVentas gvtas) {
        super(parent, "Ingreso Cliente Patitas Felices", true);
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.gc = gc;
        this.gp = gp;
        this.gvtas = gvtas;

        btnIngresar.addActionListener(e -> onIngresar());
        btnRegistrarme.addActionListener(e -> onRegistrarme());

        if (IngresarAdmin != null) {
            IngresarAdmin.addActionListener(e -> onIngresarAdmin());
        }

        pack();
        setLocationRelativeTo(parent);
    }


    private void onIngresar() {
        String nombre = txtUsuario.getText().trim();
        String dniStr = new String(pwdDni.getPassword());

        // La lógica del administrador se mueve al botón dedicado onIngresarAdmin()

        try {
            long dni = Long.parseLong(dniStr);

            Cliente clienteEncontrado = this.gc.buscarLogin(nombre, dni);

            if (clienteEncontrado != null) {
                accesoExitoso = true;
                clienteLogueado = clienteEncontrado;
                dispose();
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Nombre o DNI incorrectos. Intente de nuevo.",
                    "Error de Acceso",
                    JOptionPane.ERROR_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El DNI debe ser un número válido.",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private void onIngresarAdmin() {
        // Lanzamos el diálogo secundario, pasándole 'this' (que es un JDialog)
        new DialogoClaveAdmin(
                this,       // Pasa el JDialog actual
                this.gvtas,
                this.gc,
                this.gp
        ).setVisible(true);
    }


    private void onRegistrarme() {
        DialogoRegistrar dialogoRegistro = new DialogoRegistrar(null, this.gc, this.gp);
        dialogoRegistro.setVisible(true);

        if (dialogoRegistro.isRegistroExitoso()) {
            JOptionPane.showMessageDialog(this,
                    "Registro completado. Ahora ingrese con su Nombre y DNI.",
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            txtUsuario.setText("");
            pwdDni.setText("");
        }
    }

    public boolean isAccesoExitoso() {
        return accesoExitoso;
    }
    public void setAccesoExitoso(boolean estado) {
        this.accesoExitoso = estado;
    }

    public Cliente getClienteLogueado() {
        return clienteLogueado;
    }
}