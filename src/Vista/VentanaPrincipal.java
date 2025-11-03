package Vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class VentanaPrincipal extends JFrame {

    // Asumimos que esta clase maneja la vista del ADMIN/Veterinario (acceso completo)
    private PanelPrincipal panelPrincipal;

    public VentanaPrincipal() {
        super("Patitas Felices - Gestión Veterinaria (Acceso de Personal)");

        // Conexión del formulario diseñado
        panelPrincipal = new PanelPrincipal();
        setContentPane(panelPrincipal.getPanelPrincipal());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Mostrar el diálogo de login
            DialogoLogin login = new DialogoLogin(null);

            String rol = login.getRolUsuario();

            if (rol != null) {
                if (rol.equals("ADMIN")) {
                    // El personal accede a la Ventana Principal de Gestión
                    new VentanaPrincipal().setVisible(true);
                } else if (rol.equals("CLIENTE")) {
                    // El cliente accede a su Ventana simplificada
                    // ¡Necesitas crear la clase VentanaCliente!
                    System.out.println("Acceso Cliente. Faltaría crear VentanaCliente.");
                    // new VentanaCliente().setVisible(true);
                }
            } else {
                // Login cancelado/cerrado
                System.exit(0);
            }
        });
    }
}