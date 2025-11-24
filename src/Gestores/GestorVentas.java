package Gestores;
import java.util.ArrayList;
import java.util.List;
import modelado.Personas.Cliente;
import SOLID.OyL.IMetodoPago;
import SOLID.S.ProcesarPago;
import Persistencia.GestorPersistencia;

public class GestorVentas {

    private final GestorPersistencia gp;
    private final List<String> historialPagosLeidos;


    public GestorVentas(GestorPersistencia persistencia) {
        this.gp = persistencia;
        this.historialPagosLeidos = cargarHistorialPagos();
    }

    private List<String> cargarHistorialPagos() {
        return gp.cargarPagos();
    }


    public double calcularTotalFinal(double montoBase, IMetodoPago metodoPago) {
        if (metodoPago == null) {
            return montoBase;
        }
        return metodoPago.pagar(montoBase);
    }

    public boolean procesarYRegistrarPago(Cliente cliente, double montoSubtotal, IMetodoPago metodoPagoSeleccionado, String fechaTurno) {
        if (cliente == null || metodoPagoSeleccionado == null) {
            System.out.println("Error: Datos incompletos.");
            return false;
        }

        ProcesarPago procesador = new ProcesarPago(metodoPagoSeleccionado);
        double totalPagado = procesador.pagar(montoSubtotal);

        long dniCliente = cliente.getDni();
        String metodoPagoNombre = metodoPagoSeleccionado.getClass().getSimpleName();

        gp.guardarPago(dniCliente, totalPagado, metodoPagoNombre, fechaTurno);

        String nuevoRegistro = dniCliente + ";" + totalPagado + ";" + metodoPagoNombre + ";" + fechaTurno;
        this.historialPagosLeidos.add(nuevoRegistro); // Descomentar si usas esta lista

        System.out.println("Pago registrado. Fecha Turno: " + fechaTurno);
        return true;
    }

    public List<String[]> getHistorialPagosEstructurado() {
        List<String> lineasPagos = gp.cargarPagos();
        List<String[]> historial = new ArrayList<>();

        for (String linea : lineasPagos) {
            String[] partes = linea.split(";");

            if (partes.length == 4) {
                historial.add(partes);
            }
            else if (partes.length == 3) {
                String[] filaAdaptada = {partes[0], partes[1], partes[2], "S/D"};
                historial.add(filaAdaptada);
            }
            else {
                System.err.println("Línea ignorada: " + linea);
            }
        }
        return historial;
    }

    public List<String> getHistorialPagos() {
        return historialPagosLeidos;
    }
}