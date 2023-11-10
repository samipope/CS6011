package assignment02;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Type;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;



public class samiLibraryTest {



        private Library library;

        @BeforeEach
        public void setUp() {
            library = new Library<Type>(); // Create and populate your library with books
            //library.addAll("Mushroom_Publishing.txt");
            library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
            library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
            library.add(9780446580342L, "David Baldacci", "Simple Genius");

            var res = library.checkout(9780330351690L, "Jane Doe", 1, 1, 2008);
            var res1 = library.checkout(9780374292799L, "Jane Doe", 1, 1, 2008);
            var res2 = library.checkout(9780446580342L, "Jane Doe", 1, 1, 2008);
        }

        @Test
        public void assertNullBookFound() {
            // Choose an ISBN that exists in the library
            long existingIsbn = 123456789;
            // Call the lookup function
            LibraryBook<Type> foundBook = library.lookup(existingIsbn);
            // Check if the returned book is not null
            assertNull(foundBook);
        }

        @Test
        public void testLookupBookNotFound() {
            // Choose an ISBN that does not exist in the library
            long nonExistingIsbn = 987654321;
            // Call the lookup function
            LibraryBook<Type> foundBook = library.lookup(nonExistingIsbn);
            // Check if the returned book is null (not found)
            assertNull(foundBook);
        }


    @Test
    public void testGetOverdueListSomeOverdueBooks() {
        // Set the current date to a date when some books are overdue
        int year = 2023;
        int month = 9; // Assuming the test is run in October
        int day = 1;

        ArrayList<LibraryBook<Type>> overdueList = library.getOverdueList(year, month, day);

        // The overdueList should not be empty because there are overdue books
        assertFalse(overdueList.isEmpty());

        // You can add more specific assertions related to the overdue books here if needed
    }

    @Test
    public void testGetOverdueListAllOverdueBooks() {

        var res = library.checkout(9780330351690L, "Jane Doe", 1, 1, 2008);
        var res1 = library.checkout(9780374292799L, "Jane Doe", 1, 1, 2008);
        var res2 = library.checkout(9780446580342L, "Jane Doe", 1, 1, 2008);

        // Set the current date to a date when all books are overdue
        int year = 2023;
        int month = 8; // Assuming the test is run in October
        int day = 1;

        ArrayList<LibraryBook<Type>> overdueList = library.getOverdueList(year, month, day);

        // All books should be overdue in this case, so the list size should match the library size
        assertEquals(3, overdueList.size());

    }

  //  @Test
//    public void testGetOverdueListNoOverdueBooks() {
//
//        ArrayList<LibraryBook<Type>> emptyOverDue = library.getOverdueList(2003, 12, 12);
//
//        // The overdueList should be empty because there are no overdue books
//        assertEquals(0,emptyOverDue.size());
//    }



}














