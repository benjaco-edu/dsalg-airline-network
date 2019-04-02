import java.util.ArrayList;

public class Path implements Cloneable {
    private ArrayList<String> path;
    private double cost;
    private String airline;

    public ArrayList<String> getPath() {
        return path;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getLocation() {
        return location;
    }

    private String location;

    public Path(String location) {
        this.location = location;
        this.path = new ArrayList<String>();
        this.cost = 0;
    }
    public Path(String location, ArrayList<String> path, double cost ) {
        this.location = location;
        this.path = path;
        this.cost = cost;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }

    public Path goPath(String pathGo) throws CloneNotSupportedException{
        return goPath(pathGo, 0);
    }

    public Path goPath(String pathGo, double cost) throws CloneNotSupportedException{
        Path newNode = (Path) this.clone();
        newNode.path = (ArrayList<String>) this.path.clone();
        newNode.path.add(newNode.location);
        newNode.location = pathGo;
        newNode.cost += cost;
        return newNode;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }



    public static void main(String[] args) throws CloneNotSupportedException {
        Path a = new Path("a");
        Path b = a.goPath("b");

        System.out.println(a.location);
        System.out.println(a.path);
        System.out.println(b.location);
        System.out.println(b.path);

        // assert a.location = a
        // assert a.path = []
        // assert b.location = b
        // assert b.path = [a]
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    @Override
    public String toString() {
        return "Path{" +
                "path=" + path +
                ", cost=" + cost +
                ", airline='" + airline + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
