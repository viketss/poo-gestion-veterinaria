package SOLID.OyL;
import SOLID.I.IDescuento;

public class PagoEfectivo implements IMetodoPago, IDescuento {
    // TODO mejorar?
    public void pagar(double importe) {
        System.out.println("Pago en efectivo: " + importe);
    }

    public double aplicarDescuento(double importe) {
        double descuento = importe * 0.05; // 5% de descuento por pago en efectivo
        return importe - descuento;
    }
}
