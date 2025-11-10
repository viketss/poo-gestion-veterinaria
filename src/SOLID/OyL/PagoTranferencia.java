package SOLID.OyL;

public class PagoTranferencia implements IMetodoPago {
    public double pagar(double importe) {
        System.out.println("Procesando pago por transferencia bancaria...");
        System.out.println("Pago por transferencia bancaria: $" + importe);
        return importe;
    }
}
