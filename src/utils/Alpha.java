package utils;

/**
 * Alpha used in Reactive GRASP, which have a probability of being selected that
 * relies on the value of the solutions that used it. 
 */
public class Alpha {

    /**
     * The alpha value.
     */
    private final Double value;

    /**
     * The alpha probability.
     */
    private Double p;

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
     * Constructor for the Alpha class, where an Alpha object is initialized.
     * 
     * @param val
     *      Alpha value.
     * @param initialP
     *      Initial alpha probability.
     */
    public Alpha(Double val, Double initialP) {

        // Assert value is valid.
        assert(val >= 0.0 && val <= 1.0) : "Alpha value should be in [0, 1].";

        // Initialize attributes.
        value = val;
        p = initialP;
        A = 0.0;

    }

    /**
     * `value` getter.
     * 
     * @return {@link #value}.
     */
    public Double getValue() { return value; };

    /**
     * `p` getter.
     * 
     * @return {@link #p}.
     */
    public Double getP() { return p; };

    /**
     * `p` setter.
     * 
     * @param prob
     *      New probability.
     */
    public void setP(Double prob) { p = prob; };

    /**
     * `A` getter.
     * 
     * @return {@link #A}.
     */
    public Double getA() { return A; };

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
