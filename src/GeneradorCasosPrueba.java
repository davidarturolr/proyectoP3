import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GeneradorCasosPrueba {

    public static void generarCasosPrueba(String archivoSalida, int numCasos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida))) {
            writer.println(numCasos);

            for (int i = 0; i < numCasos; i++) {
                int numPersonas = 5000; // Cambia estos valores según tus necesidades

                // Generar montos aportados
                for (int j = 0; j < numPersonas; j++) {
                    writer.print(getRandomInt(100, 2000) + " ");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public static void main(String[] args) {
        // Cambia el valor de 'numCasos' según la cantidad de casos de prueba que deseas generar
        int numCasos = 1;
        generarCasosPrueba("caso_prueba5k.txt", numCasos);
    }
}
