package SOLID.S;
import SOLID.OyL.IMetodoPago;

public class ProcesarPago {
    // atributos
    private double importe;
    private String formaDePago;
    private IMetodoPago metodoPago;

    // constructor
    public ProcesarPago(double importe, String formaDePago, IMetodoPago metodoPago) {
        this.importe = importe;
        this.formaDePago = formaDePago;
        this.metodoPago = metodoPago;
    }

    // TODO mejorar
    public void procesarPago(double monto, String tipoPago) {
        if(formaDePago.equals("tarjeta")) {
            System.out.println("pago con tarjeta: " + importe);
            this.metodoPago.pagar(importe);
        } else if(formaDePago.equals("mercado pago")) {
            System.out.println("pago con mercado pago");
        } else {
            // manejo de error
            System.out.println("error");
        }
    }
}
