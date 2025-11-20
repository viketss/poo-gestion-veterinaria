package SOLID.OyL;
public interface IMetodoPago {
    /*
     Proposito: Procesa el pago por un monto especifico
     Parametros: 
        - importe: el monto a pagar
     */
    public abstract double pagar(double importe);

    // GRASP POLIMORFISMO: misma interfaz, diferente comportamiento para cada medio de pago
}
