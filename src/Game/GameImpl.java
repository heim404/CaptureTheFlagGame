package Game;

import Collections.ListasIterador.Classes.ArrayUnorderedList;
import Collections.ListasIterador.Interfaces.UnorderedListADT;
import Collections.Queue.ArrayQueue;
import Maps.CustomNetworkADT;
import Maps.CustomNetworkBidirecional;
import Maps.CustomNetworkUniDiretional;
import Player.PlayerImpl;
import Bot.*;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * GameImpl class that implements the Game interface.
 * It represents a game with two players (Red and Blue), a network, a queue of bot positions, a round number, and an input handler.
 */
public class GameImpl implements Game {
    private PlayerImpl Red, Blue;
    private CustomNetworkADT<Integer> network;
    private ArrayQueue<String> botPositions;
    private int round;
    private InputHandler inputHandler;

    /**
     * Constructor for the GameImpl class.
     * It initializes the input handler and sets up the game.
     */
    public GameImpl() {
        this.inputHandler = new InputHandler();
        setupGame();
    }

    /**
     * Sets up the game.
     * It initializes the Red and Blue players, the bot positions queue, the network, the player flags, the bots, and the round number.
     * Then it starts the game.
     */
    @Override
    public void setupGame() {
        Red = new PlayerImpl("Red");
        Blue = new PlayerImpl("Blue");
        botPositions = new ArrayQueue<>();
        network = setupNetwork();
        setPlayerFlag();
        setupBots();
        round = 1;
        startGame();
    }

    /**
     * Starts the game.
     * It continues until the game is over.
     * In each round, if there are bots in the queue, it dequeues a bot and executes its strategy.
     * After each move, it checks if the game is over.
     * If the game is not over, it enqueues the bot back at the end of the queue.
     * It also delays for 2 seconds after each round.
     */

