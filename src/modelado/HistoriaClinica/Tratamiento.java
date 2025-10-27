package modelado.HistoriaClinica;


public class Tratamiento {
    // atributos:
    private String descripcion;
    private float costoBase;
    private Medicamento medicamento;

    // constructor
    public Tratamiento(String descripcion, float costoBase, Medicamento medicamento) {
        this.descripcion = descripcion;
        this.costoBase = costoBase;
        this.medicamento = medicamento;
    }

    // getters y setters
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public float getCostoBase() {
        return costoBase;
    }
    public void setCostoBase(float costoBase) {
        this.costoBase = costoBase;
    }
    public Medicamento getMedicamento() {
        return medicamento;
    }
    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    // metodos
    public float calcularCostoTotal() {
        return 0;
    }

    public void administrarMedicamento(Medicamento medicamento) {
        System.out.println("El tratamiento ha sido realizado para con el medicamento: " +medicamento);
    }


}
