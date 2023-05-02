import java.util.ArrayList;

class Node implements Comparable<Node> {
    String name;
    double Latitude; // y
    double Longitude; // x
    ArrayList<Node> adjacent = new ArrayList<Node>();
    Node parent = null; // NIL for root
    double distance = Double.POSITIVE_INFINITY; // d = infinity for unvisited nodes
    boolean visited = false; // false for unvisited nodes
    // boolean inQueue = false;

    Node(String name, double Latitude, double Longitude) {
        this.name = name;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    // haversine formula to calculate distance between two nodes
    public double getDistance(Node other) {
        double lat1 = this.Latitude;
        double lat2 = other.Latitude;
        double lon1 = this.Longitude;
        double lon2 = other.Longitude;
        double R = 6371e3;
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double deltaPhi = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);
        double a = Math.pow(Math.sin(deltaPhi / 2.0), 2)
                + Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(deltaLambda / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    @Override
    public int compareTo(Node n2) {
        return Double.compare(this.distance, n2.distance);
    }

    String getName() {
        return this.name;
    }

    // @Override
    // public int compareTo(Node o) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    // }

}

