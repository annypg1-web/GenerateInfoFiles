import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Clase para generar archivos de prueba del proyecto.
 * Entrega 1 - Semana 3.
 * Integrantes: Anny Paola Perez Galvis, Bairon José Amaya Manzano, 
 * Gustavo Adolfo Jaramillo Mira, Yohan Cardona Suesca.
 */
public class GenerateInfoFiles {

    public static void main(String[] args) {
        try {
            // Generar archivo con los 4 integrantes del grupo
            createProductsFile(5);
            createSalesManInfoFile(4);
            
            // Ejemplo de archivo de ventas para un integrante (Gustavo)
            createSalesMenFile(4, "Gustavo_Jaramillo", 10102020L);
            
            System.out.println("Archivos creados con éxito.");
        } catch (Exception e) {
            System.out.println("Error en la generación: " + e.getMessage());
        }
    }

    public static void createProductsFile(int productsCount) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("productos.txt"))) {
            for (int i = 1; i <= productsCount; i++) {
                writer.println(i + ";Producto_" + i + ";" + (1000 * i));
            }
        } catch (Exception e) {
            System.out.println("Error al crear productos.");
        }
    }

    public static void createSalesManInfoFile(int salesmanCount) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("vendedores.txt"))) {
            // Lista de integrantes en minúsculas
            String[] nombres = {
                "anny paola perez galvis", 
                "bairon josé amaya manzano", 
                "gustavo adolfo jaramillo mira", 
                "yohan cardona suesca"
            };

            for (int i = 0; i < salesmanCount; i++) {
                // Usamos un documento ficticio que cambie con el índice i
                long documento = 1000000 + i;
                // Formato requerido: Tipo;Cedula;NombreCompleto
                writer.println("CC;" + documento + ";" + nombres[i % nombres.length]);
            }
        } catch (Exception e) {
            System.out.println("Error al crear info de vendedores.");
        }
    }

    public static void createSalesMenFile(int randomSalesCount, String name, long id) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(name + "_" + id + ".txt"))) {
            writer.println("CC;" + id);
            
            for (int i = 0; i < randomSalesCount; i++) {
                int idProd = (int) (Math.random() * 5) + 1;
                int cant = (int) (Math.random() * 10) + 1;
                writer.println(idProd + ";" + cant);
            }
        } catch (Exception e) {
            System.out.println("Error al crear ventas del vendedor.");
        }
    }
}