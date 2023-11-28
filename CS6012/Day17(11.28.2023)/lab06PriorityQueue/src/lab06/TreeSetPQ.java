package lab06;

//ElisabethFrischknecht, Sami Pope, and Jessica Payton

import java.util.TreeSet;

public class TreeSetPQ<T extends Comparable<? super T>> implements PriorityQueue<T>{
    TreeSet<T> data_ = new TreeSet<>();

    /**
     * Adds an element to the queue
     * @param element - the item tha twill be added
     */
    @Override
    public void add(T element) {
        data_.add(element);
    }

    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        return data_.isEmpty();
    }

    /**
     * removes the minimum of the set
     * @return true if this set changed as a result of this method call (that is, if
     * the input item was actually removed); otherwise, returns false
     */
    @Override
    public T removeMin() {
        return data_.pollFirst();
    }
}