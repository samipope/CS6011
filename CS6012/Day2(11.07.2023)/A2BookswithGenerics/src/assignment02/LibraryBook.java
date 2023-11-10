package assignment02;
import java.util.GregorianCalendar;
public class LibraryBook<T> extends Book {
    public T holder_=null;
    public GregorianCalendar dueDate_; //auto initializes as null

    public LibraryBook(long isbn, String author, String title) {
        super(isbn, author, title);
    }
    public T getHolder(){
        return holder_;
    }
    public GregorianCalendar getDueDate(){
       return dueDate_;
    }

    public void setHolder_(T holder_) {
        this.holder_ = holder_;
    }
    public void setDueDate_(GregorianCalendar calendar){
        this.dueDate_=calendar;
    }


}
