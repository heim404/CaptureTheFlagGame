package Bot;

/**
 * Bot interface that defines the basic operations of a bot.
 */
public interface Bot {
    /**
     * Gets the name of the bot.
     *
     * @return The name of the bot.
     */
    String getName();

    /**
     * Gets the current position of the bot.
     *
     * @return The current position of the bot.
     */
    int getCurrentPosition();

    /**
     * Sets the current position of the bot.
     *
     * @param currentPosition The new position of the bot.
     */
    void setCurrentPosition(int currentPosition);

    /**
     * Gets the iterator strategy of the bot.
     *
     * @return The iterator strategy of the bot.
     */
    StrategyImpl getIterator();

    /**
     * Sets the iterator strategy of the bot.
     *
     * @param iterator The new iterator strategy of the bot.
     */
    void setIterator(StrategyImpl iterator);

    /**
     * Executes the bot's strategy for a given round.
     *
     * @param round The round for which to execute the strategy.
     * @return The result of executing the strategy.
     */
    int executeStrategy(int round);

    /**
     * Executes an alternative strategy of the bot.
     *
     * @return The result of executing the alternative strategy.
     */
    int executeAlternativeStrategy();
}