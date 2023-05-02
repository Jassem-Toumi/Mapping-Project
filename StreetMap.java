import java.io.BufferedReader;
import javax.swing.*;
import java.awt.*;


public class StreetMap {

    public static void displayMap(Graph G, String start, String end) {
        // display map
        var frame = new graphDrawer(G, start, end);
        frame.setTitle(G.name);
        // center the frame
        frame.setLocationRelativeTo(null);

    }

    private static void run(String[] args) {
        if (args.length == 2 && (args[1].equals("show") || args[1].equals("--show"))) {
            // show map
            String graphName = args[0].replace(".txt", "");
            Graph G = readIn(args[0], null, null, graphName);

            SwingUtilities.invokeLater(() -> {
                displayMap(G, null, null);

            });

        } else if (args.length == 4) {
            // show map
            String Start = args[2];
            String End = args[3];
            String graphName = args[0].replace(".txt", "");
            Graph G = readIn(args[0], Start, End, graphName);
            SwingUtilities.invokeLater(() -> {
                displayMap(G, Start, End);

            });

        } else if (args.length == 5) {
            String Start = args[3];
            String End = args[4];
            String graphName = args[0].replace(".txt", "");
            Graph G = readIn(args[0], Start, End, graphName);
            SwingUtilities.invokeLater(() -> {
                displayMap(G, Start, End);
            });

        } else {
            System.out.println("Invalid arguments");
        }

    }

    private static Graph readIn(String fileName, String commandStart, String commandEnd, String graphName) {
        // read in file
        // create graph
        // return Graph for display
        Graph graph = new Graph(graphName);
        String line = null;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new java.io.FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\\s+");
                if (split[0].equals("i")) {
                    // add node
                    String nodeID = split[1];
                    Node node = new Node(nodeID, Double.parseDouble(split[2]),
                            Double.parseDouble(split[3]));
                    // System.out.println(node.name);
                    graph.addNode(nodeID, node);
                } else if (split[0].equals("r")) {
                    // add edge
                    Node from = graph.getNode(split[2]);
                    Node to = graph.getNode(split[3]);
                    String name = split[1];
                    graph.addEdge(from, to, from.getDistance(to), name);
                    // update adjacent nodes
                    from.adjacent.add(to);
                    to.adjacent.add(from);
                }
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        return graph;
    }

    public static void main(String[] args) {
        run(args);
    }

}