import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Dijkstra {

    public static void main(String[] args) {
        String topologyFileName = args[0];

        List<RoutingTable> routingTables = new NetworkParser()
                .withFileName(topologyFileName)
                .withRoutingTableStrategy(new RoutingTableStrategy.Dijkstra())
                .parse()
                .computeAllRoutingTables();

        printRoutingTables(routingTables);
        saveRoutingTables(routingTables);
    }

    private static void printRoutingTables(List<RoutingTable> routingTables) {
        System.out.println(routingTables.stream()
                .map(RoutingTable::toString)
                .collect(Collectors.joining("\n\n")));
    }

    private static void saveRoutingTables(List<RoutingTable> routingTables) {
        routingTables.stream()
                .map(RoutingTable::serializeToFile)
                .forEach(fileInformation -> {
                    String fileName = fileInformation.getKey();
                    String fileContent = fileInformation.getValue();
                    writeTxtFile(fileName, fileContent);
                });
    }

    private static void writeTxtFile(String fileName, String fileContent) {
        try {
            FileWriter writer = new FileWriter(fileName + ".txt");
            writer.write(fileContent);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing file " + fileName + ".txt" + ".");
        }
    }
}
