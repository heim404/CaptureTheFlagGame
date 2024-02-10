package Maps;

/**
 * This class represents a node in a graph, with a vertex of generic type T and a weight.
 * It implements the Comparable interface to allow for comparison between nodes based on their weights.
 */
class VertexNode<T> implements Comparable<T> {
    private T vertex;
    private double weight;

    /**
     * Constructor for the VertexNode class.
     * Initializes the vertex and weight of the node.
     * @param vertex - the vertex of the node.
     * @param weight - the weight of the node.
     */
    public VertexNode(T vertex, double weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    /**
     * Overrides the compareTo method from the Comparable interface.
     * Compares this node with another node based on their weights.
     * @param o - the other node to be compared with.
     * @return int - the result of the comparison. A negative integer, zero, or a positive integer as this node's weight is less than, equal to, or greater than the specified node's weight.
     */
    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(T o) {
        return Double.compare(weight, ((VertexNode<T>) o).weight);
    }

    /**
     * Returns the vertex of this node.
     * @return T - the vertex of this node.
     */
    public T getVertex() {
        return vertex;
    }
}