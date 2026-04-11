import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        try {

            Map<Integer, String> nombresProductos = new HashMap<>();
            Map<Integer, Integer> preciosProductos = new HashMap<>();
            Map<String, Integer> totalVendedores = new HashMap<>();
            Map<String, Integer> totalProductos = new HashMap<>();

            String linea;

            // =========================
            // LEER PRODUCTOS
            // =========================
            BufferedReader brProductos = new BufferedReader(new FileReader("productos.txt"));

            while ((linea = brProductos.readLine()) != null) {
                String[] partes = linea.split(";");
                int id = Integer.parseInt(partes[0]);
                nombresProductos.put(id, partes[1]);
                preciosProductos.put(id, Integer.parseInt(partes[2]));
            }
            brProductos.close();

            // =========================
            // LEER VENDEDORES
            // =========================
            BufferedReader brVendedores = new BufferedReader(new FileReader("vendedores.txt"));

            while ((linea = brVendedores.readLine()) != null) {
                String[] partes = linea.split(";");
                String nombre = partes[2];
                totalVendedores.put(nombre, 0);
            }
            brVendedores.close();

            // =========================
            // LEER ARCHIVOS DE VENTAS
            // =========================
            File carpeta = new File(System.getProperty("user.dir"));
            File[] archivos = carpeta.listFiles();

            for (File archivo : archivos) {

                if (archivo.getName().endsWith(".txt") &&
                        !archivo.getName().equals("productos.txt") &&
                        !archivo.getName().equals("vendedores.txt") &&
                        !archivo.getName().startsWith("reporte")) {

                    BufferedReader br = new BufferedReader(new FileReader(archivo));

                    // Obtener nombre del vendedor desde el nombre del archivo
                    String nombreArchivo = archivo.getName().replace(".txt", "");
                    String vendedor = nombreArchivo.split("_")[0];

                    if (!totalVendedores.containsKey(vendedor)) {
                        totalVendedores.put(vendedor, 0);
                    }

                    br.readLine(); // saltar encabezado

                    while ((linea = br.readLine()) != null) {

                        String[] partes = linea.split(";");
                        int idProducto = Integer.parseInt(partes[0]);
                        int cantidad = Integer.parseInt(partes[1]);

                        int precio = preciosProductos.get(idProducto);
                        int total = precio * cantidad;

                        // acumular vendedor
                        totalVendedores.put(vendedor,
                                totalVendedores.get(vendedor) + total);

                        // acumular producto
                        String nombreProducto = nombresProductos.get(idProducto);
                        totalProductos.put(nombreProducto,
                                totalProductos.getOrDefault(nombreProducto, 0) + cantidad);
                    }

                    br.close();
                }
            }

            // =========================
            // REPORTE VENDEDORES
            // =========================
            PrintWriter writer1 = new PrintWriter(new FileWriter("reporte_vendedores.txt"));

            totalVendedores.entrySet().stream()
                    .sorted((a, b) -> b.getValue() - a.getValue())
                    .forEach(e -> writer1.println(e.getKey() + ";" + e.getValue()));

            writer1.close();

            // =========================
            // REPORTE PRODUCTOS
            // =========================
            PrintWriter writer2 = new PrintWriter(new FileWriter("reporte_productos.txt"));

            totalProductos.entrySet().stream()
                    .sorted((a, b) -> b.getValue() - a.getValue())
                    .forEach(e -> writer2.println(e.getKey() + ";" + e.getValue()));

            writer2.close();

            System.out.println("Reportes generados correctamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}