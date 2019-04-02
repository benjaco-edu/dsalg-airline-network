import java.sql.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

class AirRoute{
    public String destination;
    private double time;
    private double distance;

    public AirRoute(String destination, double time, double distance) {
        this.destination = destination;
        this.time = time;
        this.distance = distance;
    }
}

public class AirrouteGraph {
    private HashMap<String, ArrayList<AirRoute>> adj;

    public AirrouteGraph(int v) {
        adj = new HashMap();
    }

    public void addVertex(String airport){
        adj.put(airport, new ArrayList<AirRoute>());
    }

    public void addEdge(String from, String to, double time, double destination) {
        adj.get(from).add(new AirRoute(to, time, destination));
    }
    public void addEdge(String from, AirRoute route) {
        adj.get(from).add(route);
    }

    public ArrayList<AirRoute> adj(String origin) {
        return adj.get(origin);
    }

}
