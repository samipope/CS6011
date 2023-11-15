package assignment04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class SortUtil <T> {

    /**
     * performs a mergesort on the generic ArrayList given as input
     * this is the driver method
     * @param list
     * @param comparator
     * @param <T>
     */
    public static <T> void mergesort(ArrayList<T> list, Comparator<? super T> comparator){
        mergesortRecursive(list,0, list.size()-1, comparator);
    }

    private static <T> void mergesortRecursive(ArrayList<T> array, int start, int end, Comparator<? super T> comparator){
        int threshold = 0 ;
        if( (end - start) < threshold){
            insertionSort(array,comparator,start,end);
        }
        else if(start<end){

            int middle = (start +end)/2;
            mergesortRecursive(array, start, middle, comparator);
            mergesortRecursive(array, (middle+1), end, comparator);
            merge(array, start, middle, end, comparator);
        }
    }
//
//    //(start<end)

    public static <T> void merge(ArrayList<T> array,int start, int middle, int end, Comparator<? super T> comparator){
        ArrayList<T> temp = new ArrayList<T>();
//        int i1 = start;
//        int i2= middle;
//
//        while(i1 <= middle && i2 <= end){
//            if(comparator.compare(list.get(i1),list.get(i2)) <= 0){
//                temp.add(list.get(i1));
//                i1++;
//            }
//            else{
//                temp.add(list.get(i2));
//                i2++;
//            }
//
//        }
//        //finish off with whatever is left
//        while(i1 < middle){
//            temp.add(list.get(i1));
//            i1++;
//        }
//        while(i2 <= end){
//            temp.add(list.get(i2));
//            i2++;
//        }
//        //int index = 0;
//        for(int i = start, index =  0; index < temp.size(); i++, index++){
//            T tempElement = temp.get(index);
//            list.set(i,tempElement);
//        }

        T[] leftArray = (T[]) new Comparable[middle - start + 1];
        T[] rightArray = (T[]) new Comparable[end - middle];

        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = array.get(start + i);
        }

        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = array.get(middle + 1 + i);
        }

        int leftIndex = 0, rightIndex = 0;

        int currentIndex = start;

        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {

            if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0) {
                array.set(currentIndex, leftArray[leftIndex]);
                leftIndex++;
            } else {
                array.set(currentIndex, rightArray[rightIndex]);
                rightIndex++;
            }
            currentIndex++;
        }

        while (leftIndex < leftArray.length) {
            array.set(currentIndex++, leftArray[leftIndex++]);
        }

        while (rightIndex < rightArray.length) {
            array.set(currentIndex++, rightArray[rightIndex++]);
        }







    }



    /**
     * placeholder for insertionSort
     * @param <T>
     */
    public static <T> void insertionSort(ArrayList<T> list, Comparator<? super T> comparator, int start, int end){

        //int n = list.size();

        for (int j = start+1; j <= end; j++) {
            T temp = list.get(j);
            int i = j-1;
            while ( (i > -1) && ( comparator.compare(list.get(i),temp) > 0) ) {
                list.set(i+1,list.get(i));
                i--;
            }
            list.set(i+1,temp);
        }

    }

    /**
     * this method performs a quicksort on the generic ArrayList given as input
     * this is the driver method
     * @param list
     * @param comparator
     * @param <T>
     */
    public static <T> void quicksort(ArrayList<T> list , Comparator<? super T> comparator){

        recursiveQuicksort(list,comparator,0,list.size() - 1);
    }

    /**
     *
     * @param list
     * @param comparator
     * @param beg
     * @param end

     */
    private static <T> void recursiveQuicksort(ArrayList<T> list, Comparator<? super T> comparator, int beg, int end){
        //base case
        int pivot = beg;
        //pick a pivot
        if(beg < end){
            int breakpoint = partition(list, beg, end, pivot, comparator);
            //left hand side, should go from 0 to breakpoint+1
            recursiveQuicksort(list,comparator,beg,breakpoint-1);
            //right hand side, should go from breakpoint-1 to the end
            recursiveQuicksort(list,comparator,breakpoint+1,list.size() - 1);
        }

    }


    /**
     *
     * @param list
     *      --the list to check for things
     * @param beginning
     *      --the beginning index of the partition we are checking
     * @param end
     *      --the ending index of the partition we are checking
     * @param pivot
     *      --the index of the pivot point for comparison
     * @return
     * @param <T>
     */
    public static <T> int partition(ArrayList<T> list, int beginning, int end, int pivot, Comparator<? super T> comparator){
        //first swap pivot to the end
        T temp = list.get(pivot);
        list.set(pivot, list.get(end));
        list.set(end, temp);

        int left = beginning;
        int right;

        if(end == 0){
            right = end;
        }
        else{
            right = end - 1;
        }

        boolean done = false;
        while(!done){
            while(comparator.compare(list.get(left), list.get(end)) <= 0 ){
                left++;
                if(left > end){
                    left--;
                    break;
                }
            }
            while(comparator.compare(list.get(right),list.get(end)) > 0){
                right--;
                if(right<0){
                    right++;
                    break;
                }
            }
            if(left < right){
                //swap them
                temp = list.get(left);
                list.set(left, list.get(right));
                list.set(right, temp);
                left ++;
                right --;
            }
            if(left >= right){
                temp = list.get(left);
                list.set(left, list.get(end));
                list.set(end, temp);
                done = true;
            }

        }

        return left;
    }


    /**
     * we made this helper function public for the purpose of testing and debugging. can be made to private if needed.
     * returns an approximate median by sampling three elements from an array
     * @param list
     *      --the list we're searching through
     * @param beginning
     *      --the beginning of the search window
     * @param end
     *      --the end of the search window
     * @param comparator
     *      --the comparator used on the generic type
     * @return
     */
    public static <T> int getPivotFromApproxMedian(ArrayList<T> list, int beginning, int end, Comparator<? super T> comparator){

        T middleElement = list.get((end-beginning)/2);
        T endElement = list.get(end);
        T beginningElement = list.get(beginning);
        //load into a smaller array
        ArrayList<T> smallList = new ArrayList<T>(Arrays.asList(middleElement,endElement,beginningElement));

        insertionSort(smallList,comparator,0,2);

        if(comparator.compare(smallList.get(1),beginningElement) == 0){
            return beginning;
        }
        else if(comparator.compare(smallList.get(1),middleElement) == 0){
            return (end - beginning)/2;
        }
        else{
            return end;
        }

    }

    /**
     * This function returns the element in the list to be used as a pivot in quicksort. It always returns the beginning of the area to search
     * @param list
     *      --the list that we are "getting" the pivot from
     * @param beginning
     *      --the beginning element that we are searching at
     * @return
     */
    private int getFirstElementPivot(ArrayList<T> list, int beginning){
        //helper method that returns the first element in a list
        return beginning;
    }

    /**
     * returns a random index to be used as the pivot in quicksort between the beginning and ending values
     * @param beg
     *      --the first valid index
     * @param end
     *      --the last valid index
     * @return
     *      --returns a random index between beg and end to be used as a pivot
     * @param <T>
     */
    public static <T> int getRandomPivot(int beg, int end){
        Random r = new Random();
        return r.nextInt(beg,end);
    }


    /**
     * this method generates and returns an ArrayList of integers 1 to size in ascending order
     * if the size is positive, the array returned will have elements. If it is 0 or negative it will return an empty array
     * @param size
     * @return
     */
   public static ArrayList<Integer> generateBestCase(int size){
       ArrayList<Integer> result = new ArrayList<>();

       for(int i = 1; i <= size; i++){
           result.add(i);
       }

       return result;
   }

    /**
     * This method generates and returns an ArrayList of integers 1 to size in permuted order (i.e. randomly ordered)
     * @param size
     * @return
     */
   public static ArrayList<Integer> generateAverageCase(int size){
       //initiate the array with our best case function
       ArrayList<Integer> result = generateBestCase(size);

       Random r = new Random();
       //shuffle the values of the array randomly
       for(int i =size-1; i>0;i--) {
           int j = r.nextInt(i + 1);

           int temp = result.get(i);
           result.set(i, result.get(j));
           result.set(j, temp);
       }

       return result;
   }

    /**
     * this method generates and returns an ArrayList of integers 1 to size in descending order
     * if the size is 0 or negative then the function returns an empty array
     * @param size
     * @return
     */
   public static ArrayList<Integer> generateWorstCase(int size){
       ArrayList<Integer> result = new ArrayList<>();
       for(int i = size; i >= 1; i--){
           result.add(i);
       }
       return result;
   }


}
