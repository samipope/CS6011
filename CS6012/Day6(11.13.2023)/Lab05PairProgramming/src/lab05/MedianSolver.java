package lab05;
import java.util.Comparator;

//completed by samantha (sami) and elizabeth (ziz)

public class MedianSolver<E>{

    //first method : sami was driver / ziz was navigator
    //For the first implementation, the generic type placeholder for the method should be derived from the Comparable interface.
    public   E naturalOrderMedian(E[] list) {
        if (list==null){
            throw new IllegalArgumentException("array is null in naturalOrderMedian function");
        }
        //sort the array
        int i, j, min_idx;
        // One by one move boundary of
        // unsorted subarray
        for (i = 0; i < list.length - 1; i++) {

            // Find the minimum element in
            // unsorted array
            min_idx = i;
            for (j = i + 1; j < list.length; j++) {
                Comparable<E> tempLHS = (Comparable<E>) list[j];

                if (tempLHS.compareTo(list[min_idx]) < 0)
                    min_idx = j;
            }
            // Swap the found minimum element
            // with the first element
            if (min_idx != i){
                E temp = list[min_idx];
                list[min_idx] = list[i];
                list[i] = temp;
            }
        }

        int place = list.length/2;
        return list[place];
    }



    //second method : ziz was the driver / sami was navigator
    //For the second method, instead of requiring the generic type placeholder for the method be derived from the Comparable interface, accept an additional method parameter that is a Comparator and don't put any constraints on the generic type.

    public E comparatorMedian(E[] list, Comparator c) {
        if (list==null){
            throw new IllegalArgumentException("array is null, try again");
        }

        //Comparator< ?super E> comparator;

        //sort the array
        int i, j, min_idx;
        // One by one move boundary of
        // unsorted subarray
        for (i = 0; i < list.length - 1; i++) {

            // Find the minimum element in
            // unsorted array
            min_idx = i;
            for (j = i + 1; j < list.length; j++) {
                if (c.compare(list[min_idx],list[j]) < 0)
                    min_idx = j;
            }
            // Swap the found minimum element
            // with the first element
            if (min_idx != i){
                E temp = list[min_idx];
                list[min_idx] = list[i];
                list[i] = temp;
            }
        }

        int place = list.length/2;
        return list[place];
  }

}

//


