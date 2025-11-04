package Persistencia;
import modelado.Mascotas.Mascota;
import modelado.Mascotas.TipoMascota;
import modelado.Personas.Cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class GestorPersistencia {

    private static final String ARCHIVO_CLIENTES = "clientes.txt";
    private static final String ARCHIVO_VETERINARIOS = "veterinarios.txt";
    private static final String ARCHIVO_TURNOS = "turnos.txt";

    //Metodos de escritura
    public void guardarClientes(List<Cliente> clientes) {
        // Usamos 'false' para sobrescribir todo el archivo
        try (FileWriter writer = new FileWriter(ARCHIVO_CLIENTES, false)) {

            for (Cliente cliente : clientes) {

                StringBuilder sb = new StringBuilder();

                // 1. Construcción de los datos del Cliente
                sb.append(cliente.getNombre()).append(";")
                        .append(cliente.getApellido()).append(";")
                        .append(cliente.getDni()).append(";")
                        .append(cliente.getTelefono()).append(";")
                        .append(cliente.getMascotas().size()).append(";");

                // 2. Construcción de los datos de las Mascotas
                for (Mascota mascota : cliente.getMascotas()) {
                    sb.append(mascota.getNombre()).append(",")
                            // Asumo que getRaza() y getEspecie() son correctos
                            .append(mascota.getRaza()).append(",")
                            .append(mascota.getEspecie()).append(",")
                            .append(mascota.getEdad()).append(",")
                            .append(mascota.isVacunado()).append("|");
                }

                // 3. Chequeo de índice y eliminación del último separador "|"
                //    Soluciona el error de Index -1 y Index Out of Bounds
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '|') {
                    sb.setLength(sb.length() - 1);
                }

                // 4. ¡LA LÍNEA CRÍTICA: ESCRIBIR EN EL ARCHIVO!
                writer.write(sb.toString() + "\n");
            }

            System.out.println("Datos guardados con éxito en clientes.txt");

        } catch (IOException e) {
            System.err.println("Error fatal al escribir en clientes.txt: " + e.getMessage());
        }
    }

    public void guardarVeterinarios() {
        String veterinario = "NOMBRE: Esteban|APELLIDO: Hernandez|DNI: 98765432|ESPECIALIDAD: Cirugia Canina|SUELDO: $30000";
        try (FileWriter writer = new FileWriter(ARCHIVO_VETERINARIOS)) {
            writer.write(veterinario + "\n");
            System.out.println("+ Datos del veterinario guardados." );
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo.ABORTANDO"+ "\n" + e.getMessage());
        }
    }

    public void guardarTurnos() {
        String turno = "PACIENTE: Fido|CONSULTA: Vomita la comida|TRATAMIENTO: Se le da un medicamento|FECHA: 27/10/2025|ID: 1234|VETERINARIO: Esteban|";

        try (FileWriter writer = new FileWriter(ARCHIVO_TURNOS)) {
            writer.write(turno + "\n");
            System.out.println("+ Turno guardado con exito." );
        } catch (IOException e) {
            System.err.println("Error guardando el turno" + "\n" + e.getMessage(    // Definición de los nombres de archivos .txt
));
        }
    }

    //Metodos de lectura
    public List<Cliente> cargarClientes() {
        List<Cliente> clientesCargados = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CLIENTES))) {
            String linea;
            while ((linea = reader.readLine()) != null) {

                String[] partesCliente = linea.split(";");

                if (partesCliente.length < 5) continue;

                // 1. Datos del Cliente
                String nombre = partesCliente[0];
                String apellido = partesCliente[1];
                long dni = Long.parseLong(partesCliente[2]);
                long telefono = Long.parseLong(partesCliente[3]);
                int numMascotas = Integer.parseInt(partesCliente[4]);

                Cliente cliente = new Cliente(nombre, apellido, dni, new ArrayList<>(), telefono);

                // 2. Datos de las Mascotas (A partir del índice 5)
                if (partesCliente.length > 5 && numMascotas > 0) {
                    String[] datosMascotas = partesCliente[5].split("\\|");

                    for (String datosMascota : datosMascotas) {
                        String[] mData = datosMascota.split(",");

                        // Formato Mascota: NombreMascota,Raza,Tipo,Edad,Vacunado
                        if (mData.length == 5) {
                            Mascota mascota = new Mascota(
                                    mData[0], // Nombre
                                    mData[1], // Raza
                                    TipoMascota.valueOf(mData[2]), // TipoMascota
                                    cliente,
                                    Boolean.parseBoolean(mData[4]), // Vacunado
                                    Integer.parseInt(mData[3])      // Edad
                            );
                            cliente.agregarMascota(mascota);
                        }
                    }
                }

                clientesCargados.add(cliente);
            }
            System.out.println("Carga de clientes finalizada. " + clientesCargados.size() + " clientes recuperados.");
        } catch (IOException e) {
            System.err.println("Archivo de clientes no encontrado. Iniciando con lista vacía.");
        } catch (Exception e) {
            System.err.println("Error al parsear datos de cliente/mascota: " + e.getMessage());
        }
        return clientesCargados; // <-- ¡AQUÍ ESTÁ LA DEVOLUCIÓN!
    }


    public void cargarVeterinarios() {
        System.out.println("Imprimiendo los datos en:" + ARCHIVO_VETERINARIOS);

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_VETERINARIOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea); // Imprime la línea leída
            }
        } catch (IOException e) {
            System.err.println("Error al leer " + ARCHIVO_VETERINARIOS + ": " + e.getMessage());
        }
        System.out.println("Impresion finalizada.");
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
        System.out.println("Impresion finalizada.");
    }
}