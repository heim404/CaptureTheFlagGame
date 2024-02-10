package Player;

import Bot.BotImpl;
import Collections.ListasIterador.Interfaces.UnorderedListADT;

/**
 * Player interface that defines the basic operations of a player.
 */
public interface Player {

    /**
     * Adds a bot to the player's list of bots.
     *
     * @param bot The bot to be added.
     */
    void addBot(BotImpl bot);

    /**
     * Gets the flag of the player.
     *
     * @return The flag of the player.
     */
    int getFlag();

    /**
     * Sets the flag of the player.
     *
     * @param flag The new flag of the player.
     */
    void setFlag(int flag);

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    String getName();

    /**
     * Gets the list of bots of the player.
     *
     * @return The list of bots of the player.
     */
    UnorderedListADT<BotImpl> getBots();
}
