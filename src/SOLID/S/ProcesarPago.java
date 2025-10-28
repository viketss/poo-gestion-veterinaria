package SOLID.S;
import SOLID.OyL.IMetodoPago;

public class ProcesarPago implements IMetodoPago {
    // atributos
    private IMetodoPago metodoPago;

    // constructor
    public ProcesarPago(IMetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public void pagar(double monto) {
        if(this.metodoPago != null) {
            this.metodoPago.pagar(monto);
        } else {
            System.out.println("No se ha especificado un metodo de pago.");
        }
    }

}
