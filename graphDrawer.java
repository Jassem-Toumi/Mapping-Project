import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class graphDrawer extends JFrame {
    private GraphPanel graphPanel;
    private ControlPanel controlPanel;

    public graphDrawer(Graph G, String start, String end) {
        super("Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set frame to the middle of the screen
        setLocationRelativeTo(null);

        setSize(1200, 1000);

        graphPanel = new GraphPanel(G);

        controlPanel = new ControlPanel(graphPanel, start, end);
        controlPanel.setBackground(Color.GRAY);

        add(graphPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

}

class GraphPanel extends JPanel {
    // add code here to draw the graph
    Graph graph = null;

    GraphPanel(Graph graph) {
        this.graph = graph;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Find the max and min edge lengths
        double maxEdgeLength = Double.MIN_VALUE;
        double minEdgeLength = Double.MAX_VALUE;
        for (Edge edge : graph.edges) {
            double length = edge.source.getDistance(edge.destination);
            maxEdgeLength = Math.max(maxEdgeLength, length);
            minEdgeLength = Math.min(minEdgeLength, length);
        }

        // Find the max and min latitudes and longitudes
        double minLatitude = Double.POSITIVE_INFINITY;
        double maxLatitude = Double.NEGATIVE_INFINITY;
        double minLongitude = Double.POSITIVE_INFINITY;
        double maxLongitude = Double.NEGATIVE_INFINITY;

        for (Entry<String, Node> node : graph.nodes.entrySet()) {
            maxLongitude = Math.max(maxLongitude, node.getValue().Longitude);
            maxLatitude = Math.max(maxLatitude, node.getValue().Latitude);
            minLongitude = Math.min(minLongitude, node.getValue().Longitude);
            minLatitude = Math.min(minLatitude, node.getValue().Latitude);
        }

        double scalex = maxLatitude - minLatitude;
        double scaley = maxLongitude - minLongitude;
        double scale = Math.max(scalex, scaley);

        // Iterate over the edges and draw each one
        for (Edge edge : graph.edges) {

            Node from = edge.source;
            Node to = edge.destination;
            double y1 = 0.0;
            double x1 = 0.0;
            double y2 = 0.0;
            double x2 = 0.0;

            if (graph.name.equals("ur")) {
                y1 = (this.getHeight() - this.getHeight() * (from.Latitude - minLatitude) / scale) * 0.9;
                x1 = (this.getWidth() * (from.Longitude - minLongitude) / scale) * 0.9 + 50;
                y2 = (this.getHeight() - this.getHeight() * (to.Latitude - minLatitude) / scale) * 0.9;
                x2 = (this.getWidth() * (to.Longitude - minLongitude) / scale) * 0.9 + 50;
            } else {

                y1 = (this.getHeight() - this.getHeight() * (from.Latitude - minLatitude) / scale) * 1.3 - 300;
                x1 = (this.getWidth() * (from.Longitude - minLongitude) / scale) * 0.9 + 60;
                y2 = (this.getHeight() - this.getHeight() * (to.Latitude - minLatitude) / scale) * 1.3 - 300;
                x2 = (this.getWidth() * (to.Longitude - minLongitude) / scale) * 0.9 + 60;

            }

            // Draw the scaled edge
            if (edge.highlighted) {
                g.setColor(Color.RED);
            } else {
                if (edge.isDarkMode) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
            }

            g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

        }

    }

    public void highlightShortestPath(ArrayList<Node> shortestPath) {
        // add code here to highlight the shortest path
        for (Edge edge : graph.edges) {
            if (shortestPath.contains(edge.source) && shortestPath.contains(edge.destination)) {
                edge.highlighted = true;
            } else {
                edge.highlighted = false;
            }
        }

        repaint();

    }

}

class ControlPanel extends JPanel {
    private JTextField fromField;
    private JTextField toField;
    private JButton searchButton;
    private JButton darkModeToggle;
    private GraphPanel graphPanel;

    public ControlPanel(GraphPanel graphPanel, String commandStart, String commandEnd) {
        this.graphPanel = graphPanel;
        if (commandStart != null && commandEnd != null) {
            search(commandStart, commandEnd);
            commandStart = null;
            commandEnd = null;
        }

        fromField = new JTextField(10);
        toField = new JTextField(10);
        searchButton = new JButton("Search");
        darkModeToggle = new JButton("Toggle Dark Mode");

        searchButton.addActionListener(e -> search(fromField.getText(), toField.getText()));
        // change background color when dark mode is toggled
        darkModeToggle.addActionListener(e -> {
            if (darkModeToggle.getText().equals("Toggle Dark Mode")) {
                graphPanel.setBackground(Color.BLACK);
                darkModeToggle.setText("Toggle Light Mode");
                // change the color of the edges to white
                for (Edge edge : graphPanel.graph.edges) {
                    edge.isDarkMode = true;
                }
                // repaint the graph
                graphPanel.repaint();
            } else {
                setBackground(Color.GRAY);
                graphPanel.setBackground(Color.WHITE);
                darkModeToggle.setText("Toggle Dark Mode");
                // change the color of the edges to black
                for (Edge edge : graphPanel.graph.edges) {
                    edge.isDarkMode = false;
                }
                // repaint the graph
                graphPanel.repaint();
            }
        });

        add(new JLabel("From:"));
        add(fromField);
        add(new JLabel("To:"));
        add(toField);
        add(searchButton);
        add(darkModeToggle);

    }

    private void search(String from, String to) {
        // if (commandStart != null && commandEnd != null) {
        // System.out.println("I am here");
        // from = commandStart;
        // to = commandEnd;
        // }

        // System.out.println("from: " + commandStart + " to: " + commandEnd);

        Node fromVertex = null;
        Node toVertex = null;
        for (Entry<String, Node> node : graphPanel.graph.nodes.entrySet()) {
            if (node.getValue().name.equals(from)) {
                fromVertex = node.getValue();
            }
            if (node.getValue().name.equals(to)) {
                toVertex = node.getValue();
            }
        }
        if (fromVertex == null || toVertex == null) {
            System.out.println("Invalid input");
            return;
        }
        ArrayList<Node> shortestPath = Dijakstra(fromVertex, toVertex);
        // update the graph panel to highlight the shortest path
        graphPanel.highlightShortestPath(shortestPath);

    }

    public ArrayList<Node> Dijakstra(Node start, Node end) {
        // add code here to implement Dijkstra's algorithm
        // return the list of nodes in the shortest path
        ArrayList<Node> shortestPath = new ArrayList<Node>();
        PriorityQueue<Node> Queue = new PriorityQueue<Node>();
        // initialize all other nodes
        for (Entry<String, Node> n : graphPanel.graph.nodes.entrySet()) {
            if (n.getValue().name != start.name) {
                n.getValue().distance = Double.POSITIVE_INFINITY;
                n.getValue().parent = null;
            } else {
                n.getValue().distance = 0;
                n.getValue().parent = null;
            }
            Queue.add(n.getValue());
        }

        while (!Queue.isEmpty()) {
            // Extract min
            Node u = Queue.poll();
            for (Node v : u.adjacent) {
                double newPath = u.distance + u.getDistance(v);
                if (newPath < v.distance) {
                    v.distance = newPath;
                    v.parent = u;
                    Queue.add(v);
                }
            }
        }

        // printing shortest path
        Node destination = end;
        String path = "";
        while (destination != null) {
            path = destination.name + ", " + path;
            shortestPath.add(destination);
            destination = destination.parent;
        }
        System.out.println("shortest path from " + start.name + " to " + end.name + " is: ");
        System.out.println(path.substring(0, path.length() - 2));
        return shortestPath;
    }

}
