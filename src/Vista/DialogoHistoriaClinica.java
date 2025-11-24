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
        setSize(700, 500); // un poco mas ancho para que entre la tabla
        setLocationRelativeTo(parent);

        configurarComponentes();
        cargarHistoriaClinica();
    }

    private void configurarComponentes() {
        lblTitulo.setText("HISTORIAL MÉDICO: " + mascota.getNombre().toUpperCase());
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));

        txtHistoria.setEditable(false);
        // monospaced para que se las columnas esten bien alineadas
        txtHistoria.setFont(new Font("Monospaced", Font.PLAIN, 12));

        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarHistoriaClinica() {
        // 1.generar datos para el historial
        gestorHistoria.generarHistorial(mascota);

        // 2.obtener lista de turnos
        List<Turno> ingresos = mascota.getHistoriaClinica().getHistorialDeTurnos();

        StringBuilder sb = new StringBuilder();

        // encabezado de la tabla
        sb.append(String.format("%-12s | %-35s | %-30s\n", "FECHA", "TRATAMIENTO", "MEDICAMENTOS"));
        sb.append("------------------------------------------------------------------------------------------\n");

        if (ingresos.isEmpty()) {
            sb.append(" No se encontraron registros clínicos.\n");
        } else {
            for (Turno t : ingresos) {
                String fecha = t.getFecha();

                // iterar sobre los tratamientos del turno
                for (Tratamiento trat : t.getTratamientos()) {

                    // acceso al enum de tratamientos
                    String nombreTratamiento = trat.getDescripcion();

                    // string de medicamentos
                    StringBuilder medsStr = new StringBuilder();
                    if (trat.getMedicamentos().isEmpty()) {
                        medsStr.append("-");
                    } else {
                        for (int i = 0; i < trat.getMedicamentos().size(); i++) {
                            Medicamento m = trat.getMedicamentos().get(i);
                            // acceso al nombre del medicamento
                            medsStr.append(m.getNombreMedicamento());
                            if (i < trat.getMedicamentos().size() - 1) {
                                medsStr.append(", ");
                            }
                        }
                    }

                    // imprimir la fila formateada
                    sb.append(String.format("%-12s | %-35s | %s\n",
                            fecha,
                            cortarTexto(nombreTratamiento, 35),
                            medsStr.toString()
                    ));

                    fecha = ""; // para que no repita la fecha si hay varios tratamientos el mismo día
                }
                sb.append("------------------------------------------------------------------------------------------\n");
            }
        }

        txtHistoria.setText(sb.toString());
        txtHistoria.setCaretPosition(0);
    }

    // por si hay textos muy largos, que no se rompa el formato
    private String cortarTexto(String texto, int max) {
        if (texto.length() > max) {
            return texto.substring(0, max - 3) + "...";
        }
        return texto;
    }
}