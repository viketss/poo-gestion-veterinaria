package Gestores;

import modelado.Personas.Cliente;
import java.util.List;

public class GestorClientes {
    // clientes de la veterinaria
    private List<Cliente> listaClientes;

    // constructor
    public GestorClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    // metodos
    public Cliente buscarCliente(String nombre) {
        for (Cliente cliente : this.listaClientes) {
            if (cliente.getNombre().equals(nombre)) {
                System.out.println("Buscando cliente: " + nombre);
                System.out.println("Cliente encontrado con éxito.");
                return cliente; // Retorna el objeto Cliente completo
            }
        }
        System.out.println("Cliente no encontrado.");
        return null; // Retorna null si no se encontró coincidencia
    }
    
    public void agregarCliente(Cliente nuevoCliente){
        listaClientes.add(nuevoCliente);
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
}