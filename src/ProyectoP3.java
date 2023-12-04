import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProyectoP3 {

    static class Caso {
        int montoRecaudado;
        List<Integer> grupo;

        public Caso(int montoRecaudado, List<Integer> grupo) {
            this.montoRecaudado = montoRecaudado;
            this.grupo = grupo;
        }
    }
    

    public static List<Caso> leerEntrada(Scanner scanner) {
        List<Caso> casos = new ArrayList<>();
        int numCasos = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea después del número de casos

        for (int casoIndex = 0; casoIndex < numCasos; casoIndex++) {
            List<Integer> montosAportados = Arrays.stream(scanner.nextLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            int numPersonas = montosAportados.size();
            Map<Integer, List<Integer>> conocidos = new HashMap<>();

            for (int persona = 1; persona <= numPersonas; persona++) {
                if (scanner.hasNextLine()) {
                    conocidos.put(persona, Arrays.stream(scanner.nextLine().split(" ")).map(Integer::parseInt).collect(Collectors.toList()));
                }
            }

            casos.add(procesarCaso(montosAportados.stream().mapToInt(Integer::intValue).toArray(), conocidos));
        }

        return casos;
    }

    public static Map<Integer, List<Integer>> grafoComplementario(Map<Integer, List<Integer>> grafo) {
        int maxPersona = grafo.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        Map<Integer, List<Integer>> complementario = new HashMap<>();

        for (int persona = 1; persona <= maxPersona; persona++) {
            Set<Integer> conocidos = new HashSet<>(grafo.getOrDefault(persona, Collections.emptyList()));
            Set<Integer> complementoConocidos = new HashSet<>(IntStream.rangeClosed(1, maxPersona).boxed().collect(Collectors.toList()));
            complementoConocidos.removeAll(conocidos);
            complementoConocidos.remove(persona);

            complementario.put(persona, new ArrayList<>(complementoConocidos));
        }

        return complementario;
    }

    public static Set<Integer> heuristicaConjuntoIndependiente(Map<Integer, List<Integer>> grafo, int[] montosAportados) {
        List<Integer> personasOrdenadas = grafo.keySet().stream()
                .sorted(Comparator.comparingInt((Integer persona) -> -montosAportados[persona - 1]).thenComparingInt(persona -> grafo.get(persona).size()))
                .collect(Collectors.toList());

        Set<Integer> conjuntoIndependiente = new HashSet<>();
        for (int persona : personasOrdenadas) {
            if (grafo.get(persona).stream().anyMatch(conocido -> !conjuntoIndependiente.contains(conocido))) {
                conjuntoIndependiente.add(persona);
            }
        }

        return conjuntoIndependiente;
    }

    public static Caso procesarCaso(int[] montosAportados, Map<Integer, List<Integer>> conocidos) {
        Map<Integer, List<Integer>> grafoComp = grafoComplementario(conocidos);
        Set<Integer> conjuntoIndependiente = heuristicaConjuntoIndependiente(grafoComp, montosAportados);
        List<Integer> grupo = IntStream.rangeClosed(1, montosAportados.length)
                .filter(persona -> !conjuntoIndependiente.contains(persona))
                .boxed()
                .collect(Collectors.toList());

        int sumaMontos = grupo.stream().mapToInt(persona -> montosAportados[persona - 1]).sum();
        return new Caso(sumaMontos, grupo);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Caso> casos = leerEntrada(scanner);

        for (Caso caso : casos) {
            System.out.print(caso.montoRecaudado + " ");
            System.out.println(caso.grupo.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
