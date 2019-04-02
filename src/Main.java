import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AirrouteGraph graph = buildGraph();
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
