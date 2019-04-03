import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
interface GetCostFunction {
    double getCost(AirRoute element);
}

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        AirrouteGraph graph = buildGraph();

        boolean dfs = depthFirstSearch(graph, "AER", "OVB");
        System.out.println(dfs);

        Path bfs = breadthFirstSearch(graph, "AER", "OVB");
        System.out.println(bfs);

        Path dij_dist = dijkstra(graph, "AER", "OVB", element -> element.distance);
        System.out.println(dij_dist);

        Path dij_time = dijkstra(graph, "AER", "OVB", element -> element.time + 1);
        System.out.println(dij_time);



    }

    private static Path dijkstra(AirrouteGraph graph, String start, String end, GetCostFunction costFunction) {
        HashMap<String, Path> unExplored = new HashMap<>();
        HashMap<String, Path> distances = new HashMap<>();

        for (String airport : graph.keys()) {
            Path path = new Path(airport, new ArrayList<>(), Integer.MAX_VALUE);
            if (airport.equals(start)) {
                path.setCost(0);
                path.getPath().add(airport);
            }
            unExplored.put(airport, path);
            distances.put(airport, path);
        }


        while (unExplored.size() > 0) {
            Path v = airportWithLowestDistance(unExplored, distances);
            unExplored.remove(v.getLocation());

            for(AirRoute neighbor : graph.adj(v.getLocation())){
                double newCost = v.getCost() + costFunction.getCost(neighbor);
                Path path = distances.get(neighbor.destination);
                if (newCost < path.getCost()) {
                    path.setCost(newCost);
                    ArrayList<String> newPath = (ArrayList<String>) v.getPath().clone();
                    newPath.add(neighbor.destination);
                    path.setPath(newPath);
                }
            }
        }

        return distances.get(end);
    }

    private static Path airportWithLowestDistance(HashMap<String,Path> q, HashMap<String,Path> distances) {
        Double lowestFoundValue = Double.MAX_VALUE;
        Path pathWithLowestValue = null;
        for (String possible : q.keySet()) {
            Path path = distances.get(possible);
            if (path.getCost() <= lowestFoundValue) {
                pathWithLowestValue = path;
                lowestFoundValue = path.getCost();
            }
        }
        return pathWithLowestValue;
    }

    private static boolean depthFirstSearch(AirrouteGraph graph, String start, String end) throws CloneNotSupportedException{
        if (start.equals(end)) {
            return true;
        }

        for (AirRoute new_start_location : graph.adj(start)) {
            Map<String, Boolean> explored = new HashMap<String, Boolean>();
            explored.put(start, true);

            if (new_start_location.destination.equals(end)) {
                return true;
            }

            if (depthExloration(new_start_location.destination, end, new_start_location.airlinenetwork, graph, explored)) {
                return true;
            }
        }
        return false;
    }


    private static boolean depthExloration(String start, String end, String airline, AirrouteGraph graph, Map<String, Boolean> explored) {

        for (AirRoute new_location : graph.adj(start)) {
            if (explored.containsKey(new_location.destination) || !new_location.airlinenetwork.equals(airline)) {
                continue;
            }
            if (new_location.destination.equals(end)) {
                return true;
            }
            explored.put(new_location.destination, true);
            if(depthExloration(new_location.destination, end, airline, graph, explored)){
                return true;
            }
        }
        return false;
    }

    private static Path breadthFirstSearch(AirrouteGraph graph, String start, String goal) throws CloneNotSupportedException {
        if (start.equals(goal)) {
            return new Path(start);
        }

        for (AirRoute startTrip : graph.adj(start)) {
            FiFo<Path> frontier = new FiFo<>();
            ArrayList<String> startPath = new ArrayList<>();
            startPath.add(start);
            Path item = new Path(startTrip.destination, startPath);
            item.setAirline(startTrip.airlinenetwork);

            frontier.enqueue(item);
            Map<String, Boolean> explored = new HashMap<>();

            if (startTrip.destination.equals(goal)) {
                return frontier.dequeue();
            }

            while (frontier.size() > 0) {
                Path node = frontier.dequeue();
                if (node.getLocation().equals(goal)) {
                    return node;
                }
                explored.put(node.getLocation(), true);
                for (AirRoute new_location : graph.adj(node.getLocation())) {
                    if (explored.containsKey(new_location.destination)
                            || locationInQue(new_location.destination, frontier)
                            || !new_location.airlinenetwork.equals(node.getAirline())
                            || new_location.destination.equals(start)) {
                        continue;
                    }
                    frontier.enqueue(node.goPath(new_location.destination));

                }
            }
        }


        return null;
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
