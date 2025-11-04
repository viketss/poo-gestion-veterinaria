package Vista;

import modelado.Personas.Cliente;
import Gestores.GestorClientes;
import javax.swing.*;

public class DialogoLogin extends JDialog {

    private JPanel contentPane;
    private JTextField txtUsuario;
    private JPasswordField pwdDNI;
    private JButton btnIngresar;
    private JButton btnRegistrarme;
    private GestorClientes gc;

    private boolean accesoExitoso = false;

    private Cliente clienteLogueado = null;

    public DialogoLogin(JFrame parent, GestorClientes gc) {
        super(parent, "Ingreso Cliente Patitas Felices", true);
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.gc = gc;

        btnIngresar.addActionListener(e -> onIngresar());
        btnRegistrarme.addActionListener(e -> onRegistrarme());

        pack();
        setLocationRelativeTo(parent);
    }

    private void onIngresar() {
        String nombre = txtUsuario.getText().trim(); // Limpiamos espacios
        String dni = new String(pwdDNI.getPassword());

        if (nombre.equals("Cliente") && dni.equals("12345678")) {
            accesoExitoso = true;
            clienteLogueado = new Cliente(nombre, "Demo", 12345678, new java.util.ArrayList<>(), 1123456789);

            dispose();
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Nombre o DNI incorrectos. Intente con 'Cliente' y '12345678'",
                "Error de Acceso",
                JOptionPane.ERROR_MESSAGE);
    }

    private void onRegistrarme() {
        DialogoRegistrar dialogoRegistro = new DialogoRegistrar(null, this.gc);
        dialogoRegistro.setVisible(true);

        if (dialogoRegistro.isRegistroExitoso()) {
            JOptionPane.showMessageDialog(this,
                    "Registro completado. Ahora ingrese con su Nombre y DNI.",
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            txtUsuario.setText("");
            pwdDNI.setText("");
        }
    }

    public boolean isAccesoExitoso() {
        return accesoExitoso;
    }

    public Cliente getClienteLogueado() {
        return clienteLogueado;
    }
}