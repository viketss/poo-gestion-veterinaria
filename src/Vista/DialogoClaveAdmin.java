package Vista;

import Gestores.GestorClientes;
import Gestores.GestorVentas;
import Persistencia.GestorPersistencia;
import javax.swing.*;

public class DialogoClaveAdmin extends JDialog {

    // Componentes del diálogo (deben coincidir con tu .form)
    private JPasswordField pwdClave;
    private JButton btnAceptar;
    private JPanel contentPane;
    private JLabel lblTitulo; // Asumimos que esta etiqueta existe

    // Dependencias
    private final JDialog dialogoLoginPadre; // Referencia al DialogoLogin para cerrarlo
    private final GestorVentas gvtas;
    private final GestorClientes gc;
    private final GestorPersistencia gp;

    private static final String CLAVE_ADMIN = "1234";

    public DialogoClaveAdmin(javax.swing.JDialog parent, GestorVentas gvtas, GestorClientes gc, GestorPersistencia gp) {
        // El diálogo es modal, bloqueando la interacción con la ventana padre (DialogoLogin)
        super(parent, "Clave de Administrador", true);

        this.dialogoLoginPadre = parent;
        this.gvtas = gvtas;
        this.gc = gc;
        this.gp = gp;

        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Listener
        if (btnAceptar != null) {
            btnAceptar.addActionListener(e -> onAceptar());
        }

        pack();
        setLocationRelativeTo(parent);
    }

    private void onAceptar() {
        String claveIngresada = new String(pwdClave.getPassword());

        if (claveIngresada.equals(CLAVE_ADMIN)) {

            // 1. Avisamos al padre que no debe matar el programa (flag true)
            if (dialogoLoginPadre instanceof DialogoLogin) {
                ((DialogoLogin) dialogoLoginPadre).setAccesoExitoso(true);
            }

            // 2. ¡ESTO ES LO QUE FALTABA! Abrimos la ventana de administración
            // Usamos las referencias (gvtas, gc, gp) que ya tenemos en esta clase
            VentanaAdmin adminWindow = new VentanaAdmin(this.gvtas, this.gc, this.gp);
            adminWindow.setVisible(true);

            // 3. Ahora sí, cerramos el diálogo de clave
            this.dispose();

            // 4. Y cerramos el login principal
            dialogoLoginPadre.dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Clave incorrecta.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            pwdClave.setText("");
        }
    }
}
