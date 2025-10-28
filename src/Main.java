import Gestores.GestorClientes;
import Gestores.GestorVeterinarios;
import Gestores.GestorTurnos;
import Persistencia.GestorPersistencia;
import modelado.HistoriaClinica.Medicamento;
import modelado.HistoriaClinica.Tratamiento;
import modelado.Mascotas.Mascota;
import modelado.Mascotas.TipoMascota;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("--- Bienvenido a Patitas Felices ---");

        try {
            //  INICIALIZACIÓN DE GESTORES Y DEPENDENCIAS
            System.out.println("Inicializando el sistema");

            GestorPersistencia gestorPersistencia = new GestorPersistencia();
            List<Cliente> listaInicialClientes = new ArrayList<>();
            List<Veterinario> listaInicialVeterinarios = new ArrayList<>();
            GestorClientes gestorClientes = new GestorClientes(listaInicialClientes);
            GestorVeterinarios gestorVeterinarios = new GestorVeterinarios(listaInicialVeterinarios);
            GestorTurnos gestorTurnos = new GestorTurnos(gestorPersistencia,gestorClientes, gestorVeterinarios); // Pasamos los otros gestores


            //Inicializacion de clases
            System.out.println("Registrando nuevo cliente");
            Cliente juan = new Cliente("Juan","Perez",12345678,new ArrayList<Mascota>(),1123455678);
            gestorClientes.agregarCliente(juan);
            System.out.println("Registrando nueva mascota");
            Mascota titoDeJuan = new Mascota("Tito","Galgo", TipoMascota.PERRO,juan, true, 7);
            juan.agregarMascota(titoDeJuan);
            System.out.println("Mascota registrada");
            System.out.println(juan);
            Veterinario esteban = new Veterinario("Esteban","Hernandez",98765432, "Cirugia Canina",20000,null);
            gestorVeterinarios.agregarVeterinario(esteban);
            Medicamento medicamentoNuevo = new Medicamento("Jeringa",5414, 1);
            List<Medicamento> medicamentosDelTratamiento = new ArrayList<>();
            medicamentosDelTratamiento.add(medicamentoNuevo);
            Tratamiento tratamiento = new Tratamiento("Analisis de sangre", 3000, medicamentosDelTratamiento);

            //Funciones principales
            gestorTurnos.solicitarTurno("Juan","Tito", "Esteban", "31 de Octubre");
            esteban.aplicarTratamiento(tratamiento, titoDeJuan);
            tratamiento.getCostoBase();
            tratamiento.calcularCostoTotal();

            // Persistencia
            try{
                System.out.println("Iniciando carga de datos");
                System.out.println("Guardando datos");
                gestorPersistencia.guardarClientes();
                gestorPersistencia.guardarVeterinarios();
                gestorPersistencia.guardarVeterinarios();

                System.out.println("Imprimiendo datos");
                gestorPersistencia.cargarClientes();
                gestorPersistencia.cargarVeterinarios();
                gestorPersistencia.cargarTurnos();

            } catch (Exception e){
                System.err.println("Error inesperado " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error inesperado, cerrando el sistema" + e);
        }

        System.out.println("Hasta luego.");
    }
}