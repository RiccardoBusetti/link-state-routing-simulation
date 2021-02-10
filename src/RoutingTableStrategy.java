import java.util.*;

public interface RoutingTableStrategy {

    RoutingTable computeRoutingTable(Set<Node> nodes, Node source);

    class Dijkstra implements RoutingTableStrategy {

        @Override
        public RoutingTable computeRoutingTable(Set<Node> nodes, Node source) {
            // List containing all the costs.
            List<RoutingTable.Cost> costs = new LinkedList<>();
            // Map containing the nodes that we have already settled.
            Map<Node, Boolean> settled = new HashMap<>();
            // Queue used to poll elements by their cost.
            PriorityQueue<RoutingTable.Cost> queue = new PriorityQueue<>();
            // Map used to compute the backwards path.
            Map<Node, Node> previous = new HashMap<>();

            // Initiate all distances to infinity.
            nodes.stream()
                    .filter((node) -> !node.equals(source))
                    .forEach((node) -> costs.add(new RoutingTable.Cost(node, Integer.MAX_VALUE)));
            // Initiate the distance to the source to 0.
            RoutingTable.Cost sourceCost = new RoutingTable.Cost(source, 0);
            costs.add(sourceCost);
            // Add source node to the queue in order to start our algorithm from it.
            queue.add(sourceCost);

            while (!queue.isEmpty()) {
                // We get the minimum cost known.
                RoutingTable.Cost currentCost = queue.poll();
                // We mark it as settled, aka the best cost is found.
                settled.putIfAbsent(currentCost.getDestination(), true);
                currentCost.addNextHop(computeNextHop(previous, source, currentCost.getDestination()));

                currentCost.getDestination()
                        .getNodes()
                        .entrySet()
                        .stream()
                        .filter((adjacentNode) -> !isSettled(settled, adjacentNode.getKey()))
                        .forEach((adjacentNode) -> {
                            int newCostToAdjacent = currentCost.getCostToNode() + adjacentNode.getValue();
                            RoutingTable.Cost currentAdjacentCost = findCost(costs, adjacentNode.getKey());

                            if (newCostToAdjacent < currentAdjacentCost.getCostToNode()) {
                                currentAdjacentCost.updateCost(newCostToAdjacent);
                                previous.put(adjacentNode.getKey(), currentCost.getDestination());
                            }

                            queue.add(currentAdjacentCost);
                        });
            }

            return new RoutingTable(source, costs);
        }

        private boolean isSettled(Map<Node, Boolean> settled, Node node) {
            return settled.get(node) != null;
        }

        private RoutingTable.Cost findCost(List<RoutingTable.Cost> costs, Node node) {
            return costs
                    .stream()
                    .filter((cost) -> cost.getDestination().equals(node))
                    .findAny()
                    .orElse(null);
        }

        private Node computeNextHop(Map<Node, Node> previous, Node source, Node current) {
            if (previous.get(current) == null) return null;

            return previous.get(current) == source
                    ? current : computeNextHop(previous, source, previous.get(current));
        }
    }
}
