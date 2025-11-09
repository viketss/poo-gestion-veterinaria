package Persistencia;
import modelado.HistoriaClinica.Turno;
import modelado.Mascotas.Mascota;
import modelado.Mascotas.TipoMascota;
import modelado.Personas.Cliente;
import modelado.Personas.Veterinario;

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
    private static final String ARCHIVO_PAGOS = "pagos.txt";

    //Metodos de escritura
    public void guardarClientes(List<Cliente> clientes) {
        // Usamos 'false' para sobrescribir todo el archivo
        try (FileWriter writer = new FileWriter(ARCHIVO_CLIENTES, false)) {

            for (Cliente cliente : clientes) {

                StringBuilder sb = new StringBuilder();

                sb.append(cliente.getNombre()).append(";")
                        .append(cliente.getApellido()).append(";")
                        .append(cliente.getDni()).append(";")
                        .append(cliente.getTelefono()).append(";")
                        .append(cliente.getMascotas().size()).append(";");


                for (Mascota mascota : cliente.getMascotas()) {
                    sb.append(mascota.getNombre()).append(",")
                            .append(mascota.getRaza()).append(",")
                            .append(mascota.getEspecie()).append(",")
                            .append(mascota.getEdad()).append(",")
                            .append(mascota.isVacunado()).append("|");
                }

                // chequeo de índice y eliminación del último separador "|"
                if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == '|') {
                    sb.setLength(sb.length() - 1);
                }

                writer.write(sb.toString() + "\n");
            }

            System.out.println("Datos guardados con éxito en clientes.txt");

        } catch (IOException e) {
            System.err.println("Error fatal al escribir en clientes.txt: " + e.getMessage());
        }
    }

    public void guardarVeterinarios(List<Veterinario> veterinarios) {
        try (FileWriter writer = new FileWriter(ARCHIVO_VETERINARIOS, false)) {

            for (Veterinario v : veterinarios) {

                String linea = v.getNombre() + ";"
                        + v.getApellido() + ";"
                        + v.getDni() + ";"
                        + v.getEspecialidad() + ";"
                        + v.getSueldo();

                writer.write(linea + "\n");
            }

            System.out.println("+ Datos de veterinarios guardados con éxito.");

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo. ABORTANDO: " + e.getMessage());
        }
    }


    public void guardarTurnos(List<modelado.HistoriaClinica.Turno> turnos) {
        try (FileWriter writer = new FileWriter(ARCHIVO_TURNOS, false)) {

            for (modelado.HistoriaClinica.Turno turno : turnos) {

                String linea = turno.getFecha() + ";"
                        + turno.getVeterinario().getDni() + ";"
                        + turno.getMascota().getDueno().getNombre() + ";"
                        + turno.getMascota().getNombre();

                writer.write(linea + "\n");
            }

            System.out.println("Turnos guardados con éxito en turnos.txt");

        } catch (IOException e) {
            System.err.println("Error al guardar turnos: " + e.getMessage());
        }
    }
    public void guardarPago(long dniCliente, double montoFinal, String metodoPago) {

        try (FileWriter writer = new FileWriter(ARCHIVO_PAGOS, true)) {
            String linea = dniCliente + ";" + montoFinal + ";" + metodoPago;
            writer.write(linea + "\n");

            System.out.println("Pago guardado con éxito.");

        } catch (IOException e) {
            System.err.println("Error al escribir en pagos.txt: " + e.getMessage());
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

                String nombre = partesCliente[0];
                String apellido = partesCliente[1];
                long dni = Long.parseLong(partesCliente[2]);
                long telefono = Long.parseLong(partesCliente[3]);
                int numMascotas = Integer.parseInt(partesCliente[4]);

                Cliente cliente = new Cliente(nombre, apellido, dni, new ArrayList<>(), telefono);

                if (partesCliente.length > 5 && numMascotas > 0) {
                    String[] datosMascotas = partesCliente[5].split("\\|");

                    for (String datosMascota : datosMascotas) {
                        String[] mData = datosMascota.split(",");

                        if (mData.length == 5) {
                            Mascota mascota = new Mascota(
                                    mData[0],
                                    mData[1],
                                    TipoMascota.valueOf(mData[2]),
                                    cliente,
                                    Boolean.parseBoolean(mData[4]),
                                    Integer.parseInt(mData[3])
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
        return clientesCargados;
    }

    public List<Veterinario> cargarVeterinarios() {
        List<Veterinario> veterinariosCargados = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_VETERINARIOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes.length >= 5) {
                    Veterinario v = new Veterinario(
                            partes[0],
                            partes[1],
                            Long.parseLong(partes[2]),
                            partes[3],
                            Float.parseFloat(partes[4])
                    );
                    veterinariosCargados.add(v);
                }
            }
            System.out.println("Carga de veterinarios finalizada. " + veterinariosCargados.size() + " veterinarios recuperados.");
        } catch (IOException e) {
            System.err.println("Archivo de veterinarios no encontrado. Iniciando con lista vacía.");
        } catch (Exception e) {
            System.err.println("Error al parsear datos de veterinario: " + e.getMessage());
        }
        return veterinariosCargados;
    }

    public List<Turno> cargarTurnos() {
        List<Turno> turnosCargados = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_TURNOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("Línea de turno leída: " + linea);
            }
        } catch (IOException e) {
            System.err.println("Archivo de turnos no encontrado. Iniciando con lista vacía.");
        }

        return turnosCargados;
    }
    public List<String> cargarPagos() {
        List<String> lineasPagos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_PAGOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineasPagos.add(linea);
            }
            System.out.println("Carga de historial de pagos finalizada. " + lineasPagos.size() + " registros recuperados.");
        } catch (IOException e) {
            System.err.println("Archivo de pagos no encontrado. Iniciando con historial vacío.");
        }
        return lineasPagos;
    }
}