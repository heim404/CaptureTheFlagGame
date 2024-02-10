package Player;

import Bot.BotImpl;
import Collections.ListasIterador.Interfaces.UnorderedListADT;
import Collections.ListasIterador.Classes.ArrayUnorderedList;

/**
 * This class represents a player in the game.
 * Each player has a name, a list of bots, and a flag.
 */
public class PlayerImpl implements Player {
    private String name;
    private UnorderedListADT<BotImpl> bots;
    private int flag;

    /**
     * Constructor for the PlayerImpl class.
     * Initializes the name of the player and the list of bots.
     * @param name - the name of the player.
     */
    public PlayerImpl(String name) {
        this.name = name;
        this.bots = new ArrayUnorderedList<>();
    }

    /**
     * Adds a bot to the player's list of bots.
     * @param bot - the bot to be added.
     */
    @Override
    public void addBot(BotImpl bot) {
        bots.addToRear(bot);
    }

    /**
     * Returns the flag of the player.
     * @return int - the flag of the player.
     */
    @Override
    public int getFlag() {
        return flag;
    }

    /**
     * Sets the flag of the player.
     * @param flag - the flag to be set.
     */
    @Override
    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * Returns the name of the player.
     * @return String - the name of the player.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the list of bots of the player.
     * @return UnorderedListADT<BotImpl> - the list of bots of the player.
     */
    @Override
    public UnorderedListADT<BotImpl> getBots() {
        return bots;
    }
}