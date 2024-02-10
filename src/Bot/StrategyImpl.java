package Bot;

import java.util.Iterator;

/**
 * StrategyImpl class that implements the Strategy interface.
 * It represents a strategy that uses an iterator to calculate the next move.
 */
public class StrategyImpl implements Strategy {
    private Iterator<Integer> iterator;

    /**
     * Constructor for the StrategyImpl class.
     *
     * @param iterator The iterator used to calculate the next move.
     */
    public StrategyImpl(Iterator<Integer> iterator) {
        this.iterator = iterator;
    }

    /**
     * Calculates the next move based on the iterator.
     * If the iterator has a next element, it returns the next element.
     * If the iterator does not have a next element, it returns -1.
     *
     * @return The next move as an integer, or -1 if the iterator does not have a next element.
     */
    @Override
    public int calculateNextMove() {
        return iterator.hasNext() ? iterator.next() : -1;
    }
}