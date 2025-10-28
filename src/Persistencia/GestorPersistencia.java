package Persistencia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestorPersistencia {

    private static final String ARCHIVO_CLIENTES = "clientes.txt";
    private static final String ARCHIVO_VETERINARIOS = "veterinarios.txt";
    private static final String ARCHIVO_TURNOS = "turnos.txt";

    //Metodos de escritura

    public void guardarClientes() {
        String cliente = "NOMBRE: Juan|APELLIDO: Perez|DNI: 12.345.678|MASCOTAS: Tito; Fido|TEL: 11-2345-5678";

        try (FileWriter writer = new FileWriter(ARCHIVO_CLIENTES)) {
            writer.write(cliente + "\n");
            System.out.println("Escribiendo datos del cliente");
        } catch (IOException e) {
            System.err.println("Error detectado: " + e.getMessage());
        }
    }

    public void guardarVeterinarios() {
        String veterinario = "NOMBRE: Esteban|APELLIDO: Hernandez|DNI: 98765432|ESPECIALIDAD: Cirugia Canina|SUELDO: $30000";
        try (FileWriter writer = new FileWriter(ARCHIVO_VETERINARIOS)) {
            writer.write(veterinario + "\n");
            System.out.println("Escribiendo los datos del veterinario." );
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo.ABORTANDO"+ "\n" + e.getMessage());
        }
    }

    public void guardarTurnos() {
        String turno = "PACIENTE: Fido|CONSULTA: Vomita la comida|TRATAMIENTO: Se le da un medicamento|FECHA: 27/10/2025|ID: 1234|VETERINARIO: Esteban|";

        try (FileWriter writer = new FileWriter(ARCHIVO_TURNOS)) {
            writer.write(turno + "\n");
            System.out.println("Guardando el turno");
        } catch (IOException e) {
            System.err.println("Error guardando el turno" + "\n" + e.getMessage(    // Definición de los nombres de archivos .txt
));
        }
    }

    //Metodos de lectura

    public void cargarClientes() {
        System.out.println("Imprimiendo los datos de: " + ARCHIVO_CLIENTES );
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CLIENTES))) {
            String linea;
            // Lee el archivo hasta que readLine() devuelva null (fin del archivo)
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea); // Imprime la línea leída
            }
        } catch (IOException e) {
            System.err.println("Error al leer " + ARCHIVO_CLIENTES + ": " + e.getMessage());
        }
        System.out.println("Fin");
    }

    public void cargarVeterinarios() {
        System.out.println("Imprimiendo los datos de:" + ARCHIVO_VETERINARIOS);

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_VETERINARIOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea); // Imprime la línea leída
            }
        } catch (IOException e) {
            System.err.println("Error al leer " + ARCHIVO_VETERINARIOS + ": " + e.getMessage());
        }
        System.out.println("Fin");
    }


    public void cargarTurnos() {
        System.out.println("Imprimiendo los datos de:" + ARCHIVO_TURNOS );
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_TURNOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea); // Imprime la línea leída
            }
        } catch (IOException e) {
            System.err.println("Error al leer " + ARCHIVO_TURNOS + ": " + e.getMessage());
        }
        System.out.println("Fin");
    }
}