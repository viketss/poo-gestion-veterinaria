package SOLID.OyL;
public interface IMetodoPago {
    /*
     Proposito: Procesa el pago por un monto especifico
     Parametros: 
        - importe: el monto a pagar
     */
    public abstract void pagar(double importe);
}
