package Gestores;

import modelado.HistoriaClinica.Medicamento;
import modelado.HistoriaClinica.Tratamiento;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GestorMedicamentos {
    // ATRIBUTOS
    private final List<Medicamento> inventarioBase;
    private final Random random;

    // CONSTRUCTOR
    public GestorMedicamentos() {
        this.inventarioBase = inicializarInventarioBase();
        this.random = new Random();
    }

    // METODOS
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

        // elegir medicamento al azar (gestionado por el veterinario)
        int index1 = random.nextInt(inventarioBase.size());
        medicamentosAsignados.add(inventarioBase.get(index1));

        // elegir el segundo medicamento (chequea que no se repita)
        int index2 = random.nextInt(inventarioBase.size());
        while (index2 == index1) {
            index2 = random.nextInt(inventarioBase.size());
        }
        medicamentosAsignados.add(inventarioBase.get(index2));

        // crear el tratamiento
        Tratamiento tratamiento = new Tratamiento(descripcion, medicamentosAsignados);

        return tratamiento;
    }
}