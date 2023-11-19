package assignment05;

import java.util.NoSuchElementException;

public class LinkedListStack <E> implements Stack<E>{

    public SinglyLinkedList<E> singlylinked;


    /**
     * include an empty constructor.
     * includes intializing the member variables
     */
    public LinkedListStack(){
      singlylinked=new SinglyLinkedList<E>();
  }

    /**
     * Removes all of the elements from the stack.
     */
    @Override
    public void clear() {
        singlylinked.clear();
    }
    /**
     * @return true if the stack contains no elements; false, otherwise.
     */
    @Override
    public boolean isEmpty() {
     return singlylinked.isEmpty();
    }

    /**
     * Returns, but does not remove, the element at the top of the stack.
     *
     * @return the element at the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public E peek() throws NoSuchElementException {
        if(singlylinked.isEmpty()){
            throw new NoSuchElementException();
        }
       return singlylinked.getFirst();
    }

    /**
     * Returns and removes the item at the top of the stack.
     *
     * @return the element at the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public E pop() throws NoSuchElementException {
        if(singlylinked.isEmpty()){
            throw new NoSuchElementException();
        }
        //save the top of the stack value
        E temp = singlylinked.getFirst();
        //delete value at top of stack
        singlylinked.deleteFirst();
        //return saved value
        return temp;
    }

    /**
     * Adds a given element to the stack, putting it at the top of the stack.
     *
     * @param element - the element to be added
     */
    @Override
    public void push(E element) {
        singlylinked.insertFirst(element);

    }
    /**
     * @return the number of elements in the stack
     */
    @Override
    public int size() {
        return singlylinked.size();
    }
}
