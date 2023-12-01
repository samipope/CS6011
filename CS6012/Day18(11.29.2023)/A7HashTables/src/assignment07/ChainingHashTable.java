package assignment07;

import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ChainingHashTable implements Set<String>{

    private LinkedList<String>[] storage_;
    private HashFunctor functor_;
    private int size_;

    /**
     * constructor
     * @param capacity int
     * @param functor Hashfunctor
     */
    @SuppressWarnings("unchecked")
    public ChainingHashTable(int capacity, HashFunctor functor) {
        storage_ = (LinkedList<String>[]) new LinkedList[capacity];
        functor_ = functor;
        size_=0;
    }


    /**
     *
     * @param item
     *          - the item whose presence is ensured in this set
     * @return true if able to add
     */
    @Override
    public boolean add(String item) {
        if(item==null){
            throw new NoSuchElementException("element is null");}
        // find hash index
        int index = functor_.hash(item) % storage_.length;

        // initialize the bucket (linked list) if it's null
        if (storage_[index] == null) {
            storage_[index] = new LinkedList<>();
        }
        // check if the item is already in the list (to avoid duplicates)
        if (!storage_[index].contains(item)) {
            storage_[index].add(item);
            size_++;
            return true; // item added successfully
        }
        return false; // item was already in the set
    }

    /**
     *
     * @param items
     *          - the collection of items whose presence is ensured in this set
     * @return false if unable to add
     */
    @Override
    public boolean addAll(Collection<? extends String> items) {

        if (items == null) {
            throw new NoSuchElementException("cannot pass null");
        }
        boolean added = false;
        for (String item : items) {
            if (add(item)) {
                added = true;
            }
        }
        return added;

    }


    /**
     * clears the array
     */
    @Override
    public void clear() {

        for (int i = 0; i < storage_.length; i++) {
            storage_[i] = null;
        }
        size_ = 0;
    }

    /**
     *
     * @param item
     *          - the item sought in this set
     * @return false if the item is not present
     */
    @Override
    public boolean contains(String item) {

        if (item == null) {
            throw new NoSuchElementException("element is null");
        }
        int index = functor_.hash(item) % storage_.length;
        return storage_[index] != null && storage_[index].contains(item);

    }

    /**
     *
     * @param items
     *          - the collection of items sought in this set
     * @return true if all of them are present
     */
    @Override
    public boolean containsAll(Collection<? extends String> items) {

        if (items == null) {
            throw new NoSuchElementException("cannot pass null");
        }
        for (String item : items) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return true if the array is empty
     */
    @Override
    public boolean isEmpty() {
        return size_==0;
    }

    /**
     *
     * @param item
     *          - the item whose absence is ensured in this set
     * @return false if you are unable to remove it
     */
    @Override
    public boolean remove(String item) {

        if (item == null) {
            throw new NoSuchElementException("element is null");
        }
        int index = functor_.hash(item) % storage_.length;
        if (storage_[index] != null && storage_[index].remove(item)) {
            size_--;
            return true;
        }
        return false;
    }

    /**
     *
     * @param items
     *          - the collection of items whose absence is ensured in this set
     * @return if you are able to remove them all
     */
    @Override
    public boolean removeAll(Collection<? extends String> items) {

        if (items == null) {
            throw new NoSuchElementException("cannot pass null");
        }
        boolean removed = false;
        for (String item : items) {
            if (remove(item)) {
                removed = true;
            }
        }
        return removed;


    }

//    public int countCollisions() {
//        int collisionCount = 0;
//        for (int i = 0; i < storage_.length; i++) {
//             if (storage_[i].size() > 1 && storage_[i]!=null){
//                collisionCount++;
//            }
//             else if(storage_[i]==null){
//                 return 0;
//             }
//        }
//
//        return collisionCount;
//    }
    public int countCollisions() {
        int collisionCount = 0;
        for (int i = 0; i < storage_.length; i++) {
            // Check if the bucket is not null and has more than one element
            if (storage_[i] != null && storage_[i].size() > 1) {
                collisionCount += storage_[i].size() - 1; // Counting additional elements as collisions
            }
        }
        return collisionCount;
    }

    /**
     *
     * @return the size
     */
    @Override
    public int size() {
        return size_;
    }
}
