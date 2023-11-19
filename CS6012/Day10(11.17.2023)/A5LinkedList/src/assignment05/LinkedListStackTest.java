package assignment05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListStackTest {
    private LinkedListStack<Integer> stack;

    @BeforeEach
    public void setUp() {
        stack = new LinkedListStack<>();
    }


    @Test
    void clear() {
        stack.push(10);
        stack.push(20);
        stack.clear();
        assertTrue(stack.isEmpty());

    }

    @Test
    void isEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(10);
        assertFalse(stack.isEmpty());

    }

    @Test
    void peek() {
        //the following line was to test that it would throw an excpetion on an emtpy stack
       // stack.peek();
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.peek());
        assertEquals(2, stack.size());

        LinkedListStack<Integer> test1 = new LinkedListStack<>();
        assertThrows(NoSuchElementException.class, test1::peek);
    }

    @Test
    void pop() {
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.pop());
        assertEquals(1, stack.size());

        LinkedListStack<Integer> test1 = new LinkedListStack<>();
        assertThrows(NoSuchElementException.class, test1::pop);
    }

    @Test
    void push() {
        stack.push(10);
        stack.push(20);
        assertEquals(2, stack.size());
    }

    @Test
    void size() {
        stack.push(10);
        stack.push(20);
        assertEquals(2, stack.size());
    }

}