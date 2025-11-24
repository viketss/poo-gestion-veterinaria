package Gestores;

import modelado.Personas.Cliente;
import Persistencia.GestorPersistencia;
import java.util.List;

public class GestorClientes {
    // ATRIBUTOS
    private List<Cliente> listaClientes;
    private final GestorPersistencia gestorPersistencia;

    // CONSTRUCTOR
    public GestorClientes(List<Cliente> listaClientes, GestorPersistencia gp) {
        this.listaClientes = listaClientes;
        this.gestorPersistencia = gp;
    }

    // GETTERS Y SETTERS
    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    // METODOS
    public Cliente buscarCliente(String nombre) {
        for (Cliente cliente : this.listaClientes) {
            if (cliente.getNombre().equals(nombre)) {
                System.out.println("Buscando cliente: " + nombre);
                System.out.println("Cliente encontrado con éxito.");
                return cliente;
            }
        }
        System.out.println("Cliente no encontrado.");
        return null;
    }

    public boolean agregarCliente(Cliente cliente) {
        if (existeClienteConDNI(cliente.getDni())) {
            System.out.println("Error: Ya existe un cliente con el DNI " + cliente.getDni());
            return false;
        }
        listaClientes.add(cliente);

        gestorPersistencia.guardarClientes(listaClientes);
        System.out.println("Cliente " + cliente.getNombre() + " registrado y guardado.");
        return true;
    }

    public boolean existeClienteConDNI(long dni) {
        for (Cliente c : this.listaClientes) {
            if (c.getDni() == dni) {
                return true;
            }
        }
        return false;
    }

    public Cliente buscarLogin(String nombre, long dni) {
        for (Cliente cliente : this.listaClientes) {
            if (cliente.getNombre().equals(nombre) && cliente.getDni() == dni) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente buscarClientePorDni(long dni) {
        for (Cliente c : listaClientes) {
            if (c.getDni() == dni) {
                return c;
            }
        }
        return null;
    }

    public boolean mascotaYaExiste(modelado.Personas.Cliente cliente, String nombreMascota) {
        if (cliente == null || nombreMascota == null || nombreMascota.trim().isEmpty()) {
            return false;
        }

        // Busqueda dentro de la lista de mascotas del cliente actual
        for (modelado.Mascotas.Mascota m : cliente.getMascotas()) {
            // la busqueda ignora mayusculas y minusculas
            if (m.getNombre().trim().equalsIgnoreCase(nombreMascota.trim())) {
                return true; // se encuentra duplicado
            }
        }
        return false; // unico nombre para este cliente
    }
}