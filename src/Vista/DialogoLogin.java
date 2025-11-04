package Vista;

import javax.swing.*;

public class DialogoLogin extends JDialog {

    private JPanel contentPane;
    private JTextField txtUsuario;
    private JPasswordField pwdDni;
    private JButton btnIngresar;
    private JButton btnRegistrarme;

    private boolean accesoExitoso = false;

    public DialogoLogin(JFrame parent) {
        super(parent, "Ingreso Cliente Patitas Felices", true);
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 2. Conexión de listeners
        btnIngresar.addActionListener(e -> onIngresar());
        btnRegistrarme.addActionListener(e -> onRegistrarme());

        pack(); // Ajusta el tamaño de la ventana al contenido
        setLocationRelativeTo(parent);
    }

    private void onIngresar() {
        String usuario = txtUsuario.getText();
        String dni = new String(pwdDni.getPassword());

        // --- SIMULACIÓN DE LOGIN ---
        // Usamos el cliente simulado creado en Main: "cliente@demo.com" y DNI 12345678
        if (usuario.equals("Tomas") && dni.equals("45235050")) {
            accesoExitoso = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Usuario o DNI incorrectos. Intente con cliente@demo.com y 12345678",
                    "Error de Acceso",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onRegistrarme() {
        JOptionPane.showMessageDialog(this,
                "Abriendo formulario de Registro...",
                "Registro Pendiente",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean isAccesoExitoso() {
        return accesoExitoso;
    }
}