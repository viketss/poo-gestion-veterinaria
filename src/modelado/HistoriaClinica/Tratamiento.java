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
        // GRASP INFORMATION EXPERT: tratamiento calcula costo total

        float costoTotal = costoBase;
        for (Medicamento m : medicamentos) {
            costoTotal += m.calcularCostoPorDias(m.getCantidadDiasDosis());
        }
        return costoTotal;
    }

    public void administrarMedicamento(Medicamento medicamento) {
        System.out.println("El tratamiento ha sido realizado con el medicamento: " +medicamento);
    }

    // tostring
    @Override
    public String toString() {
        return "\n# Tratamiento para tu mascota: " + 
        "\n- Descripcion: " + descripcion + 
        "\n- Costo Base del Tratamiento: " + costoBase + 
        "\n- Medicamentos: " + medicamentos;
    }
}
