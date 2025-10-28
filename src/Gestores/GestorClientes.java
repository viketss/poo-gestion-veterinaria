package Gestores;

import modelado.Personas.Cliente;
import java.util.List;

public class GestorClientes {

    private List<Cliente> listaClientes;

    public GestorClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Cliente buscarCliente(String nombre) {
        for (Cliente cliente : this.listaClientes) {
            if (cliente.getNombre().equals(nombre)) {
                System.out.println("GESTION CLIENTES: Cliente encontrado.");
                return cliente; // Retorna el objeto Cliente completo
            }
        }
        System.out.println("GESTION CLIENTES: Cliente no encontrado.");
        return null; // Retorna null si no se encontró coincidencia
    }
    public void agregarCliente(Cliente nuevoCliente){
        listaClientes.add(nuevoCliente);
    }

}