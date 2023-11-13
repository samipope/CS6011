package assignment03;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BinarySearchSetTest {


   BinarySearchSet<Integer> emptySet = new BinarySearchSet<Integer>();

    @BeforeEach
    public void setup(){
     var emptySet = new BinarySearchSet<Integer>();


    }

    @AfterEach
    public void takeDown(){
    }

    @Test
    void comparator() {
    }

    @Test
    void first() {
      //the following line does throw a NoSuchElementException
      //emptySet.first();
    }

    @Test
    void last() {
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

    @Test
    void addAll() {
    }

    @org.junit.jupiter.api.Test
    void clear() {
    }

    @org.junit.jupiter.api.Test
    void contains() {
    }

    @org.junit.jupiter.api.Test
    void containsAll() {
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
    }

    @org.junit.jupiter.api.Test
    void remove() {
    }

    @org.junit.jupiter.api.Test
    void removeAll() {
    }

    @org.junit.jupiter.api.Test
    void size() {
    }

    @org.junit.jupiter.api.Test
    void toArray() {
    }

    @org.junit.jupiter.api.Test
    void iterator() {
    }
}