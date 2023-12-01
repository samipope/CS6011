package assignment07;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChainingHashTest {

    @Test
    public void testingConstructor(){
        HashFunctor functor = new BadHashFunctor();
        ChainingHashTable testing = new ChainingHashTable(1,functor);
        ChainingHashTable testing2 = new ChainingHashTable(1,new MediocreHashFunctor());
        ChainingHashTable testing3 = new ChainingHashTable(1,new GoodHashFunctor());


    }

    @Test
    public void testingAdd(){
        HashFunctor functor = new BadHashFunctor();
        ChainingHashTable testing = new ChainingHashTable(1,functor);
        String item = new String();
        testing.add(item);
        ChainingHashTable table = new ChainingHashTable(10, new BadHashFunctor());
        assertTrue(table.add("test"));
        assertFalse(table.add("test")); // Adding duplicate should return false
        assertTrue(table.add("anotherTest"));
    }
    @Test
    public void testAddAll() {
        ChainingHashTable table = new ChainingHashTable(10, new BadHashFunctor());
        List<String> items = Arrays.asList("item1", "item2", "item3");
        assertTrue(table.addAll(items));
        assertFalse(table.addAll(items)); // Adding duplicates should return false
    }
    @Test
    public void testContains() {
        ChainingHashTable table = new ChainingHashTable(10, new BadHashFunctor());
        table.add("test");
        assertTrue(table.contains("test"));
        assertFalse(table.contains("notInTable"));
    }

    @Test
    public void testClear() {
        ChainingHashTable table = new ChainingHashTable(10, new BadHashFunctor());
        table.add("test");
        table.clear();
        assertTrue(table.isEmpty());
    }
    @Test
    public void testRemove() {
        ChainingHashTable table = new ChainingHashTable(10, new BadHashFunctor());
        table.add("test");
        assertTrue(table.remove("test"));
        assertFalse(table.remove("test")); // Removing again should return false
    }

    @Test
    public void testSize() {
        ChainingHashTable table = new ChainingHashTable(10, new BadHashFunctor());
        assertEquals(0, table.size());
        table.add("test");
        assertEquals(1, table.size());
    }

}
