import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class NetworkParser {

    private static final String NODE_1 = "NODE_1";
    private static final String NODE_2 = "NODE_2";
    private static final String COST = "COST";

    private String fileName;
    private RoutingTableStrategy strategy;

    public NetworkParser withFileName(String fileName) {
        this.fileName = fileName;

        return this;
    }

    public NetworkParser withRoutingTableStrategy(RoutingTableStrategy strategy) {
        this.strategy = strategy;

        return this;
    }

    public Network parse() {
        Network network = new Network(strategy);

        return createTopology(network);
    }

    private Network createTopology(Network network) {
        eachLine(line -> {
            Map<String, String> components = parseLine(line);
            Node node1 = network.addNodeIfAbsent(new Node(components.get(NODE_1)));
            Node node2 = network.addNodeIfAbsent(new Node(components.get(NODE_2)));

            int cost = Integer.parseInt(components.get(COST));
            node1.addAdjacentNode(node2, cost);
            node2.addAdjacentNode(node1, cost);

            return true;
        });

        return network;
    }

    private Map<String, String> parseLine(String line) {
        String[] components = line.split(" ");

        Map<String, String> values = new HashMap<>();
        values.put(NODE_1, components[0]);
        values.put(NODE_2, components[1]);
        values.put(COST, components[2]);

        return values;
    }

    private void eachLine(Function<String, Boolean> block) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                block.apply(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
