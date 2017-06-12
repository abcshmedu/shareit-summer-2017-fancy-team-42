package edu.hm.shareit.persistence;


import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

/**
 * Manager interface for communication with database.
 * Implements persistance for service
 * 
 * @author Thomas Murschall
 *
 */
public interface DatabaseManager {
    
    /**
     * 
     * @param book 
     */
    void insertBook(Book book);
    
    /**
     * 
     * @param disc 
     */
    void insertDisc(Disc disc);
    
    /**
     * 
     * @param barcode 
     * @return .
     */
    Book getBook(String barcode);
    
    /**
     * 
     * @param barcode 
     * @return .
     */
    Disc getDisc(String barcode);
}
