package Bot;

import Collections.ListasIterador.Interfaces.OrderedListADT;
import Collections.ListasIterador.Interfaces.UnorderedListADT;
import Maps.CustomNetworkADT;

/**
 * BotImpl class that implements the Bot interface.
 * It represents a bot with a name, current position, strategy, occupied positions, and a network.
 */
public class BotImpl implements Bot {
    private final String name;
    private int currentPosition;
    private StrategyImpl strategy;
    private static UnorderedListADT<Integer> ocupiedPositions;
    private CustomNetworkADT<Integer> network;

    /**
     * Constructor for the BotImpl class.
     *
     * @param name The name of the bot.
     * @param strategy The strategy of the bot.
     * @param network The network of the bot.
     * @param ocupiedPositions The occupied positions of the bot.
     */
    public BotImpl(String name, StrategyImpl strategy, CustomNetworkADT<Integer> network, UnorderedListADT<Integer> ocupiedPositions) {
        this.name = name;
        this.strategy = strategy;
        this.ocupiedPositions = ocupiedPositions;
        this.network = network;
    }

    /**
     * Gets the name of the bot.
     *
     * @return The name of the bot.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the current position of the bot.
     *
     * @return The current position of the bot.
     */
    @Override
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets the current position of the bot.
     *
     * @param currentPosition The new position of the bot.
     */
    @Override
    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Gets the strategy of the bot.
     *
     * @return The strategy of the bot.
     */
    @Override
    public StrategyImpl getIterator() {
        return strategy;
    }

    /**
     * Sets the strategy of the bot.
     *
     * @param iterator The new strategy of the bot.
     */
    @Override
    public void setIterator(StrategyImpl iterator) {
        this.strategy = iterator;
    }

    /**
     * Executes the bot's strategy for a given round.
     * If the next position is not occupied, it moves to that position.
     * If the next position is occupied, it executes an alternative strategy.
     *
     * @param round The round for which to execute the strategy.
     * @return The new position of the bot after executing the strategy.
     */
    @Override
    public int executeStrategy(int round) {
//        System.out.println("occupied positions: " + ocupiedPositions.toString());
        int nextPosition = strategy.calculateNextMove();
        if (nextPosition != -1 && !ocupiedPositions.contains(nextPosition)) {

                ocupiedPositions.remove(currentPosition);

            currentPosition = nextPosition;
            ocupiedPositions.addToRear(nextPosition);
//            System.out.println("Strategy executed.");
            return nextPosition;
        } else return executeAlternativeStrategy();
    }

    /**
     * Executes an alternative strategy of the bot.
     * It moves to the first unoccupied position found in the network.
     *
     * @return The new position of the bot after executing the alternative strategy, or -1 if no unoccupied position is found.
     */
    @Override
    public int executeAlternativeStrategy() {
        UnorderedListADT<Integer> alternativePositions = network.getNextVertexes(currentPosition);
        for (Integer position : alternativePositions) {
            if (!ocupiedPositions.contains(position)) {
                ocupiedPositions.remove(currentPosition); // remove old position
                currentPosition = position;
                ocupiedPositions.addToRear(position); // add new position
//                System.out.println("Alternative strategy executed.");
                return position;
            }
        }
        return -1; // return -1 if no unoccupied position is found
    }
}