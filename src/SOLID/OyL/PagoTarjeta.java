package SOLID.OyL;

public class PagoTarjeta implements IMetodoPago {
    // TODO mejorar?
    public void pagar(double importe) {
        System.out.println("Pago con tarjeta: " + importe);
    }
}
