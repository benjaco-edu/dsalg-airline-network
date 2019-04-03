import java.util.*;

class AirRoute implements Comparable{
    public String destination;
    public String airlinenetwork;
    public double time;
    public double distance;
    public String compareMode = "distance";

    public AirRoute(String destination, String airlinenetwork, double time, double distance) {
        this.destination = destination;
        this.airlinenetwork = airlinenetwork;
        this.time = time;
        this.distance = distance;
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass() != this.getClass()) {
            return 0;
        }
        AirRoute object = (AirRoute) o;

        switch (compareMode) {
            case "distance":
                if( this.distance > object.distance){
                    return 1;
                } else if (this.distance ==  object.distance) {
                    return 0;
                }
                return -1;
            case "time":
                if( this.time > object.time){
                    return 1;
                } else if (this.time ==  object.time) {
                    return 0;
                }
                return -1;
        }

        return 0;
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
