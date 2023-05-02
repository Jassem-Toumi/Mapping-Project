class Edge {
    Node source;
    Node destination;
    double weight;
    Boolean highlighted = false;
    Boolean isDarkMode = false;
    String name;

    Edge(Node from, Node to, double weight, String name) {
        this.source = from;
        this.destination = to;
        this.weight = weight;
        this.name = name;

    }
}

