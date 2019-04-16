package de.uos.inf.ko.ga.graph.util;

/**
 * @author  Henrik Gerdes
 * @param <V1> Bounds two values in a pair
 * @param <V2> Bounds two values in a pair
 */
public class Pair<V1,V2> {

    //Save the values
    private V1 first;
    private V2 second;

    /**
     * Constructs a new Pair
     * @param first first value
     * @param second second value
     */
    public Pair(V1 first, V2 second){
        this.first = first;
        this.second = second;
    }

    /**
     * Getter for first
     * @return the first value
     */
    public V1 getFirst() {
        return first;
    }

    /**
     * Getter for second
     * @return the second value
     */
    public V2 getSecond() {
        return second;
    }
}
