package utils;

import java.util.LinkedHashMap;
import java.util.Map;
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
public abstract class WeightedMap<E> extends LinkedHashMap<E, WeightedItem<E>> {

    /**
     * A random number generator.
     */
	static Random rng = new Random(0);

    /**
     * Number of elements in bag.
     */
    protected final Integer m;

    /**
     * Initialize the bag of items.
     */
    public abstract void initializeBag();

    /**
     * Constructor for the WeightedBag class, where the bag is initialized.
     * 
     * @param m
     *      Number of elements.
     */
    public WeightedMap(Integer m) {

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
        NavigableMap<Double, E> navMap = new TreeMap<Double, E>();

        /**
         * Accumulate weights and put items in the navigable map. Example:
         * - bag = {"Cat" with weight 3, "Dog" with weight 5};
         * - map = {3: "Cat", 8: "Dog"}.
         */
        for (Map.Entry<E, WeightedItem<E>> kv : this.entrySet()) {
            WeightedItem<E> item = kv.getValue();
            total += item.getW();
            navMap.put(total, item.getValue());
        }

        /* Get a random value in the range [0, total] and returns the item
         * with the closest higher weight. */
        Double randValue = rng.nextDouble() * total;
        return navMap.higherEntry(randValue).getValue();

    }
    
}
