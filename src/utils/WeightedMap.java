package utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Map of weighted items, which can be randomly selected according to their
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
     * Number of elements in map.
     */
    protected final Integer m;

    /**
     * Initialize the map of items.
     */
    public abstract void initializeMap();

    /**
     * Constructor for the WeightedMap class, where the map is initialized.
     * 
     * @param m
     *      Number of elements.
     */
    public WeightedMap(Integer m) {

        // Call ArrayList constructor
        super();

        // Initialize m and the map
        this.m = m;
        initializeMap();

    }

    /**
     * Randomly select an item from the map, taking it's weight into account.
     * 
     * @return
     *      The selected item.
     */
    public E selectItem() {

        Double total = 0.0;
        NavigableMap<Double, E> navMap = new TreeMap<Double, E>();

        /**
         * Accumulate weights and put items in the navigable map. Example:
         * - map = {"Cat" with weight 3, "Dog" with weight 5};
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