    @Override
    public void startGame() {
        while (!isGameOver()) {
            if (!botPositions.isEmpty()) {
//                System.out.println("occupied positions(Game2): " + botPositions);
                String botName = botPositions.dequeue();
                BotImpl currentBot = findBotByName(botName);
                if (currentBot != null) {
                    System.out.println(currentBot.getName() + "'s turn || started at position " + currentBot.getCurrentPosition());
                    int nextMove = currentBot.executeStrategy(round);
                    if (nextMove != -1) {
                        System.out.println(currentBot.getName() + " moved to position " + currentBot.getCurrentPosition());
                    } else {
                        System.out.println(currentBot.getName() + " cannot move.");
                    }

                }
//                System.out.println("occupied positions(Game2): " + botPositions);
                // Check for game over condition after each move
                if (isGameOver()) {
                    break;
                }
                botPositions.enqueue(botName); // Put the bot back at the end of the queue
                round++;
                // Delay for half a second
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Sleep interrupted: " + e.getMessage());
                }
            }
        }
        System.out.println("Game over!");
    }

    /**
     * Gets the occupied positions in the game.
     * It iterates through the bots of both Red and Blue players and adds their current positions to the list of occupied positions.
     *
     * @return The occupied positions as an unordered list of integers.
     */

    @Override
    public UnorderedListADT<Integer> getOccupiedPositions() {
        UnorderedListADT<Integer> occupiedPositions = new ArrayUnorderedList<>();
        for (BotImpl bot : Red.getBots()) {
            occupiedPositions.addToRear(bot.getCurrentPosition());
        }
        for (BotImpl bot : Blue.getBots()) {
            occupiedPositions.addToRear(bot.getCurrentPosition());
        }
        return occupiedPositions;
    }

    /**
     * Finds a bot by its name.
     * It iterates through the bots of both Red and Blue players and returns the bot if its name matches the given name.
     *
     * @param botName The name of the bot.
     * @return The bot if found, null otherwise.
     */
    @Override
    public BotImpl findBotByName(String botName) {
        // Iterate through both Red and Blue teams to find the bot
        for (BotImpl bot : Red.getBots()) {
            if (bot.getName().equals(botName)) {
                return bot;
            }
        }
        for (BotImpl bot : Blue.getBots()) {
            if (bot.getName().equals(botName)) {
                return bot;
            }
        }
        return null;
    }

    /**
     * Checks if the game is over.
     * The game is over if any of Red's bots have reached Blue's flag or any of Blue's bots have reached Red's flag.
     *
     * @return True if the game is over, false otherwise.
     */

    @Override
    public boolean isGameOver() {
        // Check if any of Red's bots have reached Blue's flag
        for (BotImpl bot : Red.getBots()) {
            if (bot.getCurrentPosition() == Blue.getFlag()) {
                System.out.println("Player Red wins! Bot " + bot.getName() + " has captured Blue's flag.");
                return true;
            }
        }

        // Check if any of Blue's bots have reached Red's flag
        for (BotImpl bot : Blue.getBots()) {
            if (bot.getCurrentPosition() == Red.getFlag()) {
                System.out.println("Player Blue wins! Bot " + bot.getName() + " has captured Red's flag.");
                return true;
            }
        }

        // If no bot has reached the opposing flag, the game is not over
        return false;
    }

    /**
     * Sets up the network for the game.
     * It prompts the user to choose the network type and map setup.
     * If the map setup is to import from a JSON file, it imports the network from a JSON file.
     * If the map setup is to generate a random graph, it generates a random graph for the network.
     * If the map was generated, it prompts the user to choose whether to export the map to a JSON file.
     *
     * @return The network as a CustomNetworkADT of integers.
     */
    @Override
    public CustomNetworkADT<Integer> setupNetwork() {
        int mapType = inputHandler.getNetworkType();
        int mapSetup = inputHandler.getMapSetup();
        CustomNetworkADT<Integer> network = (mapType == 1) ? new CustomNetworkBidirecional<>() : new CustomNetworkUniDiretional<>();
        boolean isMapGenerated = false;

        if (mapSetup == 1) {
            network.importFromJson();
        } else {
            int vertices = inputHandler.getNumberOfVertices();
            double density = inputHandler.getMapDensity();
            network.generateRandomGraph(vertices, density);
            isMapGenerated = true;
        }

        System.out.println(network);

        // Prompt for export only if the map was generated
        if (isMapGenerated) {
            int exportChoice = inputHandler.getExportChoice();
            if (exportChoice == 1) {
                String filename = inputHandler.getFilename();
                if (network instanceof CustomNetworkBidirecional) {
                    ((CustomNetworkBidirecional<Integer>) network).exportToJson(filename);
                } else if (network instanceof CustomNetworkUniDiretional) {
                    ((CustomNetworkUniDiretional<Integer>) network).exportToJson(filename);
                }
                System.out.println("Map exported successfully to " + filename);
            }
        }

        return network;
    }

    /**
     * Sets the player's flag.
     * It prompts the user to enter the flag position for both Red and Blue players.
     */

    @Override
    public void setPlayerFlag() {
        Red.setFlag(inputHandler.getFlagPosition(Red.getName()));
        Blue.setFlag(inputHandler.getFlagPosition(Blue.getName()));
    }


    /**
     * Sets up the bots for the game.
     * It prompts the user to enter the number of bots.
     * Then it sets up the bots for both Red and Blue players.
     */
    @Override
    public void setupBots() {
        int numberOfBots = inputHandler.getNumberOfBots();
        for (int i = 0; i < numberOfBots; i++) {
                setupBotForPlayer(Red, i);
                setupBotForPlayer(Blue, i);
        }
    }

    /**
     * Sets up the bots for the game.
     * It prompts the user to enter the number of bots.
     * Then it sets up the bots for both Red and Blue players.
     */

    @Override
    public void setupBotForPlayer(PlayerImpl player, int botNumber) {
        System.out.println("Setting up bot " + (botNumber + 1) + " for " + player.getName());
        BotImpl bot = new BotImpl((player.getName() + " " + (botNumber + 1)), getStrategy(player), network, getOccupiedPositions());
        bot.setCurrentPosition(player.getFlag());
        player.addBot(bot);
        botPositions.enqueue(bot.getName()); // Enqueue bots in alternating order
    }

    /**
     * Gets the strategy for a player.
     * It prompts the user to choose the strategy.
     * If the choice is 1, it returns a BFS iterator strategy.
     * If the choice is 2, it returns a DFS iterator strategy.
     * If the choice is 3, it returns shortest path iterator strategy.
     *
     * @param player The player for whom to get the strategy.
     * @return The strategy as a StrategyImpl, or null if the choice is not 1, 2, or 3.
     */

    @Override
    public StrategyImpl getStrategy(PlayerImpl player) {
        int choice = inputHandler.getStrategy();
        Iterator<Integer> strategyIterator;

        if (choice == 1) strategyIterator = network.iteratorBFS(player.getFlag());
        else if (choice == 2) strategyIterator = network.iteratorDFS(player.getFlag());
        else if (choice == 3)
            strategyIterator = network.iteratorShortestPath(player.getFlag(), (player.getName().equals("Red")) ? Blue.getFlag() : Red.getFlag());
        else return null;

        return new StrategyImpl(strategyIterator);
    }
}