package edu.hm.shareit.persistence;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.hm.shareit.models.Book;

/**
 * Testclass for DatabaseManagerImpl.
 * @author Thomas Murschall
 *
 */
public class DatabaseManagerImplTest {
    
    private static DatabaseManagerImpl manager;
    /**
     * 
     */
    @BeforeClass
    public static void inti() {
        manager = new DatabaseManagerImpl();
    }
    /**
     * 
     */
    @Test
    public void testBook() {
        Book book1 = new Book("Autor", "12345", "Test titile");
        manager.insertBook(book1);
        
        assertEquals(manager.getBook("12345"), book1);
    }

}
