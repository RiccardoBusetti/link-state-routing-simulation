import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Network {

    private final RoutingTableStrategy routingTableStrategy;
    private final Set<Node> nodes;

    public Network(RoutingTableStrategy routingTableStrategy) {
        this.routingTableStrategy = routingTableStrategy;
        this.nodes = new HashSet<>();
    }

    public Node addNodeIfAbsent(Node newNode) {
        nodes.add(newNode);

        return nodes.stream()
                .filter(node -> node.equals(newNode))
                .findAny()
                .orElse(newNode);
    }

    public List<RoutingTable> computeAllRoutingTables() {
        return nodes.stream()
                .map(this::computeRoutingTable)
                .collect(Collectors.toList());
    }

    private RoutingTable computeRoutingTable(Node source) {
        return routingTableStrategy.computeRoutingTable(nodes, source);
    }
}
