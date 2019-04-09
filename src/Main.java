import java.util.*;

@FunctionalInterface
interface GetCostFunction {
    double getCost(AirRoute element);
}

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        AirrouteGraph graph = buildGraph();
        ArrayList<String> airports = readAirports();

        System.out.println("depthFirstSearch: ");
        long startTime_depthFirstSearch = System.nanoTime();
        boolean dfs = depthFirstSearch(graph, "OPO", "LAX");
        System.out.println(dfs);
        System.out.println("Took "+(System.nanoTime()-startTime_depthFirstSearch)/1e6+ " ms \n");

        System.out.println("breadthFirstSearch: ");
        long startTime_breadthFirstSearch = System.nanoTime();
        Path bfs = breadthFirstSearch(graph, "OPO", "LAX");
        System.out.println(bfs);
        System.out.println("Took "+(System.nanoTime()-startTime_breadthFirstSearch)/1e9+ " seconds \n");

        System.out.println("dijkstra (distance): ");
        long startTime_dijkstra_dist  = System.nanoTime();
        Path dij_dist = dijkstra(graph, "OPO", "LAX", element -> element.distance);
        System.out.println(dij_dist);
        System.out.println("Took "+(System.nanoTime()-startTime_dijkstra_dist )/1e9+ " seconds \n");

        System.out.println("dijkstra (time): ");
        long startTime_dijkstra_time  = System.nanoTime();
        Path dij_time = dijkstra(graph, "OPO", "LAX", element -> element.time + 1);
        System.out.println(dij_time);
        System.out.println("Took "+(System.nanoTime()-startTime_dijkstra_time )/1e9+ " seconds \n");


        System.out.println("minimum spanning tree");
        long startTime_mst_time  = System.nanoTime();
        System.out.println( testForWidestCoverage(airports, graph) );
        System.out.println("Took "+(System.nanoTime()-startTime_mst_time )/1e9+ " seconds \n");


    }


    private static String testForWidestCoverage(ArrayList<String> airports, AirrouteGraph graph) {
        int widest_airline_paths = 0;
        String widest_airline = null;

        for (String airport : airports) {
            HashSet<String> airlines = new HashSet<>();

            for (AirRoute outgoing : graph.adj(airport)) {
                airlines.add(outgoing.airlinenetwork);
            }

            for (String airline : airlines) {
                int coverage = buildMst(graph, airline, airport);
                if (coverage > widest_airline_paths) {
                    widest_airline = airline;
                    widest_airline_paths = coverage;
                }
            }

        }

        return widest_airline + " has a max coverage of "+ widest_airline_paths;
    }

    private static int buildMst(AirrouteGraph graph, String airline, String start) {
        HashMap<String, Double> explored = new HashMap<>();

        HashSet<String> unexploredSet = new HashSet<>();
        PQMin<AirRoute> unexplored = new PQMin<>();

        unexploredSet.add(start);
        unexplored.enqueue(new AirRoute(start, airline, 0, 0));

        while (unexplored.size() > 0) {
            AirRoute lowest = unexplored.dequeue();
            unexploredSet.remove(lowest.destination);

            explored.put(lowest.destination, lowest.distance);
            for (AirRoute outgoing : graph.adj(lowest.destination)) {
                if (!outgoing.airlinenetwork.equals(airline)) {
                    continue;
                }
                if (explored.containsKey(outgoing.destination)) {
                    continue;
                }
                if (unexploredSet.contains(outgoing.destination)) {
                    continue;
                }
                unexploredSet.add(outgoing.destination);
                unexplored.enqueue(outgoing);
            }
        }
        return explored.size();

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

    private static Path breathFirstSerchAirline(AirrouteGraph graph, String start, String goal, AirRoute startTrip ) throws CloneNotSupportedException {
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
        return null;
    }

    private static Path breadthFirstSearch(AirrouteGraph graph, String start, String goal) throws CloneNotSupportedException {
        if (start.equals(goal)) {
            return new Path(start);
        }
        Path bestPath = null;
        for (AirRoute startTrip : graph.adj(start)) {
            Path route = breathFirstSerchAirline(graph, start, goal, startTrip);
            if (route != null) {
                if (bestPath == null) {
                    bestPath = route;
                } else {
                    if (route.getPath().size() < bestPath.getPath().size()) {
                        bestPath = route;
                    }
                }
            }
        }


        return bestPath;
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


    private static ArrayList<String> readAirports() {
        ArrayList<String> airports = new ArrayList<>();

        for (String[] item : CSVReader.readFile("src/airports.txt")) {
            airports.add(item[0]);
        }

        return airports;
    }
}
