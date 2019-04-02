import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        AirrouteGraph graph = buildGraph();

        Path bfs = breadthFirstSearch(graph, "AER", "OVB");
        System.out.println(bfs);


    }

    private static Path breadthFirstSearch(AirrouteGraph graph, String start, String goal) throws CloneNotSupportedException {
        FiFo<Path> frontier = new FiFo<>();
        frontier.enqueue(new Path(start));
        Map<String, Boolean> explored = new HashMap<>();

        if (start.equals(goal)) {
            return frontier.dequeue();
        }

        while (frontier.size() > 0) {
            Path node = frontier.dequeue();
            if (node.getLocation().equals(goal)) {
                return node;
            }
            explored.put(node.getLocation(), true);
            for (AirRoute new_location : graph.adj(node.getLocation())) {
                if (explored.containsKey(new_location.destination) || locationInQue(new_location.destination, frontier) || !sameAirlineAsStart(new_location.airlinenetwork, node)) {
                    continue;
                }
                frontier.enqueue(node.goPath(new_location.destination));

            }
        }
        return null;
    }

    private static boolean sameAirlineAsStart(String airlinenetwork, Path node) {
        if (node.getPath().size() == 0) {
            node.setAirline(airlinenetwork);
            return true;
        }
        return node.getAirline().equals(airlinenetwork);
    }

    private static boolean locationInQue(String destination, FiFo<Path> frontier) {
        for (Path path : frontier) {
            if (path.getLocation().equals(destination)) {
                return true;
            }
        }
        return false;
    }

    private static AirrouteGraph buildGraph( ) {
        AirrouteGraph graph = new AirrouteGraph();
        for(String[] item : CSVReader.readFile("src/airports.txt")){
            graph.addVertex(item[0]);
        }
        for(String[] item : CSVReader.readFile("src/routes.txt")){
            graph.addEdge(item[1], item[2], item[0], Double.parseDouble(item[4]), Double.parseDouble(item[3]));
        }
        return graph;
    }
}
