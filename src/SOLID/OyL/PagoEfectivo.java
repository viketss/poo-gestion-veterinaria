package SOLID.OyL;
import SOLID.I.IDescuento;

public class PagoEfectivo implements IMetodoPago, IDescuento {
    public void pagar(double importe) {
        System.out.println("Aplicando descuento por pago en efectivo...");
        double totalEfectivoDescuento = aplicarDescuento(importe);
        System.out.println("Pago en efectivo: $" + totalEfectivoDescuento);
    }

    public double aplicarDescuento(double importe) {
        double descuento = importe * 0.10; // 10% de descuento por pago en efectivo
        return importe - descuento;
    }
}
