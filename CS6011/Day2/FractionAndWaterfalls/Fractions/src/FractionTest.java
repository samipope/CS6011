import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class FractionTest {
    //initializing a bunch of Fractions to test inside my methods
    Fraction f1 = new Fraction(1, 3);
    Fraction f2 = new Fraction(1, 4);
    Fraction f3 = new Fraction(-1, 4);
    Fraction f4 = new Fraction(1, 2);
    Fraction f5 = new Fraction(2, 8);
    Fraction f6 = new Fraction(7, 12);
    Fraction f9 = new Fraction(1, 2);
    Fraction f10 = new Fraction(1, 3);

   // i commented out my other tests for simplicity
    @Test
    public void testompareTo(){
        Assertions.assertEquals(f2.compareTo(f4),-1);
        Assertions.assertEquals(f1.compareTo(f2),1);
    }

    @Test
    public void testSort(){
        ArrayList listOfFracs = new ArrayList<Fraction>(Arrays.asList(f1,f2,f4));
        Collections.sort(listOfFracs);
        System.out.println(listOfFracs);
    }



    //below is the test that uses try and catch to make sure my exception is working
//    @Test
//    public void testDenominatorZeroException() {
//        // This should throw an IllegalArgumentException
//        try {
//            Fraction fraction = new Fraction(3, 0);
//        } catch (IndexOutOfBoundsException e) {
//            System.out.println("Index out of bounds exception");
//        }
//    }
    //the test above prints "denominator cannot be 0" so I know my code is working




//    @org.junit.jupiter.api.Test
//    void plus() {
//        //checking the function with two positive numbers
//        assertEquals(f1.plus(f2), f6);
//        assertNotEquals(f1.plus(f2), f3);
//        assertNotEquals(f1.plus(f2), f4);
//        assertNotEquals(f1.plus(f2), f5);
//        //checking the method can add a negative and positive fraction
//        assertEquals(f2.plus(f3), new Fraction(0, 1));
//        //checking the fraction will reduce and add correctly and will add up to another fraction that has been initialized
//        assertEquals(f5.plus(f2), f4);
//    }
//
//    @org.junit.jupiter.api.Test
//    void minus() {
//        //testing the result is negative
//        assertNotEquals(f2.minus(f1), f3);
//        assertEquals(f2.minus(f1), new Fraction(-1, 12));
//        //testing when the result is positive
//        assertNotEquals(f4.minus(f1), f4);
//        assertEquals(f4.minus(f1), new Fraction(1, 6));
//        //testing when you subtract a negative number
//        assertNotEquals(f2.minus(f3), f2);
//        assertEquals(f2.minus(f3), f4);
//    }
//
//    @org.junit.jupiter.api.Test
//    void times() {
//        assertNotEquals(f2.times(f3), f2);
//        assertNotEquals(f4.times(f1), f4);
//        assertNotEquals(f2.times(f1), f3);
//        assertEquals(f1.times(f2), new Fraction(1, 12));
//        assertEquals(f3.times(f4), new Fraction(-1, 8));
//        assertEquals(f5.times(f6), new Fraction(7, 48));
//    }
//
//    @org.junit.jupiter.api.Test
//    void dividedBy() {
//       Assertions.assertNotEquals(f2.dividedBy(f3), f2);
//        Assertions.assertNotEquals(f4.dividedBy(f1), f4);
//        Assertions.assertNotEquals(f2.dividedBy(f1), f3);
//        Assertions.assertEquals(f1.dividedBy(f2), new Fraction(4, 3));
//        Assertions.assertEquals(f3.dividedBy(f4), new Fraction(-1, 2));
//        Assertions.assertEquals(f5.dividedBy(f6), new Fraction(3, 7));
//    }

//    @org.junit.jupiter.api.Test
//    void reciporacal() {
//        assertNotEquals(f2.reciprocal(), f2);
//        assertNotEquals(f4.reciprocal(), f4);
//        assertNotEquals(f2.reciprocal(), f3);
//        assertEquals(f3.reciprocal(), new Fraction(4, -1));
//        assertEquals(f2.reciprocal(), new Fraction(4, 1));
//        assertEquals(f4.reciprocal(), new Fraction(2, 1));
//    }
//
//    @org.junit.jupiter.api.Test
//    void testToString() {
//            assertEquals(f1.toString(), "1/3");
//            assertEquals(f2.toString(), "1/4");
//            assertEquals(f3.toString(), "-1/4");
//        }
//
//    @org.junit.jupiter.api.Test
//    void toDouble() {
//        assertEquals(f2.toDouble(), 0.25, 0.0001);
//        assertEquals(f4.toDouble(), 0.5, 0.0001);
//        assertEquals(f5.toDouble(), 0.25, 0.0001);
//    }
//
//    @Test
//    public void runAllTests(){
//        plus();
//        minus();
//        times();
//        dividedBy();
//        reciporacal();
//        toString();
//        toDouble();
//    }
}