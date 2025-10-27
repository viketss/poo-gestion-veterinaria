package modelado.HistoriaClinica;

import java.util.List;

public class Tratamiento {
    // atributos:
    private String descripcion;
    private float costoBase;
    private List<Medicamento> medicamentos; // 1 tratamiento .. n medicamentos - asociacion

    // constructor
    public Tratamiento(String descripcion, float costoBase, List<Medicamento> medicamentos) {
        this.descripcion = descripcion;
        this.costoBase = costoBase;
        this.medicamentos = medicamentos; // asociacion
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
    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }
    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    // metodos
    public float calcularCostoTotal() {
        return 0;
    }

    public void administrarMedicamento(Medicamento medicamento) {
        System.out.println("El tratamiento ha sido realizado para con el medicamento: " +medicamento);
    }

    // tostring
    @Override
    public String toString() {
        return "Tratamiento [descripcion=" + descripcion + ", costoBase=" + costoBase + ", medicamentos=" + medicamentos
                + "]";
    }

}
