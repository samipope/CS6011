package lab05;

/**
 * Class representation of a book. The ISBN, author, and title can never change
 * once the book is created.
 * 
 * Note that ISBNs are unique.
 *
 */
public class Book {

  private final long isbn;

  private final String author;

  private final String title;

  public Book(long isbn, String author, String title) {
    this.isbn = isbn;
    this.author = author;
    this.title = title;
  }

  /**
   * @return the author
   */
  public String getAuthor() {
    return this.author;
  }

  /**
   * @return the ISBN
   */
  public long getIsbn() {
    return this.isbn;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Two books are considered equal if they have the same ISBN, author, and title.
   * 
   * @param other
   *          -- the object begin compared with "this"
   * @return true if "other" is a Book and is equal to "this", false otherwise
   */
  public boolean equals(Object other) {
    // check first that the types match
    if(!(other instanceof Book)){
      return false;
    }

    //type cast the other to be a book
    Book otherBook = (Book)other;

    //check that the appropriate data is equivalent
    return this.isbn == otherBook.isbn && this.title.equals(otherBook.title) && this.author.equals(otherBook.author);
  }

  /**
   * Returns a string representation of the book.
   */
  public String toString() {
    return isbn + ", " + author + ", \"" + title + "\"";
  }

  @Override
  public int hashCode() {
    return (int) isbn + author.hashCode() + title.hashCode();
  }
}
