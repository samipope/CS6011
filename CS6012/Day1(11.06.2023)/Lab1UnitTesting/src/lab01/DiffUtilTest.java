package lab01;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiffUtilTest {

    private int[] arr1, arr2, arr3, arr4, arr5;

    @BeforeEach
    protected void setUp() throws Exception {
        arr1 = new int[0];
        arr2 = new int[] { 3, 3, 3 };
        arr3 = new int[] { 52, 4, -8, 0, -17 };
        arr4 = new int[]{0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4};
        arr5 = new int[]{2,3};
    }

    @AfterEach
    protected void tearDown() {
        arr1 = null;
        arr2=null;
        arr3=null;
        arr4=null;
        arr5=null;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // happens once per class, after all test methods have been run.
    }


    @Test
    public void emptyArray() throws Exception {
        setUp();
        assertEquals(-1, DiffUtil.findSmallestDiff(arr1));
        tearDown();

    }

    @Test
    public void allArrayElementsEqual() throws Exception {
        //setUp();
        assertEquals(0, DiffUtil.findSmallestDiff(arr2));
       // tearDown();
    }

    @Test
    public void smallRandomArrayElements() throws Exception {
       // setUp();
        assertEquals(4, DiffUtil.findSmallestDiff(arr3));
       // tearDown();
    }

    @Test
    public void largeArray() throws Exception{
        //setUp();
        assertEquals(0,DiffUtil.findSmallestDiff(arr4));
        //tearDown();
    }

    @Test
    public void arrayWithTwoElements() throws Exception{
        //setUp();
        assertEquals(1,DiffUtil.findSmallestDiff(arr5));
       // tearDown();
    }

}