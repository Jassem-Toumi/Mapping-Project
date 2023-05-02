import java.util.ArrayList;
import java.util.HashMap;

class Graph {
    // ArrayList<Node> nodes = new ArrayList<Node>();
    HashMap<String, Node> nodes = new HashMap<String, Node>();
    ArrayList<Edge> edges = new ArrayList<Edge>();
    String name;

    Graph(String name) {
        this.name = name;
    }

    void addNode(String nodeID, Node node) {
        nodes.put(nodeID, node);
    }

    void addEdge(Node from, Node to, double weight, String name) {
        edges.add(new Edge(from, to, weight, name));
        // graph.addEdge(from, to, from.getDistance(to), name);

    }

    // get node
    Node getNode(String nodeID) {
        return nodes.get(nodeID);
    }
}
