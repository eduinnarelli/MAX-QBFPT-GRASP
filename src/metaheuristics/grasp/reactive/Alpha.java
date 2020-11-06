package metaheuristics.grasp.reactive;

import utils.WeightedItem;

/**
 * Alpha used in Reactive GRASP, which have a changeable probability of being
 * selected that relies on the value of the solutions that used it.
 */
public class Alpha extends WeightedItem<Double> {

    /**
     * Average value of all solutions found using this alpha.
     */
    private Double A;

    /**
     * Number of times this alpha was used.
     */
    private Integer timesUsed;

    /**
     * Accumulated cost of solutions that used alpha.
     */
    private Double accCost;

    /**
     * Constructor for a alpha used in the Reactive GRASP method.
     * 
     * @param value
     *      The alpha value (within the range [0,1]).
     * @param w
     *      The probability of alpha being selected.
     */
    public Alpha(Double value, Double w) {

        // Call WeightedItem constructor.
        super(value, w);

        // Initialize attributes.
        A = accCost = 0.0;
        timesUsed = 0;

    }

    /**
     * Calculates the ratio between the cost of the best solution and {@link 
     * #A}.
     * 
     * @param incumbentCost
     *      Cost of the best solution found so far.
     * 
     * @return {@code incumbentCost / A}.
     */
    public Double getQ(Double incumbentCost) {
        
        if (A == 0.0) return 0.0;

        return incumbentCost / A; 
    }

    /**
     * Method to update {@link #A}, after this alpha is used in a new solution.
     *
     * @param cost
     *      Cost of a new solution that uses this alpha.
     */
    public void updateA(Double cost) {
        
        // Update accCost and timesUsed
        timesUsed++;
        accCost += cost;
        
        // Calculate average
        A = accCost / timesUsed;

    };

}
