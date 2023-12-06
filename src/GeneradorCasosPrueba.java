import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeneradorCasosPrueba {

    public static void generarCasosPrueba(String archivoSalida, int numCasos, int maxPersonas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida))) {
            writer.println(numCasos);

            Set<Integer> numerosPersonas = new HashSet<>();
            Random random = new Random();

            for (int i = 0; i < numCasos - 1; i++) {
                int numPersonas;
                do {
                    numPersonas = getRandomInt(1, maxPersonas);
                } while (numerosPersonas.contains(numPersonas));
                numerosPersonas.add(numPersonas);

                // Generar montos aportados
                for (int j = 0; j < numPersonas; j++) {
                    writer.print(getRandomInt(100, 1000) + " ");
                }
                writer.println();

                // Generar conocidos para cada persona
                for (int j = 1; j <= numPersonas; j++) {
                    int numConocidos = getRandomInt(1, numPersonas - 1);

                    for (int k = 0; k < numConocidos; k++) {
                        writer.print(getRandomInt(1, numPersonas) + " ");
                    }
                    writer.println();
                }
            }

            // Asegurar al menos un caso con 10 mil personas
            int numPersonas = maxPersonas;
            // Generar montos aportados
            for (int j = 0; j < numPersonas; j++) {
                writer.print(getRandomInt(100, 1000) + " ");
            }
            writer.println();

            // Generar conocidos para cada persona
            for (int j = 1; j <= numPersonas; j++) {
                int numConocidos = getRandomInt(1, numPersonas - 1);

                for (int k = 0; k < numConocidos; k++) {
                    writer.print(getRandomInt(1, numPersonas) + " ");
                }
                writer.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public static void main(String[] args) {
        int numCasos = 50;
        int maxPersonas = 10000;
        generarCasosPrueba("casos_prueba10k.txt", numCasos, maxPersonas);
    }
}
