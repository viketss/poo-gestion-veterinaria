import Gestores.GestorClientes;
import Gestores.GestorVeterinarios;
import Gestores.GestorTurnos;
import Persistencia.GestorPersistencia;
import SOLID.OyL.IMetodoPago;
import SOLID.OyL.PagoEfectivo;
import SOLID.S.ProcesarPago;
import modelado.HistoriaClinica.Medicamento;
import modelado.HistoriaClinica.Tratamiento;
import modelado.Mascotas.Mascota;
import modelado.Mascotas.TipoMascota;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;

import java.util.ArrayList;
import java.util.List;

public class Main {
    // TODO ver que se impriman todas las clases
    // TODO poner todos los metodos de cada clase
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
            System.out.println("# Registrando nuevo cliente");
            Cliente juan = new Cliente("Juan","Perez",12345678,new ArrayList<Mascota>(),1123455678);
            gestorClientes.agregarCliente(juan);
            System.out.println("# Registrando nueva mascota");
            Mascota titoDeJuan = new Mascota("Tito","Galgo", TipoMascota.PERRO,juan, true, 7);
            juan.agregarMascota(titoDeJuan);
            System.out.println("Mascota registrada con exito");
            System.out.println(juan);

            Veterinario esteban = new Veterinario("Esteban","Hernandez",98765432, "Cirugia Canina",20000,null);
            gestorVeterinarios.agregarVeterinario(esteban);
            System.out.println("# Veterinarios disponibles: " + esteban.getNombre() + " " + esteban.getApellido());

            Medicamento medicamentoNuevo = new Medicamento("Vacuna de rabia",18000, 1);
            List<Medicamento> medicamentosDelTratamiento = new ArrayList<>();
            medicamentosDelTratamiento.add(medicamentoNuevo);
            Tratamiento tratamiento = new Tratamiento("Analisis de sangre", 62000, medicamentosDelTratamiento);
            
            //Funciones principales
            // gestion de turnos
            gestorTurnos.solicitarTurno("Juan","Tito", "Esteban", "31 de Octubre");
            esteban.aplicarTratamiento(tratamiento, titoDeJuan);
            tratamiento.getCostoBase();
            tratamiento.calcularCostoTotal();

            // pago de la consulta
            double montoAPagar = tratamiento.getCostoBase() + medicamentoNuevo.getPrecio();
            System.out.println("\n# El monto total a pagar por la consulta es: $" + montoAPagar);
            IMetodoPago metodoEfectivo = new PagoEfectivo();
            ProcesarPago procesarPagoEfectivo = new ProcesarPago(metodoEfectivo);
            procesarPagoEfectivo.pagar(montoAPagar);
            System.out.println("Pago realizado con exito.\n");

            // Persistencia
            try{
                System.out.println("# Guardando datos en el sistema... ");
                gestorPersistencia.guardarClientes();
                gestorPersistencia.guardarVeterinarios();
                gestorPersistencia.guardarTurnos();

                gestorPersistencia.cargarClientes();
                gestorPersistencia.cargarVeterinarios();
                gestorPersistencia.cargarTurnos();

            } catch (Exception e){
                System.err.println("Error inesperado " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error inesperado, cerrando el sistema" + e);
        }

        System.out.println("Cerrando el sistema...");
    }
}