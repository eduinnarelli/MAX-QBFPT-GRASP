package utils;

/**
 * Item with a value and an associated weight.
 * 
 * @author aserpa, einnarelli
 */
public class WeightedItem<E> {

    /**
     * The item value.
     */
    private final E value;

    /**
     * The item weight.
     */
    private Double w;

    /**
     * Constructor for the WeightedItem class, where an item is initialized.
     * 
     * @param value
     *      The item value.
     * @param w
     *      The item weight.
     */
    public WeightedItem(E value, Double w) {
        this.value = value;
        this.w = w;
    }

    /**
     * `value` getter.
     * 
     * @return {@link #value}.
     */
    public E getValue() { return value; };

    /**
     * `w` getter.
     * 
     * @return {@link #w}.
     */
    public Double getW() { return w; };

    /**
     * `w` setter.
     * 
     * @param w
     *      New weight.
     */
    public void setW(Double w) { this.w = w; };

}
