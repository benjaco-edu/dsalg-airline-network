import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        AirrouteGraph graph = buildGraph();

//        boolean dfs = depthFirstSearch(graph, "AER", "OVB");
//        System.out.println(dfs);

        Path bfs = breadthFirstSearch(graph, "AER", "OVB");
        System.out.println(bfs);


    }

//    private static boolean depthFirstSearch(AirrouteGraph graph, String start, String end) {
//        if (start.equals(end)) {
//            return true;
//        }
//        Map<String, Boolean> explored = new HashMap<>();
//
//        for (AirRoute new_location : graph.adj(start)) {
//
//        }
//    }

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
