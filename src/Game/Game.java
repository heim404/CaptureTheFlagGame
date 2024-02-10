package Game;

import Bot.BotImpl;
import Bot.StrategyImpl;
import Collections.ListasIterador.Interfaces.UnorderedListADT;
import Maps.CustomNetworkADT;
import Player.PlayerImpl;

/**
 * Game interface that defines the basic operations of a game.
 */
public interface Game {

    /**
     * Sets up the game.
     */
    public void setupGame();

    /**
     * Starts the game.
     */
    void startGame();

    /**
     * Gets the occupied positions in the game.
     *
     * @return The occupied positions as an unordered list of integers.
     */
    UnorderedListADT<Integer> getOccupiedPositions();

    /**
     * Finds a bot by its name.
     *
     * @param botName The name of the bot.
     * @return The bot if found, null otherwise.
     */
    BotImpl findBotByName(String botName);

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    boolean isGameOver();

    /**
     * Sets up the map for the game.
     *
     * @return The network as a CustomNetworkADT of integers.
     */
    CustomNetworkADT<Integer> setupNetwork();

    /**
     * Sets the player's flag.
     */
    void setPlayerFlag();

    /**
     * Sets up the bots for the game.
     */
    void setupBots();

    /**
     * Sets up a bot for a player.
     *
     * @param player The player for whom to set up the bot.
     * @param botNumber The number of the bot to set up.
     */
    void setupBotForPlayer(PlayerImpl player, int botNumber);

    /**
     * Gets the strategy for a player.
     *
     * @param player The player for whom to get the strategy.
     * @return The strategy.
     */
    StrategyImpl getStrategy(PlayerImpl player);
}