package assignment03;

import java.util.*;
import java.lang.reflect.Array;

//TODO below:
    //create tests
    //fix functions
    //PDF document on how to

//FIXME
//first and last tests (0/12)
//contains tests (0/6)
//toArray tests (0/3)
//basic tests on empty set with Comparable (0/6)
//test add (0/3)
//comparator tests (0/6)
//test addAll, ContainsAll (0/6)



public class BinarySearchSet<E> implements SortedSet<E>, Iterable<E> {

    private int capacity;
    private E[] elements;
    private int size;
    private final Comparator<? super E> comparator;
    private int size_;


    // Constructors
    public BinarySearchSet() {
        this.capacity=10;
        this.elements = (E[]) new Object[capacity];
        this.comparator = null;
        this.size=0;
    }

    public BinarySearchSet(Comparator<E> comparator) {
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
//    private int binarySearch(E element) {
//        return binarySearchImp(elements, element, 0, (size-1));
//        //the left and right define an interval - closed or half open
//    }
//
//
//    public int binarySearchImp(E[] arr, E val, int beg, int end) {
//        if ((end - beg) <= 0) {
//            return -1;
//        }
//
//        while (beg<=end) {
//            //how do you know when to use compareTo or comparator ??
//            //if they called the constructor where they gave us the comparator, if not then we should just use comparator
//            //if comparator value is null, use compareTo, if not use the comparator they gave us
//            //can use our own comparator that we make that can use "natural order" that
//            int middle = (beg + end) / 2;
//           int cmp = compareWithEither(val, arr[middle]);
//
//            if (cmp == 0) {
//                return middle;
//            } else if (cmp > 0) {
//                end = middle-1;
//                //return binarySearchImp(arr, val, middle + 1, end);
//            } else {
//                beg= middle+1;
//               // return binarySearchImp(arr, val, beg, middle);
//            }
//       } return -(end+1);
//    }
//
//    private int compareWithEither(E val, E arrvalue) {
//        if(val ==null || arrvalue ==null){return 0;}
//        // If the user used the constructor that did not give us a comparator, use the built-in one
//        if (comparator == null) {
//            // Use compareTo that is built-in for elements implementing Comparable
//            if (val instanceof Comparable) {
//                return ((Comparable<E>) val).compareTo(arrvalue);
//            } else {
//                // Handle the case where val is not Comparable (you might want to throw an exception or handle it in some way)
//                throw new IllegalArgumentException("Elements must be Comparable if no comparator is provided.");
//            }
//        }else { //if comparator is not null, use the one that the user provided
//            return comparator.compare(val, arrvalue);}
//    }

    private int binarySearch(E element) {

        // Initialize low and high indices for the binary search
        int left = 0;
        int right = size_ - 1;

        if ((left - right) <= 0) {
                     return -1;}

        // Perform binary search until low index is less than or equal to high index
        while (left <= right) {
            // Calculate the middle index
            //int mid = right+left /2;
            int mid = left+ (right-left) / 2;
            int cmp;
            // Compare elements using either the provided comparator or natural ordering
            if (comparator != null) {
                // Use the provided comparator for comparison
                cmp = comparator.compare(elements[mid], element);
            } else {
                try {
                    //Use natural ordering for comparison
                    Comparable<? super E> midVal = (Comparable<? super E>) elements[mid];
                    cmp = midVal.compareTo(element);
                } catch (ClassCastException e) {
                    //If the natural ordering doesn't work
                    throw new ClassCastException(e.getMessage());
                }
            }

            // Adjust low and high indices based on the comparison result
            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                return mid; // Element found, returns a positive if present
            }
        }

        return -left - 1; // Element not found, return the insertion point, add a +1 to account for first element creation
    }


    /**
     * @return The comparator used to order the elements in this set, or null if
     * this set uses the natural ordering of its elements (i.e., uses
     * Comparable).
     */
    public Comparator comparator(E val, E arrvalue) {
        if(comparator==null){
            Comparator comparator1 = null;
            return comparator1;
        }
        return comparator;
    }


    @Override
    public Comparator comparator() {
        return null;
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
        else if (elements[0] == null) {
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
        //TODO ADD IN CHECK FOR IF THE ARRAY OF ELEMENTS IS NULL (make a new one)
        if(elements==null){

        }

        //add in boolean variable for added that is returned?

        //don't allow null items to be added
        if (element == null) {
            throw new IllegalArgumentException("Can't add null element");
        }
        if(contains(element)){
            return false;}
        // Find the insertion point using binary search
        int insertionPoint = binarySearch(element);
        // If the element is already present in the set, it will return positive, return false
        if (insertionPoint >= 0) {
            return false;
        }
        // If the insertion point is negative, this is a flag for the index, convert it to a positive
        insertionPoint = -(insertionPoint + 1);

        // If the current size exceeds the capacity, increase the capacity of the set
        if (size == (elements.length-1)) {
            resizeArray();
        }

        // Shift elements to make space for the new element at the insertion point
        System.arraycopy(elements, insertionPoint, elements, insertionPoint + 1, size - insertionPoint);
        // Increment the size of the set
        size_++;
        // Insert the new element at the calculated insertion point
        elements[insertionPoint]=element;

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
        //fixme add in
        // int original size = this.size()
        //add in iterator object
        //while (iterator.hasNext(){E element = elementsIterator.next();
        //if(!this.contains(element)
        //this.add(element);
        //}

        //int finalsize
        //if finalsize>originalsize, return true

        boolean setChanged = false;

        for (E element : elements) {
            if (add(element)) {
                // If at least one element is added, setChanged is true
                setChanged = true;
            }
        }
        return setChanged;
    }


    /**
     * Removes all the elements from this set. The set will be empty after
     * this call returns.
     */
    @Override
    public void clear() {
        // Set all elements to null and reset the size to 0
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
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
        return index >= 0;
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
        int index = binarySearch(element);

        if (index >= 0) {
            // Element found, remove it
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
            elements[--size] = null; // Set the last element to null
            size--;
            return true;
        } else {
            // Element not found
            return false;
        }
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
        boolean setChanged = false;

        for (E element : elements) {
            if (remove(element)) {
                // If at least one element is removed, setChanged is true
                setChanged = true;
            }
        }

        return setChanged;
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
        System.arraycopy(elements, 0, result, 0, size);
        return result;
    }




    /**
     * return an iterator over the elements in this set, where the elements are
     * returned in sorted (ascending) order
     */
    //this is a nested and inner class that is attached to a certain collection


        // Implement your inner class for the iterator
        private class SetIterator implements Iterator<E> {
            private int nextIndex = -1;

            @Override
            public boolean hasNext() {return (nextIndex+1) < size;}

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                nextIndex++;
                return elements[nextIndex++];
            }

            //FIXME
            @Override
            public void remove(){
                if(nextIndex<0 ||nextIndex>size){
                    throw new IllegalArgumentException("Remove function not able to find index");
                }
                //tODO write a getter function
                //E element = get(nextIndex);
               // BinarySearchSet.this.remove(element);

                size--;


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


    }
