package lab05;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Class representation of a library (a collection of library books).
 * 
 */
public class Library<T> {

  private ArrayList<LibraryBook<T>> library;

  public Library() {
    library = new ArrayList<LibraryBook<T>>();
  }

  /**
   * Add the specified book to the library, assume no duplicates.
   * 
   * @param isbn
   *          -- ISBN of the book to be added
   * @param author
   *          -- author of the book to be added
   * @param title
   *          -- title of the book to be added
   */
  public void add(long isbn, String author, String title) {
    library.add(new LibraryBook(isbn, author, title));
  }

  /**
   * Add the list of library books to the library, assume no duplicates.
   * 
   * @param list
   *          -- list of library books to be added
   */
  public void addAll(ArrayList<LibraryBook<T>> list) {
    library.addAll(list);
  }

  /**
   * Add books specified by the input file. One book per line with ISBN, author,
   * and title separated by tabs.
   * 
   * If file does not exist or format is violated, do nothing.
   * 
   * @param filename
   */
  public void addAll(String filename) {
    ArrayList<LibraryBook<T>> toBeAdded = new ArrayList<LibraryBook<T>>();

    try (Scanner fileIn = new Scanner(new File(filename))) {

      int lineNum = 1;

      while (fileIn.hasNextLine()) {
        String line = fileIn.nextLine();

        try (Scanner lineIn = new Scanner(line)) {
          lineIn.useDelimiter("\\t");

          if (!lineIn.hasNextLong()) {
            throw new ParseException("ISBN", lineNum);
          }
          long isbn = lineIn.nextLong();

          if (!lineIn.hasNext()) {
            throw new ParseException("Author", lineNum);
          }
          String author = lineIn.next();

          if (!lineIn.hasNext()) {
            throw new ParseException("Title", lineNum);
          }
          String title = lineIn.next();
          toBeAdded.add(new LibraryBook(isbn, author, title));
        }
        lineNum++;
      }
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage() + " Nothing added to the library.");
      return;
    } catch (ParseException e) {
      System.err.println(e.getLocalizedMessage() + " formatted incorrectly at line " + e.getErrorOffset()
          + ". Nothing added to the library.");
      return;
    }

