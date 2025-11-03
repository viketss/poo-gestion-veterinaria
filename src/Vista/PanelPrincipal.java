package Vista;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane; // Incluido para la pestaña Clientes

public class PanelPrincipal {

    // Componente Raíz
    private JPanel panelPrincipal;

    // Contenedor de Pestañas
    private JTabbedPane tabbedPane1;

    // Contenido de la Pestaña "Clientes"
    // Asumimos que agregaste un JSplitPane dentro de la pestaña Clientes
    private JSplitPane splitPaneClientes;
    private JPanel panelFormularioCliente; // Panel Izquierdo
    private JPanel panelListaClientes;     // Panel Derecho

    // Constructor vacío (usado por VentanaPrincipal)
    public PanelPrincipal() {

    }

    // El gancho para el JFrame
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    // El resto de los métodos y variables generados por IntelliJ... (omito el código generado)
}