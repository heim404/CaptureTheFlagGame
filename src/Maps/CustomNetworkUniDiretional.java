package Maps;

import Collections.Grafos.NetworkUniDirectional;
import Collections.ListasIterador.Classes.ArrayUnorderedList;
import Collections.ListasIterador.Classes.OrderedLinkedList;
import Collections.ListasIterador.Interfaces.OrderedListADT;
import Collections.ListasIterador.Interfaces.UnorderedListADT;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * CustomNetworkUniDiretional class that extends the NetworkUniDirectional class and implements the CustomNetworkADT interface.
 * It represents a unidirectional network with additional methods for importing and exporting to JSON, generating a random graph, and getting adjacent vertices.
 *
 * @param <T> The type of elements in the network.
 */

public class CustomNetworkUniDiretional<T> extends NetworkUniDirectional<T> implements CustomNetworkADT<T> {

    /**
     * This method generates a random graph with a given number of vertices and density.
     * It first resizes the vertex array and the adjacency matrix to the given number of vertices.
     * Then, it initializes the vertices and the adjacency matrix.
     * After that, it calculates the number of edges to be added based on the given density, and adds random edges to the graph.
     * Finally, it updates the number of vertices and checks if the graph is connected. If the graph is not connected, it repeats the process.
     *
     * @param numberOfVertices the number of vertices in the graph
     * @param density the density of the graph
     */

    public void generateRandomGraph(int numberOfVertices,double density) {
        Random rand = new Random();
        boolean isConnected;

        do {
            // Resize the vertex array and the adjacency matrix
            vertices = (T[]) new Object[numberOfVertices];
            adjMatrix = new double[numberOfVertices][numberOfVertices];

            // Initialize vertices
            for (int i = 0; i < numberOfVertices; i++) {
                vertices[i] = (T) Integer.valueOf(i); // Each vertex is an Integer
            }

            // Calculate the number of edges to be added
            int numEdges = (int) ((numberOfVertices * (numberOfVertices - 1)) / 2.0 * density);

            // Initialize the adjacency matrix
            for (int i = 0; i < numberOfVertices; i++) {
                for (int j = 0; j < numberOfVertices; j++) {
                    adjMatrix[i][j] = Double.POSITIVE_INFINITY;
                }
            }

            // Add random edges
            while (numEdges > 0) {
                int v1 = rand.nextInt(numberOfVertices);
                int v2 = rand.nextInt(numberOfVertices);
                if (v1 != v2 && adjMatrix[v1][v2] == Double.POSITIVE_INFINITY) {
                    double weight = 1 + rand.nextInt(15);
                    adjMatrix[v1][v2] = weight;  // Edge only from v1 to v2
                    numEdges--;
                }
            }

            // Update the number of vertices
            numVertices = numberOfVertices;

            // Check if the graph is connected
            isConnected = isConnected();
        } while (!isConnected);
    }

    /**
     * Imports the network from a JSON file.
     * It prompts the user to enter the filename of the map to import, then imports the network from the JSON file.
     */

    public void importFromJson() {
        System.out.println("Enter the filename of the map to import: ");
        String filename = new Scanner(System.in).nextLine();
        String basePath = "src/Maps/"; // Fixed path
        String fullPath = basePath + filename + ".json";
        JSONParser parser = new JSONParser();
        JSONArray graphArray = null;
        try {
            graphArray = (JSONArray) parser.parse(new FileReader(fullPath));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        if (!isEmpty()) {
            this.clearGraph();
        }

        // First Pass - Add All Vertices
        for (Object vertexObj : graphArray) {
            JSONObject vertexJson = (JSONObject) vertexObj;
            String vertexIdStr = (String) vertexJson.get("vertex");
            int vertexName = Integer.parseInt(vertexIdStr);
            T vertex = (T) Integer.valueOf(vertexName);
            this.addVertex(vertex);
        }

        // Second Pass - Add All Edges
        for (Object vertexObj : graphArray) {
            JSONObject vertexJson = (JSONObject) vertexObj;
            T vertex = (T) Integer.valueOf(Integer.parseInt((String) vertexJson.get("vertex")));

            JSONArray edges = (JSONArray) vertexJson.get("edges");
            for (Object edgeObj : edges) {
                JSONObject edgeJson = (JSONObject) edgeObj;
                String toVertexIdStr = (String) edgeJson.get("to");
                int toVertexName = Integer.parseInt(toVertexIdStr);
                double weight = (double) edgeJson.get("weight");

                T toVertex = (T) Integer.valueOf(toVertexName);

                this.addEdge(vertex, toVertex, weight); // Unidirectional edge
            }
        }
    }

    /**
     * Exports the network to a JSON file.
     * It exports the network to a JSON file with the given filename.
     *
     * @param filename The name of the file to which the network will be exported.
     */

    public void exportToJson(String filename) {
        JSONArray graphArray = new JSONArray();

        // Iterate over each vertex
        for (int i = 0; i < numVertices; i++) {
            JSONObject vertexObj = new JSONObject();
            JSONArray edgesArray = new JSONArray();

            // Add vertex details
            vertexObj.put("vertex", vertices[i].toString());

            // Iterate over the adjacency matrix for edges
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j] != Double.POSITIVE_INFINITY) {
                    JSONObject edgeObj = new JSONObject();
                    edgeObj.put("to", vertices[j].toString());
                    edgeObj.put("weight", adjMatrix[i][j]);
                    edgesArray.add(edgeObj);
                }
            }

            vertexObj.put("edges", edgesArray);
            graphArray.add(vertexObj);
        }

        // Define the specific directory path
        String directoryPath = "src/Maps/";

        // Check if filename ends with .json, if not, append it
        if (!filename.endsWith(".json")) {
            filename += ".json";
        }

        // Write JSON file
        try (FileWriter file = new FileWriter(directoryPath + filename)) {
            file.write(graphArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the graph.
     * It resets the number of vertices, the vertex array, and the adjacency matrix.
     */

    private void clearGraph() {
        this.numVertices = 0;
        this.vertices = (T[]) new Object[this.DEFAULT_CAPACITY];
        this.adjMatrix = new double[this.DEFAULT_CAPACITY][this.DEFAULT_CAPACITY];
    }

    /**
     * Gets the next vertices from a given start vertex.
     * It returns an unordered list of vertices that are directly reachable from the start vertex.
     *
     * @param startVertex The vertex from which to get the next vertices.
     * @return An unordered list of vertices.
     */

    public UnorderedListADT<T> getNextVertexes(T startVertex) {
        if (!indexIsValid(getIndex(startVertex))) throw new IllegalArgumentException("Vertex does not exist");

        OrderedListADT<VertexNode<T>> vertexesList = new OrderedLinkedList<>();
        for (int i = 0; i < super.numVertices; i++) {
            if (super.adjMatrix[getIndex(startVertex)][i] != Double.POSITIVE_INFINITY) {
                vertexesList.add(new VertexNode<>(super.vertices[i], super.adjMatrix[getIndex(startVertex)][i]));
            }
        }
        UnorderedListADT<T> resultList = new ArrayUnorderedList<>();
        while (!vertexesList.isEmpty()) resultList.addToRear(vertexesList.removeFirst().getVertex());

        return resultList;
    }

}