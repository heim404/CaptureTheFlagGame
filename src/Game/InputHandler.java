package Game;

import java.util.Scanner;

/**
 * This class is responsible for handling all the user inputs in the game.
 */
public class InputHandler {
    private Scanner scanner;

    /**
     * Constructor for the InputHandler class.
     * Initializes the scanner object.
     */
    public InputHandler() {
        scanner = new Scanner(System.in);
    }

    /**
     * Prompts the user to choose the network type.
     * @return int - the user's choice of network type.
     */
    public int getNetworkType() {
        System.out.println("Choose the network type:\n1 - Network Bidirectional\n2 - Network Unidirectional");
        return scanner.nextInt();
    }

    /**
     * Prompts the user to choose between importing an existing map or generating a new one.
     * @return int - the user's choice of map setup.
     */
    public int getMapSetup() {
        System.out.println("Do you want to import an existing map or generate a new one?\n1 - Import existing map\n2 - Generate new map");
        return scanner.nextInt();
    }

    /**
     * Prompts the user to choose whether to export the generated map or not.
     * @return int - the user's choice of exporting the map.
     */
    public int getExportChoice() {
        System.out.println("Do you want to export the generated map?\n1 - Yes\n2 - No");
        return scanner.nextInt();
    }

    /**
     * This method prompts the user to input the density of the map.
     * The density should be a double value between 0.0 and 1.0.
     * The density of the map determines how many edges are in the graph relative to the maximum possible number of edges.
     * A density of 0.0 means there are no edges in the graph, and a density of 1.0 means the graph is a complete graph (i.e., there is an edge between every pair of vertices).
     *
     * @return double - the density of the map entered by the user.
     */
    public double getMapDensity() {
        System.out.println("Insert the map density (0.0 - 1.0): ");
        return scanner.nextDouble();
    }
    /**
     * This method prompts the user to input the number of vertices in the map.
     * The number of vertices should be an integer value.
     * Each vertex in the map represents a location.
     *
     * @return int - the number of vertices entered by the user.
     */
    public int getNumberOfVertices() {
        System.out.println("Enter the number of vertices: ");
        return scanner.nextInt();
    }

    /**
     * Prompts the user to enter the filename to export the map.
     * @return String - the filename entered by the user.
     */
    public String getFilename() {
        System.out.println("Enter the filename to export the map:");
        return scanner.nextLine();
    }

    /**
     * Prompts the user to choose their flag position.
     * @param playerName - the name of the player.
     * @return int - the flag position chosen by the user.
     */
    public int getFlagPosition(String playerName) {
        System.out.println(playerName + ", choose your flag position:");
        return scanner.nextInt();
    }

    /**
     * Prompts the user to enter the number of bots to be in the game.
     * @return int - the number of bots entered by the user.
     */
    public int getNumberOfBots() {
        System.out.println("How many Bots to be in the game: ");
        return scanner.nextInt();
    }

    /**
     * Prompts the user to choose the strategy.
     * @return int - the strategy chosen by the user.
     */
    public int getStrategy() {
        System.out.println("Choose the strategy: \n1 - BFS\n2 - DFS\n3 - Shortest Path");
        return scanner.nextInt();
    }
}