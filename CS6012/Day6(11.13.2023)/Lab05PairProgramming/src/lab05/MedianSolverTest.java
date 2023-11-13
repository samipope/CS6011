package lab05;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MedianSolverTest {

    @Test
    public void naturalOrderMedianTest(){
        Integer[] list = new Integer[] {3,5,1,4,2};
        MedianSolver<Integer> test = new MedianSolver<Integer>();
        int median = test.naturalOrderMedian(list);
        assertEquals(median, 3);

        String[] list2 = new String[]{"a","b","d","c"};
        MedianSolver<String> stringTest = new MedianSolver<String>();
        String medianofStrings = stringTest.naturalOrderMedian(list2);
        assertEquals(medianofStrings,"c");
    }

    @Test
    public void comparatorMedianTest(){

        //Using Library Book type to test
        Library<String> lib = new Library<>();

        Library.OrderByAuthor comparator = lib.authorComparator();

//        BinarySearchSet<LibraryBook<String>> testBook = new BinarySearchSet<>(comparator);
//        assertEquals(testBook.comparator(),comparator);

        LibraryBook<String> book1 = new LibraryBook(9780374292799L, "Thomas L. Friedman", "The World is Flat");
        LibraryBook<String> book2 = new LibraryBook(9780330351690L, "Jon Krakauer", "Into the Wild");
        LibraryBook<String> book3 = new LibraryBook(9780446580342L, "David Baldacci", "Simple Genius");
        LibraryBook<String> book4 = new LibraryBook(9780446580343L, "Brandon Sanderson", "The Way of Kings");
        LibraryBook<String> book5 = new LibraryBook(9780446580344L, "Brandon Sanderson", "Words of Radiance");
        LibraryBook<String> book6 = new LibraryBook(9780446580345L, "Brandon Sanderson", "Oathbringer");
        LibraryBook<String> book7 = new LibraryBook(9780446580346L, "Brandon Sanderson", "Rhythm of War");

        LibraryBook[] testBook = new LibraryBook[]{book1,book2,book3,book4,book5,book6,book7};

        /*
        order:
        0. BS oathbringer - book6
        1. BS ROW - book 7
        2. BS WOK - book4
        3. BS WOR - book5
        4. DB - book3
        5. JK - book2
        6. TF - book1

         */

        MedianSolver<LibraryBook<String>> testMedianBook = new MedianSolver<LibraryBook<String>>();
        LibraryBook<String> bookMedian =  testMedianBook.comparatorMedian(testBook,comparator);
        assertEquals(bookMedian, book5);


    }



}