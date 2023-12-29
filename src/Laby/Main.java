package Laby;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            // Remplacez le chemin absolu suivant par le vôtre
            String filePath = "C:\\Users\\TRETEC\\eclipse-workspace\\ProjetAlgo\\src\\Laby\\graph.txt";
            System.out.println("Graph file path: " + filePath);

            WeightedGraph weightedGraph = readGraphFromFile(filePath);

            // Création d'une instance de AStar avec le nœud de départ et le nœud d'arrivée
            AStar astar = new AStar(weightedGraph.vertexlist.get(0), weightedGraph.vertexlist.get(5)); // À adapter selon vos besoins

            // Appel de la méthode solve et impression du résultat
            List<WeightedGraph.Vertex> path = astar.solve();
            System.out.println(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static WeightedGraph readGraphFromFile(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));

        boolean isGraphSection = false;
        HashMap<Integer, LinkedList<Integer>> graph = new HashMap<>();
        String metadata = "";
        int ncols = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.startsWith("==Metadata==")) {
                isGraphSection = false;
                continue;
            } else if (line.startsWith("==Graph==")) {
                isGraphSection = true;
                continue;
            }

            if (!isGraphSection) {
                metadata += line;
                if (line.contains("ncol=")) {
                    ncols = Integer.parseInt(line.split("=")[1]);
                }
            }

            if (isGraphSection && line.length() == ncols) {
                for (int i = 0; i < line.length(); i++) {
                    int vertex = i % ncols;
                    int color = mapColorToInt(String.valueOf(line.charAt(i)));

                    // Vérifiez si les indices sont valides
                    if (vertex < metadata.length() && color != -1) {
                        LinkedList<Integer> neighbors = graph.getOrDefault(vertex, new LinkedList<>());
                        if (!neighbors.contains(color)) {
                            neighbors.add(color);
                        }
                        graph.put(vertex, neighbors);
                    } else {
                        System.out.println("Error: Invalid vertex or color - vertex: " + vertex + ", color: " + color);
                    }
                }
            }
        }

        scanner.close();

        WeightedGraph weightedGraph = new WeightedGraph();
        for (int vertex : graph.keySet()) {
            int currentVertexIndex = mapColorToInt(metadata.charAt(vertex) + "");

            // Ajoutez le sommet avec l'index correct
            weightedGraph.addVertex(currentVertexIndex);

            LinkedList<Integer> neighbors = graph.get(vertex);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    int currentNeighborIndex = mapColorToInt(metadata.charAt(neighbor) + "");

                    // Assurez-vous de définir correctement les poids des arêtes en fonction des couleurs
                    double weight = computeEdgeWeight(currentVertexIndex, currentNeighborIndex);

                    // Ajoutez l'arête avec les indices corrects
                    weightedGraph.addEgde(
                            weightedGraph.vertexlist.get(currentVertexIndex),
                            weightedGraph.vertexlist.get(currentNeighborIndex),
                            weight
                    );
                }
            }
        }

        return weightedGraph;
    }

    private static double computeEdgeWeight(int color1, int color2) {
        // Exemple de calcul du poids en fonction des couleurs
        // Vous devez adapter cette logique en fonction de vos besoins réels

        // Utilisez les valeurs des couleurs pour déterminer le poids de l'arête
        // C'est juste un exemple, vous devriez adapter cela à votre propre logique
        if (color1 == 1 && color2 == 1) {
            return 1.0;
        } else if (color1 == 1 && color2 == 1000) {
            return 2.0;
        } else {
            return 0.0;
        }
    }

    private static int mapColorToInt(String color) {
        switch (color) {
            case "G":
                return 1;
            case "W":
                return 1000;
            case "B":
                return 20;
            case "S":
                return 30;
            default:
                return -1;
        }
    }
}
