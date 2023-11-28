package lab06;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ArrayListPQ<T extends Comparable<? super T>> implements PriorityQueue<T>{
    private ArrayList<T> data;


    public ArrayListPQ(){
        data = new ArrayList<>();
    }

    public ArrayListPQ(ArrayList<T> elements){
        data = new ArrayList<>(elements);
        heapify();
    }


    private void heapify(){

        for(int i = data.size()/2; i >= 0; i--){
            percolateDown(i);
        }
    }

    private void percolateUp(int index){
        while(index != 0 && data.get(index).compareTo(data.get(0)) < 0){
            //index of the parent is (index - 1)/2
            T temp = data.get(index);
            data.set( index, data.get((index - 1)/2));
            data.set( (index - 1)/2 ,temp);
        }
    }


    /**
     * swap an element
     * @param index
     *      the index of the parent
     */
    private void percolateDown(int index){

        while( canSwapLeft(index) || canSwapRight(index) ){
            if(canSwapLeft(index) && canSwapRight(index)){
                if(data.get(index*2 + 1).compareTo(data.get(index*2 +2)) < 0){
                    //if left is smaller swap with left
                    T temp = data.get(index);
                    data.set( index, data.get(index*2 + 1));
                    data.set( index*2 + 1 ,temp);

                    index = index*2 +1;
                }
                else{
                    T temp = data.get(index);
                    data.set( index, data.get(index*2 + 2));
                    data.set( index*2 + 2 ,temp);

                    index = index*2 +2;
                }
            }else if(canSwapLeft(index)){
                T temp = data.get(index);
                data.set( index, data.get(index*2 + 1));
                data.set( index*2 + 1 ,temp);

                index = index*2 +1;
            }
            else{
                T temp = data.get(index);
                data.set( index, data.get(index*2 + 2));
                data.set( index*2 + 2 ,temp);

                index = index*2 +2;
            }

        }


    }

    /**
     * determines if a parent can be swapped with its left child
     * @param index
     *      the index of the parent
     * @return
     *      returns if the value at the index has a left child that is less than its parent
     */
    private boolean canSwapLeft(int index){
        return (index*2 + 1 < data.size() && data.get(index*2 + 1).compareTo(data.get(index)) < 0);
    }

    /**
     * determines if a parent can be swapped with its right child
     * @param index
     *      the index of the parent
     * @return
     *      returns if the value at the index has a right child that is less than its parent
     */
    private boolean canSwapRight(int index){
        return (index*2 + 2 < data.size() && data.get(index*2 + 2).compareTo(data.get(index)) < 0);
    }


    /**
     * Adds an element to the queue
     *
     * @param element - the item tha twill be added
     * @throws NullPointerException if the item is null
     */
    @Override
    public void add(T element) {
        data.add(element);
        percolateUp(data.size() - 1);
    }

    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Ensures that this set does not contain the specified item.
     *
     * @return true if this set changed as a result of this method call (that is, if
     * the input item was actually removed); otherwise, returns false
     * @throws NullPointerException if the item is null
     */
    @Override
    public T removeMin() {
        //swap the root to the end
        T temp = data.get(0);
        data.set( 0, data.get(data.size() - 1));
        data.set( data.size() - 1,temp);

        //remove the end
        data.remove(data.size() - 1);

        //put the root in its proper place
        percolateDown(0);

        return temp;
    }
}