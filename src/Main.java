import Gestores.GestorClientes;
import Gestores.GestorVeterinarios;
import Gestores.GestorTurnos;
import Persistencia.GestorPersistencia;
import modelado.Mascotas.Mascota;
import modelado.Mascotas.TipoMascota;
import modelado.Personas.Cliente;   // Necesario si quieres interactuar con el resultado
import modelado.Personas.Veterinario; // Necesario si quieres interactuar con el resultado

import java.util.ArrayList;
import java.util.List; // Necesario si quieres interactuar con listas

public class Main {

    public static void main(String[] args) {

        System.out.println("🐾 Bienvenido a Patitas Felices 🐾");

        try {
            //  INICIALIZACIÓN DE GESTORES Y DEPENDENCIAS
            System.out.println("Inicializando el sistema");

            GestorPersistencia gestorPersistencia = new GestorPersistencia();
            List<Cliente> listaInicialClientes = new ArrayList<>();
            List<Veterinario> listaInicialVeterinarios = new ArrayList<>();
            GestorClientes gestorClientes = new GestorClientes(listaInicialClientes);
            GestorVeterinarios gestorVeterinarios = new GestorVeterinarios(listaInicialVeterinarios);
            GestorTurnos gestorTurnos = new GestorTurnos(gestorPersistencia,gestorClientes, gestorVeterinarios); // Pasamos los otros gestores

            System.out.println("Sistema inicializados");


            // --- 2. CARGA DE DATOS INICIALES  ---
            System.out.println("Cargando datos desde archivos");

            // Llamamos a los métodos de carga (que solo imprimen por ahora)
            gestorPersistencia.cargarClientes();
            gestorPersistencia.cargarVeterinarios();
            gestorPersistencia.cargarTurnos();

            System.out.println("Carga de datos completada.");

            //3. Inicializacion de clases
            System.out.println("Registrando nuevo cliente");
            Cliente victoria = new Cliente("Victoria","Cuomo",44899034,new ArrayList<Mascota>(),1134666556);
            gestorClientes.agregarCliente(victoria);
            System.out.println("Registrando nueva mascota");
            Mascota titoDeVictoria = new Mascota("Tito","Galgo", TipoMascota.PERRO,victoria, true, 7);
            victoria.agregarMascota(titoDeVictoria);
            System.out.println("Mascota registrada");
            System.out.println(victoria);
            Veterinario tomas = new Veterinario("Tomas","Flory",45235050, "Cirugia Canina",30000,null);
            gestorVeterinarios.agregarVeterinario(tomas);

            //4. Funciones principales
            gestorTurnos.solicitarTurno("Victoria","Tito", "Tomas", "31 de Octubre");


        } catch (Exception e) {
            // Captura genérica por si alguna operación inesperada falla
            System.err.println("\n❌ ERROR INESPERADO EN LA EJECUCIÓN PRINCIPAL ❌");
            e.printStackTrace(); // Imprime el detalle completo del error
        }

        System.out.println("\n--- 👋 FIN DE LA EJECUCIÓN ---");
    }
}