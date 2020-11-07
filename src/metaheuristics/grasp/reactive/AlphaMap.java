package metaheuristics.grasp.reactive;

import java.util.Map;

import utils.WeightedMap;
import utils.WeightedItem;

/** 
 * Map of alphas ({@link Alpha}) that can be selected during the Reactive
 * GRASP constructive phase.
 * 
 * @author aserpa, einnarelli
 */
@SuppressWarnings("serial")
public class AlphaMap extends WeightedMap<Double, Double> {

    /**
     * Number of possible alphas.
     */
    private final Integer m;

    /** 
     * Constructor for the AlphaMap class, where an AlphaMap is 
     * initialized. 
     * 
     * @param m
     *      Number of alphas in the map.
     */
    public AlphaMap(Integer m) {

        // Call WeightedMap constructor.
        super();

        // Initialize m and the alphas.
        this.m = m;
        initializeAlphas();

    }

    /**
     * Each {@link Alpha} is initialized with the value i / m and probability 
     * 1.0 / m of being selected, where i is its index in the map and m is 
     * the total number of alphas ({@link #m}).
     */
    public void initializeAlphas() {
        for (int i = 1; i <= m; i++) {
            Double val = (double) i / m;
            this.put(val, new Alpha(val, 1.0 / m));
        }
    }

    /**
     * Each alpha has its weight updated as the individual {@link 
     * Alpha#getQ(double)}. 
     * 
     * @param incumbentCost
     *      Cost of the best solution found so far.
     */
    public void updateWeights(Double incumbentCost) {

        // Update weights.
        for (Map.Entry<Double, WeightedItem<Double>> kv : this.entrySet()) {
            WeightedItem<Double> item = kv.getValue();
            Alpha a = (Alpha) item; // cast to alpha
            a.setW(a.getQ(incumbentCost));
        }

        // Remove elements with weight 0 from the map.
        this.entrySet().removeIf(entry -> entry.getValue().getW() == 0);

    }
    
}
