import java.util.HashMap;
import java.util.Objects;

public class Node {

    private final String id;
    private final HashMap<Node, Integer> nodes;

    public Node(String id) {
        this.id = id.toUpperCase();
        this.nodes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public HashMap<Node, Integer> getNodes() {
        return nodes;
    }

    public void addAdjacentNode(Node node, Integer cost) {
        nodes.putIfAbsent(node, cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
