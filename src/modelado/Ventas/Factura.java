package modelado.Ventas;


import SOLID.OyL.IMetodoPago;
import modelado.Personas.Cliente;


public class Factura {
    private Cliente cliente;
    private double subtotal;
    private double totalFinal;
    private IMetodoPago metodoPago;

    public Factura(Cliente cliente) {
        this.cliente = cliente;
        this.subtotal = 0.0;
        this.totalFinal = 0.0;
    }

    public void setMetodoPago(IMetodoPago metodoPago) {
        this.metodoPago = metodoPago;
        this.totalFinal = calcularTotalFinalConMetodo();
    }

    public double calcularTotalFinalConMetodo() {
        if (metodoPago == null) {
            return this.subtotal;
        }
        return this.subtotal;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTotalFinal() {
        return totalFinal;
    }

    public IMetodoPago getMetodoPago() {
        return metodoPago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}