package Gestores;

import modelado.HistoriaClinica.Medicamento;
import modelado.HistoriaClinica.Tratamiento;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GestorMedicamentos {

    private final List<Medicamento> inventarioBase;
    private final Random random;

    public GestorMedicamentos() {
        this.inventarioBase = inicializarInventarioBase();
        this.random = new Random();
    }

    private List<Medicamento> inicializarInventarioBase() {
        List<Medicamento> medicamentos = new ArrayList<>();

        medicamentos.add(new Medicamento("Carprofeno", 250.00f, 7));
        medicamentos.add(new Medicamento("Amoxicilina_Vet", 450.00f, 10));
        medicamentos.add(new Medicamento("Praziquantel", 300.00f, 1));
        medicamentos.add(new Medicamento("Vitaminico_B12", 150.00f, 30));
        medicamentos.add(new Medicamento("Ketoconazol_Crema", 200.00f, 14));

        return medicamentos;
    }
    public Tratamiento simularAsignacionTratamiento(String descripcion, float costoBase) {

        List<Medicamento> medicamentosAsignados = new ArrayList<>();

        // Elegir el primer medicamento al azar
        int index1 = random.nextInt(inventarioBase.size());
        medicamentosAsignados.add(inventarioBase.get(index1));

        // Elegir el segundo medicamento al azar, asegurando que no sea el mismo
        int index2 = random.nextInt(inventarioBase.size());
        while (index2 == index1) {
            index2 = random.nextInt(inventarioBase.size());
        }
        medicamentosAsignados.add(inventarioBase.get(index2));

        // Crear el Tratamiento (constructor: descripcion, costoBase, medicamentos)
        Tratamiento tratamiento = new Tratamiento(descripcion, costoBase, medicamentosAsignados);

        return tratamiento;
    }
}