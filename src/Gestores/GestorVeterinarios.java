package Gestores;

import modelado.Personas.Veterinario;
import java.util.List;

public class GestorVeterinarios {
    // atributos
    private List<Veterinario> listaVeterinarios;

    // constructor
    public GestorVeterinarios(List<Veterinario> listaVeterinarios) {
        this.listaVeterinarios = listaVeterinarios;
    }

    // metodos
    public Veterinario buscarVeterinarioPorNombre(String nombreVeterinario) {
        for (Veterinario veterinario : this.listaVeterinarios) {
            if (veterinario.getNombre().equals(nombreVeterinario)) {
                System.out.println("Veterinario encontrado con éxito.");
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