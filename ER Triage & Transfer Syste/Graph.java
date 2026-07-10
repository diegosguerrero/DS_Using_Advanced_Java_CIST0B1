import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {

    HashMap<String, List<Edge>> adjacencyList = new HashMap<>();

    public Graph() {
    }

    public void addNode(String name) {
        if (!adjacencyList.containsKey(name)) {

            adjacencyList.put(name, new ArrayList<>());

        }
    }

    public void addEdge(String source, String destination, int weight) {

        // Making sure both nodes exist
        addNode(source);
        addNode(destination);

        // Adding an edge from source
        adjacencyList.get(source).add(new Edge(destination, weight));

        // Adding an edge from destination
        adjacencyList.get(destination).add(new Edge(source, weight));
    }

    public List<Edge> getNeighbors(String node) {

        if (adjacencyList.containsKey(node)) {
            return adjacencyList.get(node);

        } else
            return new ArrayList<>();

    }

    public Boolean hasNode(String name) {

        return (adjacencyList.containsKey(name));
    }

    public Set<String> getNodes() {
        return adjacencyList.keySet();
    }

    public void displayGraph() {
        for (String node : adjacencyList.keySet()) {
            System.out.print("\nDEPARTMENT: " + node);
            System.out.println("\n" + "Connects to " + adjacencyList.get(node));
        }
    }

    public void dijkstra(String source, String destination) {

        HashMap<String, Integer> distances = new HashMap<>();
        HashMap<String, String> previous = new HashMap<>();
        HashSet<String> visited = new HashSet<>();
        PriorityQueue<String[]> priorityQueue = new PriorityQueue<>(
                (a, b) -> Integer.parseInt(a[1]) - Integer.parseInt(b[1]));

        // Initializing | Step 1
        for (String node : getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
            previous.put(node, null);
        }

        distances.put(source, 0);
        priorityQueue.add(new String[] { source, "0" });

        // Greedy Loop | Step 2
        while (!priorityQueue.isEmpty()) {

            String[] current = priorityQueue.poll();
            String currentName = current[0];
            int currentDistance = Integer.parseInt(current[1]);

            if (currentName.equals(destination)) {
                break;
            }

            if (visited.contains(currentName)) {
                continue;
            }

            visited.add(currentName);

            for (Edge edge : getNeighbors(currentName)) {

                String neighbor = edge.destination;
                int newDistance = currentDistance + edge.weight;

                if (newDistance < distances.get(neighbor)) {

                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, currentName);
                    priorityQueue.add(new String[] { neighbor, String.valueOf(newDistance) });
                }
            }
        }

        // Check if path exists | Step 3
        if (distances.get(destination).equals(Integer.MAX_VALUE)) {

            System.out.println("No Path was found from " + source + " to " + destination);
            return;
        }

        // Rebuilding the path | Step 4
        ArrayList<String> path = new ArrayList<>();
        String current = destination;

        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }

        System.out.println("The Shortest path is: " + String.join(" -> ", path));
        System.out.println("Total transfer time is: " + distances.get(destination) + " minutes");
    }
}
