package assignment03;

import java.util.*;
import java.lang.reflect.Array;


public class BinarySearchSet<E> implements SortedSet<E>, Iterable<E> {
    private int capacity;
    private E[] elements;
    private int size;
    private Comparator<? super E> comparator;

    // Constructors
    public BinarySearchSet() {
        this.capacity=10;
        this.elements = (E[]) new Object[capacity];
        this.size=0;
    }

    public BinarySearchSet(Comparator<? super E> comparator) {
        this.capacity=10;
        this.elements = (E[]) new Object[capacity];
        this.comparator = comparator;
        this.size=0;
    }


    /**
     * Performs a binary search for the specified element.
     *
     * @param element the element to be searched for
     * @return the index of the element if found, otherwise returns a negative value
     * indicating the insertion point
     */
    //in add method, if it is not there, what do we want to get back? --> where it should go
    //returns index where val should be in the array
    //when we call it in contains we get a positive index no matter what --> have to check that index and if thing we want is there - its there
    private int binarySearch(E element) {
        return binarySearchImp(elements, element, 0, (size-1));
        //the left and right define an interval - closed or half open
    }


    public int binarySearchImp(E[] arr, E val, int beg, int end) {
        int middle = (beg + end ) / 2;
        while (beg <= end) {
            int cmp = compareWithEither(val, arr[middle]);

            if (cmp == 0) {
                return middle;
            } else if (cmp < 0) {
                end = middle - 1;
                //return binarySearchImp(arr, val, middle + 1, end);
            } else {
                beg = middle + 1;
                // return binarySearchImp(arr, val, beg, middle);
            }
            middle = (beg + end ) / 2;
        }

        return  beg;
    }

    private int compareWithEither(E val, E arrvalue) {

        // If the user used the constructor that did not give us a comparator, use the built-in one
        if (comparator == null) {
            // Use compareTo that is built-in for elements implementing Comparable
//            if (val instanceof Comparable) {
                return ((Comparable<E>) val).compareTo(arrvalue);

        }else { //if comparator is not null, use the one that the user provided
            return comparator.compare(val, arrvalue);
        }
    }

    /**
     * @return The comparator used to order the elements in this set, or null if
     * this set uses the natural ordering of its elements (i.e., uses
     * Comparable).
     */
    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    /**
     * @return the first (lowest, smallest) element currently in this set
     * @throws NoSuchElementException if the set is empty
     */
    @Override
    public E first() throws NoSuchElementException {
        //if size is 0 throw no such element
        if (elements.length == 0) {
            throw new NoSuchElementException();
        }
        //if the element at this place is null, throw exception
        else if (elements[0] == null) {
            throw new NoSuchElementException();
        }

        //else return the object at this spot
        else return elements[0];
    }


    /**
     * @return the last (highest, largest) element currently in this set
     * @throws NoSuchElementException if the set is empty
     */
    @Override
    public E last() throws NoSuchElementException {

        //if size is 0 throw no such element
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        //if the element at this place is null, throw exception
        else if (elements[size-1] == null) {
            throw new NoSuchElementException();
        }
        //else return the object at this spot
        else {
            return elements[size-1];
        }

    }

  


    /**
     * Adds the specified element to this set if it is not already present and
     * not set to null.
     *
     * @param element element to be added to this set
     * @return true if this set did not already contain the specified element
     */
    @Override
    public boolean add(E element) {

        if(contains(element)|| element == null){
            return false;}
        // Find the insertion point using binary search
        int insertionPoint = binarySearch(element) ; // No  + 1

        // If the current size exceeds the capacity, increase the capacity of the set
        if (size == (elements.length-1)) {
            resizeArray();
        }
        if(size == 0){
            elements[0] = element;
        }
        else {
            // Shift elements to make space for the new element at the insertion point
            System.arraycopy(elements, insertionPoint, elements, insertionPoint + 1, size - insertionPoint);
            // Insert the new element at the calculated insertion point
            elements[insertionPoint] = element;
        }
        // Increment the size of the set
        size++;

        return true; //Return element was added
    }



