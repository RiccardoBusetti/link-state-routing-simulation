import javafx.util.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RoutingTable {
    private final List<Cost> costs;
    private final Node node;

    public RoutingTable(Node node, List<Cost> costs) {
        this.node = node;
        this.costs = costs;
        // After the routing table is created, we sort its entries in order to have
        // a uniform standard.
        costs.sort(Comparator.comparing(o -> o.getDestination().getId()));
    }

    public Pair<String, String> serializeToFile() {
        return new Pair<>(node.getId(), costs.stream()
                .map(Cost::serializeToFile)
                .collect(Collectors.joining("\n")));
    }

    @Override
    public String toString() {
        return "Router " +
                node +
                ":" +
                "\n" +
                "Dest, Next hop" +
                "\n" +
                costs.stream().filter(cost -> !cost.getDestination().equals(node))
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"));
    }

    public static class Cost implements Comparable<Cost> {

        private final Node destination;
        private Node nextHop;
        private Integer costToNode;

        public Cost(Node destination, Integer costToNode) {
            this.destination = destination;
            this.nextHop = null;
            this.costToNode = costToNode;
        }

        public Node getDestination() {
            return destination;
        }

        public Node getNextHop() {
            return nextHop;
        }

        public Integer getCostToNode() {
            return costToNode;
        }

        public void updateCost(Integer newCost) {
            costToNode = newCost;
        }

        public void addNextHop(Node node) {
            nextHop = node;
        }

        @Override
        public int compareTo(Cost o) {
            return costToNode.compareTo(o.costToNode);
        }

        public String serializeToFile() {
            return destination.getId() + " " + (nextHop != null ? nextHop.getId() : "direct");
        }

        @Override
        public String toString() {
            return destination + "     " + nextHop;
        }
    }
}
