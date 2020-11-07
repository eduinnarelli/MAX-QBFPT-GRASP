package utils;

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
public abstract class WeightedMap<E, T> extends TreeMap<E, WeightedItem<T>> {

    /**
     * A random number generator.
     */
	static Random rng = new Random(0);

    /**
     * Constructor for the WeightedMap class, where the TreeMap constructor
     * is called.
     */
    public WeightedMap() {
        super();
    }

    /**
     * Randomly select an item from the map, taking it's weight into account.
     * 
     * @return
     *      The selected item.
     */
    public T selectItem() {

        Double total = 0.0;
        NavigableMap<Double, T> navMap = new TreeMap<Double, T>();

        /**
         * Accumulate weights and put items in the navigable map. Example:
         * - map = {"Cat" with weight 3, "Dog" with weight 5};
         * - map = {3: "Cat", 8: "Dog"}.
         */
        for (Map.Entry<E, WeightedItem<T>> kv : this.entrySet()) {
            WeightedItem<T> item = kv.getValue();
            total += item.getW();
            navMap.put(total, item.getValue());
        }

        /* Get a random value in the range [0, total] and returns the item
         * with the closest higher weight. */
        Double randValue = rng.nextDouble() * total;
        return navMap.higherEntry(randValue).getValue();

    }
    
}
