package assignment04;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.*;

//Elisabeth Frischknecht and Sami Pope

class SortUtilTest {

//    ArrayList<Integer> sortedTest1;
//    ArrayList<Integer> sortedTest2;
//    ArrayList<Integer>
//
//
//        ArrayList<Integer> sortedTest1= SortUtil.generateBestCase(4);
//        ArrayList<Integer> sortedTest2= SortUtil.generateBestCase(7);
//    }


    @Test
    void testgenerateBestCase(){
        ArrayList<Integer> sortedTest1= SortUtil.generateBestCase(4);
        ArrayList<Integer> sortedTest2= SortUtil.generateBestCase(7);
        assertEquals(sortedTest2.size(),7);
        assertEquals(sortedTest1.size(),4);

        for(int i = 0; i < 4; i++){
            assertEquals(sortedTest1.get(i),i+1);
        }
    }

    @Test
    void testgenerateWorstCase(){
        ArrayList<Integer> sortedTest1= SortUtil.generateWorstCase(4);
        ArrayList<Integer> sortedTest2= SortUtil.generateWorstCase(7);
        assertEquals(sortedTest2.size(),7);
        assertEquals(sortedTest1.size(),4);

        for(int i = 0; i < 4; i++){
            assertEquals(sortedTest1.get(i),4-i);
        }

    }

    @Test
    void testAverageCaseGenerator(){
        ArrayList<Integer> randomTest1 = SortUtil.generateAverageCase(10);
        ArrayList<Integer> randomTest2 = SortUtil.generateAverageCase(10);
        assertNotEquals(randomTest2,randomTest1);

    }

    @Test
    void testPartition (){
        ArrayList<Integer> test1 = new ArrayList<>(Arrays.asList(2,3,1,4,5));
        int pivot = SortUtil.partition(test1,0,4,1, Comparator.naturalOrder());
        //assertEquals(pivot,2);
        //System.out.println(test1);

        ArrayList<Integer> test2 = new ArrayList<>(Arrays.asList(1,5,2,3,4));
        pivot = SortUtil.partition(test2,0,4,3, Comparator.naturalOrder());
        //System.out.println(test2);
        assertEquals(pivot,2);

        ArrayList<Integer> test3 = new ArrayList<>(Arrays.asList(2,3));
        pivot = SortUtil.partition(test3,0,1,0,Comparator.naturalOrder());
        assertEquals(test3.get(0),2);
        assertEquals(test3.get(1),3);
        //System.out.println(test3);
        assertEquals(pivot, 0);

        ArrayList<Integer> test4 = new ArrayList<>(Arrays.asList(2,3));
        pivot = SortUtil.partition(test4,0,1,1,Comparator.naturalOrder());
        assertEquals(test4.get(0),2);
        assertEquals(test4.get(1),3);
        //System.out.println(test4);
        assertEquals(pivot, 1);

        ArrayList<Integer> test5 = new ArrayList<>(Arrays.asList(3,2));
        pivot = SortUtil.partition(test5,0,1,0,Comparator.naturalOrder());
        assertEquals(test5.get(0),2);
        assertEquals(test5.get(1),3);
        //System.out.println(test4);
        assertEquals(pivot, 1);

        ArrayList<Integer> test6 = new ArrayList<>(Arrays.asList(3,2));
        pivot = SortUtil.partition(test6,0,1,1,Comparator.naturalOrder());
        assertEquals(test6.get(0),2);
        assertEquals(test6.get(1),3);
        //System.out.println(test4);
        assertEquals(pivot, 0);

        ArrayList<Integer> test7 = new ArrayList<>(List.of(4));
        pivot = SortUtil.partition(test7,0,0,0,Comparator.naturalOrder());
        assertEquals(test7.get(0),4);
        assertEquals(pivot, 0);

    }

    @Test
    void testQuickSort(){
        ArrayList<Integer> test1 = new ArrayList<>(Arrays.asList(2,3,1,4,5));
        SortUtil.quicksort(test1, Comparator.naturalOrder());
        ArrayList<Integer> sorted5 = SortUtil.generateBestCase(5);
        assertEquals(test1,sorted5);

        ArrayList<Integer> test2 = SortUtil.generateAverageCase(10);
        SortUtil.quicksort(test2, Comparator.naturalOrder());
        ArrayList<Integer> sorted10 = SortUtil.generateBestCase(10);
        //assertEquals(test2,sorted10);

        ArrayList<Integer> test3 = SortUtil.generateBestCase(10);
        SortUtil.quicksort(test3, Comparator.naturalOrder());
        assertEquals(test3,sorted10);

        ArrayList<Integer> test4 = SortUtil.generateWorstCase(10);
        SortUtil.quicksort(test4,Comparator.naturalOrder());
        assertEquals(test4,sorted10);

        ArrayList<Integer> test5 = new ArrayList<>(List.of(1));
        SortUtil.quicksort(test5,Comparator.naturalOrder());
        assertEquals(test5.get(0),1);
        assertEquals(test5.size(),1);

        ArrayList<Integer> test6 = new ArrayList<>(Arrays.asList(2,3));
        SortUtil.quicksort(test6,Comparator.naturalOrder());
        assertEquals(test6.get(0),2);
        assertEquals(test6.get(1),3);

        ArrayList<Integer> test7 = new ArrayList<>(Arrays.asList(3,2));
        SortUtil.quicksort(test7,Comparator.naturalOrder());
        assertEquals(test7.get(0),2);
        assertEquals(test7.get(1),3);

        ArrayList<Integer> test8 = SortUtil.generateAverageCase(50);
        SortUtil.quicksort(test8,Comparator.naturalOrder());
        ArrayList<Integer> test8Solved = SortUtil.generateBestCase(50);
        assertEquals(test8,test8Solved);

    }

