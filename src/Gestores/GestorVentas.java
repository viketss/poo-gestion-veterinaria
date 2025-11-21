package Gestores;
import java.util.List;
import modelado.Personas.Cliente;
import SOLID.OyL.IMetodoPago;
import SOLID.S.ProcesarPago;
import Persistencia.GestorPersistencia;

public class GestorVentas {

    private GestorPersistencia persistencia;
    private List<String> historialPagosLeidos;


    public GestorVentas(GestorPersistencia persistencia) {
        this.persistencia = persistencia;
        this.historialPagosLeidos = cargarHistorialPagos();
    }

    private List<String> cargarHistorialPagos() {
        return persistencia.cargarPagos();
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

        /* GRASP BAJO ACOPLAMIENTO: GestorVentas depende de interfaz
        IMetodoPago, no de PagoTarjeta
        * */
        ProcesarPago procesador = new ProcesarPago(metodoPagoSeleccionado);
        double totalPagado = procesador.pagar(montoSubtotal);

        long dniCliente = cliente.getDni();
        String metodoPagoNombre = metodoPagoSeleccionado.getClass().getSimpleName();

        persistencia.guardarPago(dniCliente, totalPagado, metodoPagoNombre);

        String nuevoRegistro = dniCliente + ";" + totalPagado + ";" + metodoPagoNombre;
        this.historialPagosLeidos.add(nuevoRegistro);

        System.out.println("Pago finalizado y registrado. Total: $" + totalPagado);

        return true;
    }

    public List<String> getHistorialPagos() {
        return historialPagosLeidos;
    }
}