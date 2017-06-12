package edu.hm.shareit.persistence;


import java.util.List;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.persistence.DatabaseManagerImpl.DuplicateException;
import edu.hm.shareit.persistence.DatabaseManagerImpl.MediaNotFoundException;

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
     * @throws DuplicateException 
     */
    void insertBook(Book book) throws DuplicateException;
    
    /**
     * 
     * @param disc 
     * @throws DuplicateException 
     */
    void insertDisc(Disc disc) throws DuplicateException;
    
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
    
    /**
     * 
     * @param book 
     */
    void deleteBook(Book book);
    
    /**
     * 
     * @param disc 
     */
    void deleteDisc(Disc disc);
    
    /**
     * 
     * @param book 
     * @throws MediaNotFoundException 
     */
    void updateBook(Book book) throws MediaNotFoundException;
    
    /**
     * 
     * @param disc 
     * @throws MediaNotFoundException 
     */
    void updateDisc(Disc disc) throws MediaNotFoundException;
    /**
     * 
     * @return . 
     */
    List<Book> getAllBooks();
    
    /**
     * 
     * @return . 
     */
    List<Disc> getAllDiscs();
    
}
