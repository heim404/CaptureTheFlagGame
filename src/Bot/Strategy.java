package Bot;

/**
 * Strategy interface that defines the basic operations of a strategy.
 */
public interface Strategy {

    /**
     * Calculates the next move based on the strategy.
     *
     * @return The next position as an integer.
     */
    int calculateNextMove();

}