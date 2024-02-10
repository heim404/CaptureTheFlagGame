package Maps;

import Collections.Grafos.NetworkADT;
import Collections.ListasIterador.Interfaces.UnorderedListADT;

/**
 * CustomNetworkADT interface that extends the NetworkADT interface.
 * It provides additional methods for importing and exporting to JSON, generating a random graph, and getting adjacent vertices.
 *
 * @param <T> The type of elements in the network.
 */
public interface CustomNetworkADT<T> extends NetworkADT<T> {

    /**
     * Imports the network from a JSON file.
     */
    void importFromJson();

    /**
     * Generates a random graph for the network.
     */
    void generateRandomGraph(int numberOfVertices, double densidade);

    /**
     * Exports the network to a JSON file.
     *
     * @param filename The name of the file to which the network will be exported.
     */
    void exportToJson(String filename);

    /**
     * Gets the vertices adjacent to a given vertex.
     *
     * @param startVertex The vertex for which to get the adjacent vertices.
     * @return An iterable collection of integers representing the adjacent vertices.
     */
    UnorderedListADT<T> getNextVertexes(T startVertex);

}
