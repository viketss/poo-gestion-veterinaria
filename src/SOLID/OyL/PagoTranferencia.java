package SOLID.OyL;

public class PagoTranferencia implements IMetodoPago {
    // TODO mejorar?
    public void pagar(double importe) {
        System.out.println("Pago por transferencia bancaria: " + importe);
    }
}
