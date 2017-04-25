package edu.hm.shareit.models;

/**
 * @author Thomas Murschall
 */
public class Book extends Medium {
    private String author;
    private String isbn;
    
    /**
     * Empty constructor.
     */
    public Book() {
        super("");
    }
    /**
     * Constructor.
     * @param title 
     * @param author 
     * @param isbn 
     */
    public Book(String author, String isbn, String title) {
        super(title);
        this.author = author;
        this.isbn = isbn;
    }
    
    /**
     * 
     * @return String, author
     */
    public String getAuthor() {
        return author;
    }
    /**
     * 
     * @return String, isbn
     */
    public String getIsbn() {
        return isbn;
    }
    @Override
    public boolean equals(Object other) {
        Book b = null;
        try {
            b = (Book) other;
        }
        catch (Exception e) {
            return false;
        }
        return this.getIsbn().equals(b.getIsbn());
    }
    @Override
    public int hashCode() {
        return this.getTitle().hashCode() + this.getIsbn().hashCode() + this.getAuthor().hashCode();
    }
    
}