    library.addAll(toBeAdded);
  }

  /**
   * Returns the holder of the library book with the specified ISBN.
   * 
   * If no book with the specified ISBN is in the library, returns null.
   * 
   * @param isbn
   *          -- ISBN of the book to be looked up
   */
  public T lookup(long isbn) {
    for(LibraryBook<T> book : library){
      if(book.getIsbn()== isbn){
        return book.getHolder();
      }
    }
    return null;
  }

  /**
   * Returns the list of library books checked out to the specified holder.
   * 
   * If the specified holder has no books checked out, returns an empty list.
   * 
   * @param holder
   *          -- holder whose checked out books are returned
   */
  public ArrayList<LibraryBook<T>> lookup(T holder) {
    ArrayList<LibraryBook<T>> result = new ArrayList<LibraryBook<T>>();

    for(LibraryBook<T> book : library){
      if(book.getHolder() != null && book.getHolder().equals(holder)){ //null indicates there is no holder
        result.add(book);
      }
    }

    return result;
  }

  /**
   * Sets the holder and due date of the library book with the specified ISBN.
   * 
   * If no book with the specified ISBN is in the library, returns false.
   * 
   * If the book with the specified ISBN is already checked out, returns false.
   * 
   * Otherwise, returns true.
   * 
   * @param isbn
   *          -- ISBN of the library book to be checked out
   * @param holder
   *          -- new holder of the library book
   * @param month
   *          -- month of the new due date of the library book
   * @param day
   *          -- day of the new due date of the library book
   * @param year
   *          -- year of the new due date of the library book
   * 
   */
  public boolean checkout(long isbn, T holder, int month, int day, int year) {

    boolean available = false;

    for(LibraryBook<T> book : library){ //for each book in the library
      if(book.getIsbn() == isbn){ //if the isbn is the requested one
        if(book.getHolder() == null){ //and is not currently checked out (holder == null)
          available = true; //the book is available
          book.checkOut(holder,month,day,year);
          break; //break out of the loop
        }
      }
    }

    return available;
  }

  /**
   * Unsets the holder and due date of the library book.
   * 
   * If no book with the specified ISBN is in the library, returns false.
   * 
   * If the book with the specified ISBN is already checked in, returns false.
   * 
   * Otherwise, returns true.
   * 
   * @param isbn
   *          -- ISBN of the library book to be checked in
   */
  public boolean checkin(long isbn) {

    for(LibraryBook<T> book : library){
      if(book.getIsbn() == isbn){
        //the book holder will be null if the book is checked in already
        if(book.getHolder() != null) {
          book.checkIn();
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Unsets the holder and due date for all library books checked out be the
   * specified holder.
   * 
   * If no books with the specified holder are in the library, returns false;
   * 
   * Otherwise, returns true.
   * 
   * @param holder
   *          -- holder of the library books to be checked in
   */
  public boolean checkin(T holder) {

    boolean foundbook = false;
    for(LibraryBook<T> book : library){
      if(book.getHolder() != null && book.getHolder().equals(holder)){
        //the book holder will be null if the book is checked in already
          book.checkIn();
          foundbook = true;
      }
    }
    return foundbook;
  }

  /**
   * Returns the list of library books, sorted by ISBN (smallest ISBN
   first).
   */
  public ArrayList<LibraryBook<T>> getInventoryList() {
    ArrayList<LibraryBook<T>> libraryCopy = new ArrayList<LibraryBook<T>>();
    libraryCopy.addAll(library);

    //do the actual sorting if it is not empty
    if(!libraryCopy.isEmpty()){
      OrderByIsbn comparator = new OrderByIsbn();
      sort(libraryCopy, comparator);
    }

    return libraryCopy;
  }

  /**
   * Returns the list of library books, sorted by author
   */
  public ArrayList<LibraryBook<T>> getOrderedByAuthor() {

    ArrayList<LibraryBook<T>> libraryCopy = new ArrayList<LibraryBook<T>>();
    libraryCopy.addAll(library);
    if(!libraryCopy.isEmpty()){
      OrderByAuthor comparator = new OrderByAuthor();
      sort(libraryCopy, comparator);
    }

    return libraryCopy;
  }

  public OrderByAuthor authorComparator() {
    return new OrderByAuthor();
  }

  /**
   * Returns the list of library books whose due date is older than the
   input
   * date. The list is sorted by date (oldest first).
   *
   * If no library books are overdue, returns an empty list.
   */
  public ArrayList<LibraryBook<T>> getOverdueList(int month, int day, int year) {

    GregorianCalendar due = new GregorianCalendar(year,month,day);

    ArrayList<LibraryBook<T>> overdueBooks = new ArrayList<LibraryBook<T>>();
    for(LibraryBook<T> book : library){
      boolean overdue = false;
      //should this be <=? I think it is not overdue if it comes in ON the day it is due
      if(book.getDueDate() != null && book.getDueDate().compareTo(due) < 0){
        overdue = true;
      }
      if(book.getDueDate() != null && overdue){
        overdueBooks.add(book);
      }
    }

    if(!overdueBooks.isEmpty()){
      OrderByDueDate comparator = new OrderByDueDate();
      sort(overdueBooks,comparator);
    }

    return overdueBooks;
  }




  /**
   * Performs a SELECTION SORT on the input ArrayList.
   * 1. Find the smallest item in the list.
   * 2. Swap the smallest item with the first item in the list.
   * 3. Now let the list be the remaining unsorted portion
   * (second item to Nth item) and repeat steps 1, 2, and 3.
   */
  private static <ListType> void sort(ArrayList<ListType> list, Comparator<ListType> c) {
    for (int i = 0; i < list.size() - 1; i++) {
      int j, minIndex;
      for (j = i + 1, minIndex = i; j < list.size(); j++)
        if (c.compare(list.get(j), list.get(minIndex)) < 0)
          minIndex = j;
      ListType temp = list.get(i);
      list.set(i, list.get(minIndex));
      list.set(minIndex, temp);
    }
  }



  /**
   * Comparator that defines an ordering among library books using the
   ISBN.
   */
  protected class OrderByIsbn implements Comparator<LibraryBook<T>> {
    /**
     * Returns a negative value if lhs is smaller than rhs. Returns a positive
     * value if lhs is larger than rhs. Returns 0 if lhs 	and rhs are equal.
     */
    public int compare(LibraryBook<T> lhs, LibraryBook<T> rhs) {
      return (int) (lhs.getIsbn() - rhs.getIsbn());
    }
  }

  /**
   * Comparator that defines an ordering among library books using the
   author, and book title as a tie-breaker.
   */
  public class OrderByAuthor implements Comparator<LibraryBook<T>> {

    public int compare(LibraryBook<T> lhs, LibraryBook<T> rhs){
      int result = lhs.getAuthor().compareTo(rhs.getAuthor());
      if(result == 0){//if they are equal it should return 0
        return lhs.getTitle().compareTo(rhs.getTitle()); //then return the tie-breaker of the title
      }
      return lhs.getAuthor().compareTo(rhs.getAuthor());
    }
  }

  /**
   * Comparator that defines an ordering among library books using the
   due date.
   */
  protected class OrderByDueDate implements Comparator<LibraryBook<T>> {

    public int compare(LibraryBook<T> lhs, LibraryBook<T> rhs){
      return lhs.getDueDate().compareTo(rhs.getDueDate());
    }

  }

}
