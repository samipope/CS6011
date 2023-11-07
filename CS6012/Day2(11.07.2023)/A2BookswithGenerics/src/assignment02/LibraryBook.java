package assignment02;
import java.util.GregorianCalendar;
public class LibraryBook extends Book {
    public String holder_=null;
    public GregorianCalendar dueDate_;

    public LibraryBook(long isbn, String author, String title) {
        super(isbn, author, title);

    }
    public String getHolder(){
       return holder_;
    }
    public GregorianCalendar getDueDate(){
       return dueDate_;
    }

    public void setHolder_(String holder_) {
        this.holder_ = holder_;
    }
    public void setDueDate_(GregorianCalendar calendar){
        this.dueDate_=calendar;
    }


}
