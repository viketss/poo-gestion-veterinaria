package Vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import Gestores.GestorHistoriaClinica;
import modelado.Mascotas.Mascota;
import modelado.HistoriaClinica.Turno;
import modelado.HistoriaClinica.Tratamiento;
import modelado.HistoriaClinica.Medicamento;

public class DialogoHistoriaClinica extends JDialog {
    private JPanel contentPane;
    private JTextArea txtHistoria;
    private JButton btnCerrar;
    private JLabel lblTitulo;

    private Mascota mascota;
    private GestorHistoriaClinica gestorHistoria;

    public DialogoHistoriaClinica(JFrame parent, Mascota mascota, GestorHistoriaClinica gestorHistoria) {
        super(parent, "Historia Clínica", true);
        this.mascota = mascota;
        this.gestorHistoria = gestorHistoria;

        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 500); // Un poco más ancho para que entre la tabla
        setLocationRelativeTo(parent);

        configurarComponentes();
        cargarHistoriaClinica();
    }

    private void configurarComponentes() {
        lblTitulo.setText("HISTORIAL MÉDICO: " + mascota.getNombre().toUpperCase());
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));

        txtHistoria.setEditable(false);
        // Usamos Monospaced para que las columnas se alineen perfectamente
        txtHistoria.setFont(new Font("Monospaced", Font.PLAIN, 12));

        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarHistoriaClinica() {
        // 1. Generar datos simulados si la mascota no tiene historial
        gestorHistoria.generarHistorialSimulado(mascota);

        // 2. Obtener la lista de Turnos (Ingresos)
        List<Turno> ingresos = mascota.getHistoriaClinica().getHistorialDeTurnos();

        StringBuilder sb = new StringBuilder();

        // Encabezado de la "Tabla" de texto
        sb.append(String.format("%-12s | %-35s | %-30s\n", "FECHA", "TRATAMIENTO", "MEDICAMENTOS"));
        sb.append("------------------------------------------------------------------------------------------\n");

        if (ingresos.isEmpty()) {
            sb.append(" No se encontraron registros clínicos.\n");
        } else {
            for (Turno t : ingresos) {
                String fecha = t.getFecha();

                // Iterar sobre los tratamientos del turno
                for (Tratamiento trat : t.getTratamientos()) {

                    // --- CORRECCIÓN AQUÍ ---
                    // Accedemos al ENUM a través de getTipo()
                    String nombreTratamiento = trat.getDescripcion();

                    // Armar String de medicamentos
                    StringBuilder medsStr = new StringBuilder();
                    if (trat.getMedicamentos().isEmpty()) {
                        medsStr.append("-");
                    } else {
                        for (int i = 0; i < trat.getMedicamentos().size(); i++) {
                            Medicamento m = trat.getMedicamentos().get(i);
                            // Accedemos al nombre del medicamento (sea por Enum o String según tu clase)
                            medsStr.append(m.getNombreMedicamento()); // O m.getTipo().getNombre()
                            if (i < trat.getMedicamentos().size() - 1) {
                                medsStr.append(", ");
                            }
                        }
                    }

                    // Imprimir fila formateada
                    // %-35s reserva 35 caracteres para el tratamiento
                    sb.append(String.format("%-12s | %-35s | %s\n",
                            fecha,
                            cortarTexto(nombreTratamiento, 35),
                            medsStr.toString()
                    ));

                    fecha = ""; // Para que no repita la fecha si hay varios tratamientos el mismo día
                }
                sb.append("------------------------------------------------------------------------------------------\n");
            }
        }

        txtHistoria.setText(sb.toString());
        txtHistoria.setCaretPosition(0);
    }

    // Ayuda visual para cortar textos muy largos y que no rompan la tabla
    private String cortarTexto(String texto, int max) {
        if (texto.length() > max) {
            return texto.substring(0, max - 3) + "...";
        }
        return texto;
    }
}