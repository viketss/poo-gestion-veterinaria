package modelado.HistoriaClinica;

import java.util.List;

public class Tratamiento {
    // atributos:
    private String descripcion;
    private List<Medicamento> medicamentos; // 1 tratamiento .. n medicamentos - asociacion

    // constructor
    public Tratamiento(String descripcion, float costoBase, List<Medicamento> medicamentos) {
        this.descripcion = descripcion;
        this.medicamentos = medicamentos; // asociacion
    }

    // getters y setters
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }
    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    // metodos
    public float calcularCostoMedicamento() {
        float costoMedicamentos = 0;
        for (Medicamento m : medicamentos) {
            costoMedicamentos += m.calcularCostoPorDias(m.getCantidadDiasDosis());
        }
        return costoMedicamentos;
    }

    public void administrarMedicamento(Medicamento medicamento) {
        System.out.println("El tratamiento ha sido realizado con el medicamento: " +medicamento);
    }

    // tostring
    @Override
    public String toString() {
        return "\n# Tratamiento para tu mascota: " + 
        "\n- Descripcion: " + descripcion +
        "\n- Medicamentos: " + medicamentos;
    }
}
