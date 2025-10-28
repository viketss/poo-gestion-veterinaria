package Gestores;

import modelado.Personas.Veterinario;
import java.util.List;

public class GestorVeterinarios {

    // Asumimos que esta lista ya está inicializada y cargada con datos.
    private List<Veterinario> listaVeterinarios;

    public GestorVeterinarios(List<Veterinario> listaVeterinarios) {
        this.listaVeterinarios = listaVeterinarios;
    }

    public Veterinario buscarVeterinarioPorNombre(String nombreVeterinario) {
        for (Veterinario veterinario : this.listaVeterinarios) {
            if (veterinario.getNombre().equals(nombreVeterinario)) {
                System.out.println("Veterinario encontrado.");
                return veterinario; // Retorna el objeto Veterinario completo
            }
        }
        System.out.println("Veterinario no encontrado.");
        return null; // Retorna null si no se encontró coincidencia
    }

    public void agregarVeterinario(Veterinario nuevoVeterinario){
        listaVeterinarios.add(nuevoVeterinario);
    }
}