import java.util.*;

class AirRoute{
    public String destination;
    public String airlinenetwork;
    public double time;
    public double distance;

    public AirRoute(String destination, String airlinenetwork, double time, double distance) {
        this.destination = destination;
        this.airlinenetwork = airlinenetwork;
        this.time = time;
        this.distance = distance;
    }
}

public class AirrouteGraph {
    private HashMap<String, ArrayList<AirRoute>> adj;

    public AirrouteGraph() {
        adj = new HashMap();
    }

    public void addVertex(String airport){
        adj.put(airport, new ArrayList<AirRoute>());
    }

    public void addEdge(String from, String to, String airlinenetwork, double time, double destination) {
        adj.get(from).add(new AirRoute(to, airlinenetwork, time, destination));
    }
    public void addEdge(String from, AirRoute route) {
        adj.get(from).add(route);
    }

    public ArrayList<AirRoute> adj(String origin) {
        return adj.get(origin);
    }

    public Set<String> keys (){
        return adj.keySet();
    }
}
