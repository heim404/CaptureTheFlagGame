package Maps;

import Collections.Grafos.NetworkBiDirectional;
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
import Collections.Grafos.NetworkBiDirectional;

/**
 * This class extends the NetworkBiDirectional class and implements the CustomNetworkADT interface.
 * It represents a custom bidirectional network, which is a graph where each edge has a direction and a weight.
 * The vertices of the graph are of a generic type T.
 * The class provides methods for generating a random graph, importing a graph from a JSON file, exporting a graph to a JSON file, clearing the graph, and getting the next vertices from a given start vertex.
 */
public class CustomNetworkBidirecional<T> extends NetworkBiDirectional<T> implements CustomNetworkADT<T> {

    public void generateRandomGraph(int numberOfVertices, double densidade) {
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
            int numEdges = (int) ((numberOfVertices * (numberOfVertices - 1)) / 2.0 * densidade);

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
                    adjMatrix[v1][v2] = weight;
                    adjMatrix[v2][v1] = weight;
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
     * This method is used to import a graph from a JSON file.
     * The JSON file should contain an array of vertices, where each vertex is an object with a "vertex" field and an "edges" field.
     * The "vertex" field is a string that represents the name of the vertex.
     * The "edges" field is an array of edges, where each edge is an object with a "to" field and a "weight" field.
     * The "to" field is a string that represents the name of the vertex that the edge is connected to.
     * The "weight" field is a double that represents the weight of the edge.
     * If the graph is not empty before the import, it will be cleared.
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

        // First phase: Add all vertices
        for (Object vertexObj : graphArray) {
            JSONObject vertexJson = (JSONObject) vertexObj;
            String vertexIdStr = (String) vertexJson.get("vertex");
            int vertexName = Integer.parseInt(vertexIdStr);
            T vertex = (T) Integer.valueOf(vertexName);
            this.addVertex(vertex);
        }

        // Second phase: Add all edges
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
                this.addEdge(vertex, toVertex, weight);
            }
        }
    }
    /**
     * This method is used to export a graph to a JSON file.
     * The JSON file will contain an array of vertices, where each vertex is an object with a "vertex" field and an "edges" field.
     * The "vertex" field is a string that represents the name of the vertex.
     * The "edges" field is an array of edges, where each edge is an object with a "to" field and a "weight" field.
     * The "to" field is a string that represents the name of the vertex that the edge is connected to.
     * The "weight" field is a double that represents the weight of the edge.
     * The filename parameter is the name of the file to which the graph will be exported.
     * If the filename does not end with ".json", the extension will be appended.
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
     * This method is used to clear the graph. It resets the number of vertices to 0,
     * and reinitializes the vertices array and adjacency matrix to their default capacity.
     */
    private void clearGraph() {
        this.numVertices = 0;
        this.vertices = (T[]) new Object[this.DEFAULT_CAPACITY];
        this.adjMatrix = new double[this.DEFAULT_CAPACITY][this.DEFAULT_CAPACITY];
    }
    /**
     * This method is used to get the next vertices from a given start vertex in the graph.
     * It first checks if the start vertex is valid, if not, it throws an IllegalArgumentException.
     * Then, it creates an ordered list of VertexNode objects, where each VertexNode represents a vertex that is connected to the start vertex.
     * It iterates over the adjacency matrix, and for each vertex that is connected to the start vertex, it creates a VertexNode with the vertex and the weight of the edge, and adds it to the ordered list.
     * After that, it creates an unordered list of vertices, and while the ordered list is not empty, it removes the first VertexNode from the ordered list, gets its vertex, and adds it to the unordered list.
     * Finally, it returns the unordered list of vertices.
     *
     * @param startVertex the vertex from which to get the next vertices
     * @return an unordered list of vertices that are connected to the start vertex
     * @throws IllegalArgumentException if the start vertex is not valid
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