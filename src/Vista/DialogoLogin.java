package Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class DialogoLogin extends JDialog {
    // Componentes del diseñador de IntelliJ
    private JPanel contentPane;
    private JTextField txtUsuario;
    private JPasswordField pwdContrasena;
    private JButton btnIngresar;
    private JPanel contentPame;
    private JButton btnRegistrarme; // ¡Asegúrate de que esta variable exista en el .form!

    private String rolUsuario = null;

    public DialogoLogin(JFrame parent) {
        super(parent, "Sistema Patitas Felices - Ingreso", true); // 'true' la hace modal
        setContentPane(contentPane);

        // Configuración básica del diálogo
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(btnIngresar);

        // Listeners
        btnIngresar.addActionListener(e -> onIngresar());
        btnRegistrarme.addActionListener(e -> onRegistrarme());

        // Muestra el diálogo
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void onIngresar() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(pwdContrasena.getPassword());

        // --- Lógica de Simulación de Autenticación ---
        if (usuario.equals("admin") && contrasena.equals("123")) {
            rolUsuario = "ADMIN"; // Acceso total a la gestión
            dispose();
        }
        // Simulación de cliente
        else if (usuario.equals("juan") && contrasena.equals("perez")) {
            rolUsuario = "CLIENTE"; // Acceso limitado a su historial
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Usuario o Contraseña incorrectos. ¡Vuelve a intentarlo!",
                    "Error de Acceso",
                    JOptionPane.ERROR_MESSAGE);
        }
        // Limpia la contraseña por seguridad
        Arrays.fill(pwdContrasena.getPassword(), '0');
    }

    private void onRegistrarme() {
        // Aquí iría el código para abrir un nuevo DialogoRegistro para Clientes.
        JOptionPane.showMessageDialog(this,
                "Formulario de Registro de Clientes no implementado aún. ¡Pronto lo haremos!",
                "Registro de Clientes",
                JOptionPane.INFORMATION_MESSAGE);
        // Por ahora, solo cerramos este mensaje.
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    // Método $$setupUI$$ y componentes generados por IntelliJ... (omito el código generado)
}