package SOLID.OyL;

public class PagoTarjeta implements IMetodoPago {
    
    public double pagar(double importe) {
        System.out.println("Pago con tarjeta implica un recargo del 5%.");
        double totalTarjetaRecargo = importe * 1.05;
        System.out.println("Pago con tarjeta: $" + totalTarjetaRecargo);
        return totalTarjetaRecargo;
    }
}
