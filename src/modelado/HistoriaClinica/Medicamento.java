package modelado.HistoriaClinica;

public class Medicamento {
    // atributos:
    private String nombreMedicamento;
    private float precio;
    private int cantidadDiasDosis;

    // constructor
    public Medicamento(String nombreMedicamento, float precio, int cantidadDiasDosis) {
        this.nombreMedicamento = nombreMedicamento;
        this.precio = precio;
        this.cantidadDiasDosis = cantidadDiasDosis;
    }

    // getters y setters
    public String getNombreMedicamento() {
        return nombreMedicamento;
    }
    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }
    public float getPrecio() {
        return precio;
    }
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public int getCantidadDiasDosis() {
        return cantidadDiasDosis;
    }
    public void setCantidadDiasDosis(int cantidadDiasDosis) {
        this.cantidadDiasDosis = cantidadDiasDosis;
    }

    // metodos:
    public float calcularCostoPorDias() {
        return precio * cantidadDiasDosis;
    }

    // tostring
    @Override
    public String toString() {
        return "# Medicamento: " +
        "\n- Nombre: " + nombreMedicamento + 
        "\n- Precio: " + precio + 
        "\n- Cantidad de dias a aplicar la dosis: " + cantidadDiasDosis;
    }
}
