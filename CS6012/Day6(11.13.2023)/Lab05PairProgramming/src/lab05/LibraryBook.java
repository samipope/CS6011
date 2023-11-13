package lab05;

import java.util.GregorianCalendar;

public class LibraryBook<T> extends Book{
    private T holder_;
    private GregorianCalendar dueDate_;


    /**
     * The constructur of a library book. holder and dueDate are null until the book is checked out
     *
     * @param isbn
     *          -- ISBN of the book to be added
     * @param author
     *          -- author of the book to be added
     * @param title
     *          -- title of the book to be added
     */
    public LibraryBook(long isbn, String author, String title){
        super(isbn, author, title);
        holder_ = null;
        dueDate_ = null;
    }

    //returns the holder
    public T getHolder(){
        return holder_;
    }

    //returns the due date
    public GregorianCalendar getDueDate(){
        return dueDate_;
    }

    /**
    This function is called when a book is checked in or out of the library.
    It sets the holder and due date to null
     */
    public void checkIn(){
        holder_ = null;
        dueDate_ = null;
    }


    /**
     * This function is called when a book is checked out and sets the holder and duedate parameters
     *
     * @param name
     *          -- the ID of the person checking out the book (generic type)
     * @param month
     *          -- month of the due date
     * @param day
     *          -- day of the due date
     * @param year
     *          -- year of the due date
     */
    public void checkOut(T name, int month, int day, int year){
        holder_ = (T) name;
        dueDate_ = new GregorianCalendar(year, month, day);
    }
}