    private void resizeArray() {
        capacity=capacity*2;
        // Double the size of the array
        E[] newArray = (E[]) Arrays.copyOf(elements,capacity);
        System.arraycopy(elements, 0, newArray, 0, size);
        elements = newArray;
        newArray=null;
    }


    /**
     * Adds all the elements in the specified collection to this set if they
     * are not already present and not set to null.
     *
     * @param elements collection containing elements to be added to this set
     * @return true if this set changed as a result of the call
     */
    @Override
    public boolean addAll(Collection<? extends E> elements) {
     int originalSize = size();
        for (E element : elements) {
                add(element);
                // If at least one element is added, setChanged is true
        }
       int finalSize = size();
        return finalSize >originalSize ;
    }


    /**
     * Removes all the elements from this set. The set will be empty after
     * this call returns.
     */
    @Override
    public void clear() {
        // Set all elements to null and reset the size to 0
        this.capacity=10;
        this.elements = (E[]) new Object[capacity];
//        this.comparator = null;
        this.size=0;
    }

    public E[] getSet(){
        return elements;
    }


    /**
     * @param element element whose presence in this set is to be tested
     * @return true if this set contains the specified element
     */
    @Override
    public boolean contains(E element) {
        // Check if the element is present in the set using binary search
        int index = binarySearch(element);
        return  index < size && compareWithEither(element, elements[index]) == 0 ;
    }


    /**
     * @param elements collection to be checked for containment in this set
     * @return true if this set contains all the elements of the specified
     * collection
     */
    @Override
    public boolean containsAll(Collection<? extends E> elements) {
        for (E element : elements) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }


    /**
     * @return true if this set contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size==0;
    }


    /**
     * Removes the specified element from this set if it is present.
     *
     * @param element element to be removed from this set, if present
     * @return true if this set contained the specified element
     */
    @Override
    public boolean remove(E element) {
        if(!contains(element)){
            return false;
        }
        int index = binarySearch(element);

        if (index >= 0) {
            // Element found, remove it
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
            elements[--size] = null; // Set the last element to null
            return true;
        }
        return false;
    }


    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection.
     *
     * @param elements collection containing elements to be removed from this set
     * @return true if this set changed as a result of the call
     */
    @Override
    public boolean removeAll(Collection<? extends E> elements) {
        int originalSize = size();

        for (E obj : elements) {
            remove(obj);
            }
        int finalSize = size();

        return finalSize<originalSize;
    }


    /**
     * @return the number of elements in this set
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * @return an array containing all the elements in this set, in sorted
     * (ascending) order.
     */
    @Override
    public E[] toArray() {
        E[] result = (E[]) new Object[size];
        System.arraycopy(elements, 0, result,0, size);
        return result;
    }




    /**
     * return an iterator over the elements in this set, where the elements are
     * returned in sorted (ascending) order
     */
    //this is a nested and inner class that is attached to a certain collection

        // Implement your inner class for the iterator
        private class SetIterator implements Iterator<E> {
            private int nextIndex;

            @Override
            public boolean hasNext() {
                return nextIndex < size;}

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return elements[nextIndex++];
            }

            @Override
            public void remove(){
                // check if user called next, throw exception
                if (nextIndex == 0 || nextIndex > (size+1)) {
                    throw new IllegalStateException("next method has not been called");
                }
                //get the element that was most recently returned by the next()
                E obj = get(nextIndex - 1);
                //remove element
                BinarySearchSet.this.remove(obj);
                //position is decremented so that the iterator is correctly positioned after the removal of the element
                nextIndex--;
            }


            }

    /**
     * @return an iterator over the elements in this set, where the elements are
     *         returned in sorted (ascending) order
     */
    @Override
    public Iterator<E> iterator() {
        return new SetIterator();
    }

    //Returns an element from the set (for testing)
    public E get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for BinarySearchSet");
        }
        return elements[i];
    }


    }
