package SOLID.OyL;

public class PagoTarjeta implements IMetodoPago {
    // GRASP ALTA COHESION: clase PagoTarjeta solo implementa la logica
    // del pago con tarjeta
    public double pagar(double importe) {
        System.out.println("Pago con tarjeta implica un recargo del 5%.");
        double totalTarjetaRecargo = importe * 1.05;
        System.out.println("Pago con tarjeta: $" + totalTarjetaRecargo);
        return totalTarjetaRecargo;
    }
}
