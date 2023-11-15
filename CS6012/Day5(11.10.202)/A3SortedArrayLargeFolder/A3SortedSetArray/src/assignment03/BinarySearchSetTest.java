package assignment03;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class BinarySearchSetTest {


   BinarySearchSet<Integer> emptySet = new BinarySearchSet<Integer>();


    @Test
    void first() {
      //the following line does throw a NoSuchElementException
      //emptySet.first();
     BinarySearchSet<Integer> set = new BinarySearchSet<>();
     assertTrue(set.add(5));
     assertTrue(set.add(2));
     assertEquals(set.first(),2);
     set.add(1);
     assertEquals(set.first(),1);

    }

    @Test
    void last() {
     BinarySearchSet<Integer> set = new BinarySearchSet<>();
     assertTrue(set.add(5));
     assertTrue(set.add(2));
     assertEquals(set.last(),5);
     set.add(9);
     assertEquals(set.last(),9);

    }

    @Test
    void add() {
     BinarySearchSet<Integer> set = new BinarySearchSet<>();
     assertTrue(set.add(5));
     assertTrue(set.add(2));

     BinarySearchSet<String> Stringset = new BinarySearchSet<>();
     assertTrue(Stringset.add("happy"));
     assertTrue(Stringset.add("sad"));
     assertFalse(Stringset.add("happy"));

    }

    @org.junit.jupiter.api.Test
    void clear() {
     BinarySearchSet<Double> doubleBinarySearchSet = new BinarySearchSet<>();
     doubleBinarySearchSet.add(2.80);
     doubleBinarySearchSet.add(3.90);
     doubleBinarySearchSet.add(4.209);
     assertEquals(doubleBinarySearchSet.size(),3);
     doubleBinarySearchSet.clear();
     assertEquals(doubleBinarySearchSet.size(),0);


    }

    @org.junit.jupiter.api.Test
    void contains() {
     BinarySearchSet<Integer> set = new BinarySearchSet<>();
     assertTrue(set.add(5));
     assertTrue(set.add(2));
     assertFalse(set.contains(3));
     assertFalse(set.contains(4));
     assertTrue(set.contains(5));

    }


    @org.junit.jupiter.api.Test
    void isEmpty() {
     assertTrue(emptySet.isEmpty());
     BinarySearchSet<Integer> set = new BinarySearchSet<>();
     assertTrue(set.add(5));
     assertFalse(set.isEmpty());

    }

    @org.junit.jupiter.api.Test
    void remove() {
     BinarySearchSet<Integer> Intset = new BinarySearchSet<>();
     assertTrue(Intset.add(5));
     assertFalse(Intset.isEmpty());
     Intset.remove(5);
     assertTrue(Intset.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void removeAll() {
     BinarySearchSet<String> Stringset = new BinarySearchSet<>();
     assertTrue(Stringset.add("happy"));
     assertTrue(Stringset.add("sad"));
     assertTrue(Stringset.add("joy"));
     assertTrue(Stringset.add("crazy"));
     assertTrue(Stringset.add("mad"));
     assertEquals(Stringset.size(),5);
     for(String s : Stringset){
      System.out.println(s);
     }
     List <String> elementstoRemove = Arrays.asList("joy","crazy","mad");
     Stringset.removeAll(elementstoRemove);
     for(String s : Stringset){
      System.out.println(s);
     }
     assertEquals(Stringset.size(),2);
    }

    @org.junit.jupiter.api.Test
    void size() {
     BinarySearchSet<Integer> set = new BinarySearchSet<>();
     set.add(5);
     set.add(6);
     assertEquals(set.size(),2);
     set.add(4);
     set.add(9);
     assertEquals(set.size(),4);
    }

    @org.junit.jupiter.api.Test
    void toArrayTest() {
     BinarySearchSet<Object> set = new BinarySearchSet<>();
     Object[] ans = new Object[]{4,5,6,9};
     set.add(5);
     set.add(6);
     set.add(4);
     set.add(9);
     assertArrayEquals(set.toArray(),ans);


     BinarySearchSet<String> Stringset = new BinarySearchSet<>();
     assertTrue(Stringset.add("happy"));
     assertTrue(Stringset.add("sad"));
     assertTrue(Stringset.add("joy"));
     Object[] ansString = new String[]{"happy","sad","joy"};


    }

}
