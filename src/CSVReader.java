import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

    public static void main(String[] args) {

        String csvFile = "src/testcsvfile.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String[]> readFile(String filename) {
        ArrayList<String[]> result = new ArrayList<>();

        String line = "";
        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] route = line.split(cvsSplitBy);

                result.add(route);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