    @Test
    void testInsertionSort(){
        ArrayList<Integer> test1 = new ArrayList<>(Arrays.asList(2,3,1,4,5));
        SortUtil.insertionSort(test1, Comparator.naturalOrder(),0,4);
        ArrayList<Integer> sorted5 = SortUtil.generateBestCase(5);
        assertEquals(test1,sorted5);

        ArrayList<Integer> test2 = new ArrayList<>(List.of(1));
        SortUtil.insertionSort(test2, Comparator.naturalOrder(),0,0);
        assertEquals(test2.size(),1);
        assertEquals(test2.get(0),1);

        ArrayList<Integer> test3 = new ArrayList<>(Arrays.asList(3,2));
        SortUtil.insertionSort(test3,Comparator.naturalOrder(),0,1);
        assertEquals(test3.get(0),2);
        assertEquals(test3.get(1),3);

        ArrayList<Integer> test4 = new ArrayList<>(Arrays.asList(3,2,1));
        SortUtil.insertionSort(test4,Comparator.naturalOrder(),0,2);
        assertEquals(1,test4.get(0));
        assertEquals(2,test4.get(1));
        assertEquals(3,test4.get(2));

        ArrayList<Integer> test5 = new ArrayList<>(Arrays.asList(5,4,3,2,1));
        SortUtil.insertionSort(test5,Comparator.naturalOrder(),1,3);
       // System.out.println(test5);
        assertEquals(5,test5.get(0));
        assertEquals(2,test5.get(1));
        assertEquals(3,test5.get(2));
        assertEquals(4,test5.get(3));
        assertEquals(1,test5.get(4));



    }

  @Test
    void testGetPivotFromMedian(){
      ArrayList<Integer> test1 = new ArrayList<>(Arrays.asList(2,3,1));
      int median = SortUtil.getPivotFromApproxMedian(test1,0,2,Comparator.naturalOrder());
      //note that this returns the original index of the median in our test1 array
      assertEquals(median,0);

      ArrayList<Integer> test2 = new ArrayList<>(Arrays.asList(1,2,3));
      int median2 = SortUtil.getPivotFromApproxMedian(test2,0,2,Comparator.naturalOrder());
      assertEquals(median2,1);

      //testing on larger arrays since that is what we will be passing to find our pointer
      ArrayList<Integer> test3 = new ArrayList<>(Arrays.asList(1,3,4,5,2));
      int median3 = SortUtil.getPivotFromApproxMedian(test3,0,4,Comparator.naturalOrder());
      assertEquals(median3,4);

      ArrayList<Integer> test4 = new ArrayList<>(Arrays.asList(1,3));
      int median4 = SortUtil.getPivotFromApproxMedian(test4,0,1,Comparator.naturalOrder());
      assertEquals(median4,0);

      ArrayList<Integer> test5 = new ArrayList<>(Arrays.asList(1));
      int median5 = SortUtil.getPivotFromApproxMedian(test5,0,0,Comparator.naturalOrder());
      assertEquals(median5,0);


  }

  @Test
    void testMerge(){
        ArrayList<Integer> test1 = new ArrayList<>(Arrays.asList(4,5,6,1,2,3));
       SortUtil.merge(test1,0,2,5,Comparator.naturalOrder());
        ArrayList<Integer> testing = SortUtil.generateBestCase(6);
        assertEquals(testing,test1);

      ArrayList<Integer> test2 = new ArrayList<>(Arrays.asList(4,5,6,2,3));
      SortUtil.merge(test2,0,2,4,Comparator.naturalOrder());
      ArrayList<Integer> sorted2 = new ArrayList<>(Arrays.asList(2,3,4,5,6));
      assertEquals(test2, sorted2);
      //System.out.println(test2);

      ArrayList<Integer> test3 = new ArrayList<>(Arrays.asList(5,6,3));
      SortUtil.merge(test3,0,1,2,Comparator.naturalOrder());
      ArrayList<Integer> sorted3 = new ArrayList<>(Arrays.asList(3,5,6));
      assertEquals(test3, sorted3);

      ArrayList<Integer> test4 = new ArrayList<>(Arrays.asList(5,2));
      SortUtil.merge(test4,0,0,1,Comparator.naturalOrder());
      ArrayList<Integer> sorted4 = new ArrayList<>(Arrays.asList(2,5));
      assertEquals(test4, sorted4);

  }

  @Test
  void testMergeRecursive(){

        ArrayList<Integer> test2 = new ArrayList<>(Arrays.asList(4,5,6,7,8,1,2));
      SortUtil.mergesort(test2,Comparator.naturalOrder());
      ArrayList<Integer> sorted2 = new ArrayList<>(Arrays.asList(1,2,4,5,6,7,8));
      assertEquals(test2,sorted2);

      ArrayList<Integer> testodd = new ArrayList<>(Arrays.asList(4,5,6,7,1,2,3));
      SortUtil.mergesort(testodd,Comparator.naturalOrder());
      ArrayList<Integer> testingodd = SortUtil.generateBestCase(7);
      assertEquals(testingodd,testodd);


      ArrayList<Integer> test1 = new ArrayList<>(Arrays.asList(4,5,6,7,8,1,2,3));
      SortUtil.mergesort(test1,Comparator.naturalOrder());
      ArrayList<Integer> testing = SortUtil.generateBestCase(8);
      assertEquals(testing,test1);

      ArrayList<Integer> largeTest = SortUtil.generateAverageCase(100);
      ArrayList<Integer> sorted100 = SortUtil.generateBestCase(100);
      SortUtil.mergesort(largeTest,Comparator.naturalOrder());
      assertEquals(largeTest,sorted100);

  }


}