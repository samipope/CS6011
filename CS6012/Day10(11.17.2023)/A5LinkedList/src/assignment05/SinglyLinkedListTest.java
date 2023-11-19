package assignment05;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListTest {


    @Test
    public void testInsertFirst(){
        SinglyLinkedList<Integer> test1 = new SinglyLinkedList<>();
        assertEquals(0,test1.size());
        test1.insertFirst(2);
        assertEquals(1,test1.size());

        Object[] test1array = test1.toArray();
        assertEquals(2,test1array[0]);

        test1.insertFirst(4);
        assertEquals(2, test1.size());
        test1array = test1.toArray();
        assertEquals(2, test1array.length);
        assertEquals(4,test1array[0]);
        assertEquals(2,test1array[1]);
    }

    @Test
    public void testInsert(){
        SinglyLinkedList<Integer> test1 = new SinglyLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> {test1.insert(1,1);});
        assertThrows(IndexOutOfBoundsException.class, () -> {test1.insert(-1,1);});
        assertEquals(0,test1.size());
        test1.insertFirst(2);
        assertEquals(1,test1.size());

        //expected order: 2,4,null
        test1.insert(1,4);
        assertEquals(4,test1.get(1));
        assertEquals(2, test1.get(0));
        assertEquals(2,test1.size());

        //expected order: 6,2,4,null
        test1.insert(0,6);
        assertEquals(4,test1.get(2));
        assertEquals(2,test1.get(1));
        assertEquals(6, test1.get(0));
        assertEquals(3,test1.size());

        test1.clear();
        test1.insert(0,1);
        assertEquals(1,test1.size());
        assertEquals(1,test1.get(0));

    }


    @Test
    public void testGetFirstElement(){
        SinglyLinkedList<Integer> test1 = new SinglyLinkedList<>();
        assertThrows(NoSuchElementException.class, () -> {test1.getFirst();});

        test1.insertFirst(2);
        assertEquals(2,test1.getFirst());


        test1.insertFirst(4);
        assertEquals(4,test1.getFirst());
    }

    @Test
    public void testGetElement(){
        SinglyLinkedList<Integer> test1 = new SinglyLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class,() -> {test1.get(1);});

        test1.insertFirst(2);
        assertEquals(2,test1.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> {test1.get(1);});
    }

    @Test
    public void testIsEmpty(){
        SinglyLinkedList<Integer> test1 = new SinglyLinkedList<>();
        assert(test1.isEmpty());

        test1.insertFirst(2);
        assertFalse(test1.isEmpty());

        test1.clear();
        assert(test1.isEmpty());
        assertThrows(IndexOutOfBoundsException.class, () -> {test1.get(0);});
        assertEquals(0,test1.size());
    }

    @Test
    public void testRemoveFirst(){
        SinglyLinkedList<Integer> test1 = new SinglyLinkedList<>();
        assertThrows(NoSuchElementException.class, () -> {test1.deleteFirst();});

        test1.insertFirst(2);
        test1.deleteFirst();
        assertEquals(0,test1.size());
        assert(test1.isEmpty());

        test1.insertFirst(2);
        test1.insertFirst(4);
        test1.insertFirst(6);
        Integer res = (Integer) test1.deleteFirst();
        assertEquals(6,res);
        //the list should now be 4,2,null
        assertEquals(2, test1.size());
        assertEquals(4,test1.get(0));
        assertEquals(2,test1.get(1));
    }

    @Test
    public void testRemoveIndex(){
        SinglyLinkedList<Integer> test1 = new SinglyLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> {test1.delete(0);});
        assertThrows(IndexOutOfBoundsException.class, () -> {test1.delete(1);});
        assertThrows(IndexOutOfBoundsException.class, () -> {test1.delete(-1);});

        test1.insertFirst(2);
        test1.insertFirst(4);
        test1.insertFirst(6);

        //current order is 6,4,2
        //if I delete element 1 it should be 6,2
        Integer res = test1.delete(1);
        assertEquals(4,res);
        assertEquals(6,test1.get(0));
        assertEquals(2, test1.get(1));
        assertEquals(2,test1.size());

        //try to remove the first element, that should leave us with only 2 in the set
        res = test1.delete(0);
        assertEquals(6,res);
        assertEquals(1,test1.size());
        assertEquals(2,test1.get(0));

        //add things back in, and then try to remove the last element
        test1.insertFirst(4);
        test1.insertFirst(6);
        //should be left with 6,4
        res = test1.delete(2);
        assertEquals(2,res);
        assertEquals(2,test1.size());
        assertEquals(6,test1.get(0));
        assertEquals(4,test1.get(1));
    }

    @Test
    public void testIndexof(){
        SinglyLinkedList<Integer> test1 = new SinglyLinkedList<>();
        test1.insertFirst(2);
        test1.insertFirst(4);
        test1.insertFirst(6);

        assertEquals(test1.indexOf(2),2);
        assertEquals(test1.indexOf(4),1);
        assertEquals(test1.indexOf(6),0);
        //returns negative 1 when not found
        assertEquals(test1.indexOf(8),-1);
        assertEquals(test1.indexOf(10),-1);
    }

    @Test
    public void testIterator(){
        SinglyLinkedList<Integer> test1 = new SinglyLinkedList<>();
        test1.insertFirst(2);
        test1.insertFirst(4);
        test1.insertFirst(6);

        //does it run a for each loop
        Integer sum = 0;
        for(Object element: test1){
            sum += (Integer)element;
        }
        assertEquals(12,sum);

        Iterator i = test1.iterator();

        //check that the function throws an exception when we haven't called i.next
        while(i.hasNext()) {
            assertThrows(IllegalStateException.class, i::remove);
            i.next();
            i.remove();
        }

        assertEquals(0,test1.size());

    }
}