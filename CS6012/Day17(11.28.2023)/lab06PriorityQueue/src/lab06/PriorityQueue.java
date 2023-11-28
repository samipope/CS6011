package lab06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * An interface for representing a sorted set of generically-typed items. By
 * definition, a set contains no duplicate items. The items are ordered using
 * their natural ordering (i.e., each item must be Comparable). Note that this
 * interface is much like Java's assignment06.SortedSet, but simpler.
 *
 * @author
 */
public interface PriorityQueue<T extends Comparable<? super T>> {

    /**
     * Adds an element to the queue
     *
     * @param element
     *          - the item tha twill be added
     * @throws NullPointerException
     *           if the item is null
     */
    public void add(T element);

    /**
     * Returns true if this set contains no items.
     */
    public boolean isEmpty();

    /**
     * Ensures that this set does not contain the specified item.
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually removed); otherwise, returns false
     * @throws NullPointerException
     *           if the item is null
     */
    public T removeMin();

}