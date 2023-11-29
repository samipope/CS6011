package assignment06;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
public class BinarySearchTreeTest {

    @Test
    public void testAddAndContains() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(3);
        bst.add(1);
        bst.add(4);

        assertTrue(bst.contains(3));
        assertTrue(bst.contains(1));
        assertTrue(bst.contains(4));
        assertFalse(bst.contains(2));
    }

    @Test
    public void testAddAll() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addAll(Arrays.asList(3, 1, 4));

        assertTrue(bst.contains(3));
        assertTrue(bst.contains(1));
        assertTrue(bst.contains(4));
    }

    @Test
    public void testClearAndIsEmpty() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(3);

        assertFalse(bst.isEmpty());
        bst.clear();
        assertTrue(bst.isEmpty());
    }

    @Test
    public void testFirstAndLast() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(3);
        bst.add(1);
        bst.add(4);
        assertEquals(1, bst.first());
        assertEquals(4, bst.last());
    }

    @Test
    public void testRemove() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addAll(Arrays.asList(3, 1, 4));
        assertTrue(bst.remove(1));
        assertFalse(bst.contains(1));
    }

    @Test
    public void testRemoveAll() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addAll(Arrays.asList(3, 1, 4, 2));
        bst.removeAll(Arrays.asList(1, 4));

        assertFalse(bst.contains(1));
        assertFalse(bst.contains(4));
        assertTrue(bst.contains(2));
        assertTrue(bst.contains(3));
    }

    @Test
    public void testSize() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addAll(Arrays.asList(3, 1, 4));

        assertEquals(3, bst.size());
    }

    @Test
    public void testToArrayList() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.addAll(Arrays.asList(3, 1, 4, 2));
        assertEquals(Arrays.asList(1, 2, 3, 4), bst.toArrayList());
    }



}
