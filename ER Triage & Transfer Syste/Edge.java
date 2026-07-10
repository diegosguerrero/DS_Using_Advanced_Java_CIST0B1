public class Edge {

    String destination;
    int weight;

    public Edge(String destination, int weight){
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public String toString() {

        return "(Destination: " + destination + " | " + "Weight: " + weight + ")";

    }
}