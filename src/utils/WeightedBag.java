package utils;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Bag of weighted items, which can be randomly selected according to their
 * weight.
 * 
 * @author aserpa, einnarelli
 */
@SuppressWarnings("serial")
public abstract class WeightedBag<E> extends ArrayList<WeightedItem<E>> {

    /**
     * A random number generator.
     */
	static Random rng = new Random(0);

    /**
     * Number of elements in bag.
     */
    private final Integer m;

    /**
     * Initialize the bag of items.
     */
    public abstract void initializeBag();

    /**
     * Update the items in the bag.
     */
    public abstract void updateItems();

    /**
     * Constructor for the WeightedBag class, where the bag is initialized.
     * 
     * @param m
     *      Number of elements.
     */
    public WeightedBag(Integer m) {

        // Call ArrayList constructor
        super();

        // Initialize m and the bag
        this.m = m;
        initializeBag();

    }

    /**
     * Randomly select an item from the bag, taking it's weight into account.
     * 
     * @return
     *      The selected item.
     */
    public E selectItem() {

        Double total = 0.0;
        NavigableMap<Double, E> map = new TreeMap<Double, E>();

        /**
         * Accumulate weights and put items in the map. Example:
         * - bag = {"Cat" with weight 3, "Dog" with weight 5};
         * - map = {3: "Cat", 8: "Dog"}.
         */
        for (WeightedItem<E> item: this) {
            total += item.getW();
            map.put(total, item.getValue());
        }

        /* Get a random value in the range [0, total] and returns the item
         * with the closest higher weight. */
        Double randValue = rng.nextDouble() * total;
        return map.higherEntry(randValue).getValue();

    }
    
}
