package Gestores;

import modelado.Personas.Veterinario;
import Persistencia.GestorPersistencia;
import java.util.List;

public class GestorVeterinarios {
    // ATRIBUTOS
    private final GestorPersistencia gp;
    private List<Veterinario> listaVeterinarios;

    // CONSTRUCTOR
    public GestorVeterinarios(List<Veterinario> listaVeterinarios, GestorPersistencia gp) {
        this.listaVeterinarios = listaVeterinarios;
        this.gp = gp;
    }

    // METODOS
    public Veterinario buscarVeterinarioPorNombre(String nombreVeterinario) {
        for (Veterinario veterinario : this.listaVeterinarios) {
            if (veterinario.getNombre().equals(nombreVeterinario)) {
                System.out.println("Veterinario encontrado con éxito.");
                return veterinario;
            }
        }
        System.out.println("Veterinario no encontrado.");
        return null;
    }

    public Veterinario buscarVeterinarioPorDni(long dni) {
        for (Veterinario v : listaVeterinarios) {
            if (v.getDni() == dni) {
                return v;
            }
        }
        return null;
    }

    public void agregarVeterinario(Veterinario nuevoVeterinario){
        listaVeterinarios.add(nuevoVeterinario);
        this.gp.guardarVeterinarios(this.listaVeterinarios);
    }

    // GETTERS Y SETTERS
    public List<Veterinario> getVeterinarios() {
        return listaVeterinarios;
    }
}