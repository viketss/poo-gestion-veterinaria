package Gestores;

import modelado.Personas.Veterinario;
import Persistencia.GestorPersistencia;
import java.util.List;

public class GestorVeterinarios {

    private final GestorPersistencia gp;
    private List<Veterinario> listaVeterinarios;


    public GestorVeterinarios(List<Veterinario> listaVeterinarios, GestorPersistencia gp) {
        this.listaVeterinarios = listaVeterinarios;
        this.gp = gp;
    }

    // metodos
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

    public void agregarVeterinario(Veterinario nuevoVeterinario){
        listaVeterinarios.add(nuevoVeterinario);
        this.gp.guardarVeterinarios(this.listaVeterinarios);
    }

    public List<Veterinario> getVeterinarios() {
        return listaVeterinarios;
    }
}