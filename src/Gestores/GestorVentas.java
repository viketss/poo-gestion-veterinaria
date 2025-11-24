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

    public boolean procesarYRegistrarPago(Cliente cliente, double montoSubtotal, IMetodoPago metodoPagoSeleccionado) {
        if (cliente == null || metodoPagoSeleccionado == null) {
            System.out.println("Error: Cliente o método de pago no especificado.");
            return false;
        }

        ProcesarPago procesador = new ProcesarPago(metodoPagoSeleccionado);
        double totalPagado = procesador.pagar(montoSubtotal);

        long dniCliente = cliente.getDni();
        String metodoPagoNombre = metodoPagoSeleccionado.getClass().getSimpleName();

        gp.guardarPago(dniCliente, totalPagado, metodoPagoNombre);

        String nuevoRegistro = dniCliente + ";" + totalPagado + ";" + metodoPagoNombre;
        this.historialPagosLeidos.add(nuevoRegistro);

        System.out.println("Pago finalizado y registrado. Total: $" + totalPagado);

        return true;
    }

    public List<String[]> getHistorialPagosEstructurado() {
        List<String> lineasPagos = gp.cargarPagos();
        List<String[]> historial = new ArrayList<>();

        for (String linea : lineasPagos) {
            String[] partes = linea.split(";");

            if (partes.length == 3) {
                historial.add(partes);
            } else {
                System.err.println("Advertencia: Línea de pago corrupta o incompleta: " + linea);
            }
        }
        return historial;
    }

    public List<String> getHistorialPagos() {
        return historialPagosLeidos;
    }
}