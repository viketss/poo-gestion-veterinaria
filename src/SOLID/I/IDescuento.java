package SOLID.I;

public interface IDescuento {
    // GRASP PROTECTED VARIATIONS: se puede cambiar IDescuento, pero el sistema
    // esta protegido porque depende de la interfaz

    /*
     * Proposito: Aplica un descuento al importe dado
     * Parametros: double importe - El importe al que se le aplicará el descuento
     * Retorno: double - El importe después de aplicar el descuento
     */
    public double aplicarDescuento(double importe);

    /*
     * Proposito: Obtiene la tasa de descuento aplicada
     * Retorno: double - La tasa de descuento
     */
    public double tasaDescuento();
}
