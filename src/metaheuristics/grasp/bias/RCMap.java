package metaheuristics.grasp.bias;

import java.util.function.Function;
import java.util.Map;

import utils.WeightedMap;
import utils.WeightedItem;

/** 
 * Map of restricted candidates that can be selected during the GRASP 
 * constructive phase.
 * 
 * @author aserpa, einnarelli
 */
@SuppressWarnings("serial")
public class RCMap<E> extends WeightedMap<Double, E> {

    /** 
     * Constructor for the RCMap class, where the WeightedMap constructor is 
     * called. 
     */
    public RCMap() {
        super();
    }

    /**
     * Each alpha has its probability updated as the ratio between the 
     * individual and accumulated {@link Alpha#getQ(double)}. 
     * 
     * @param incumbentCost
     *      Cost of the best solution found so far.
     */
    public void updateWeights(Function<Integer, Double> bias) {

        // Update weights.
        int r = 1;
        for (Map.Entry<Double, WeightedItem<E>> kv : this.entrySet()) {
            WeightedItem<E> item = kv.getValue();
            item.setW(bias.apply(r));
            r++;
        }

    }
    
}
