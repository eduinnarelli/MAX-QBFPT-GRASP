package metaheuristics.grasp.reactive;

import java.util.Map;

import utils.WeightedMap;
import utils.WeightedItem;

/** 
 * Map of alphas ({@link Alpha}) that can be selected during the Reactive
 * GRASP constructive phase.
 */
@SuppressWarnings("serial")
public class AlphaMap extends WeightedMap<Double> {

    /** 
     * Constructor for the AlphaList class, where an AlphaList is 
     * initialized. 
     * 
     * @param m
     *      Number of alphas in the list.
     */
    public AlphaMap(Integer m) {
        super(m);
    }

    /**
     * {@inheritDoc}
     * 
     * Each {@link Alpha} is initialized with the value i / m and probability 
     * 1.0 / m of being selected, where i is its index in the list and m is 
     * the total number of alphas ({@link WeightedMap#m}).
     */
    @Override
    public void initializeBag() {
        for (int i = 1; i <= m; i++) {
            Double val = (double) i / m;
            this.put(val, new Alpha(val, 1.0 / m));
        }
    }

    /**
     * Each alpha has its probability updated as the ratio between the 
     * individual and accumulated {@link Alpha#getQ(double)}. 
     * 
     * @param incumbentCost
     *      Cost of the best solution found so far.
     */
    public void updateProbabilities(Double incumbentCost) {

        Double accQ = 0.0;

        // Accumulate all Q's.
        for (Map.Entry<Double, WeightedItem<Double>> kv : this.entrySet()) {
            WeightedItem<Double> item = kv.getValue();
            Alpha a = (Alpha) item; // cast to alpha
            accQ += a.getQ(incumbentCost);
        }

        // Update probabilities (weights).
        for (Map.Entry<Double, WeightedItem<Double>> kv : this.entrySet()) {
            WeightedItem<Double> item = kv.getValue();
            Alpha a = (Alpha) item; // cast to alpha
            a.setW(a.getQ(incumbentCost) / accQ);
        }

        // Remove elements with probability 0 from the map.
        this.entrySet().removeIf(entry -> entry.getValue().getW() == 0);

    }
    
}
