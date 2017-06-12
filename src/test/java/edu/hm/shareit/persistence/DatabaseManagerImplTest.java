package edu.hm.shareit.persistence;

import static org.junit.Assert.*;


import org.junit.BeforeClass;
import org.junit.Test;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

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
     * @exception Exception 
     */
    @Test
    public void testBook() throws Exception {
        Book book1 = new Book("Autor", "12345", "Test titile");
        manager.insertBook(book1);  
        assertEquals(manager.getBook("12345"), book1);
        manager.deleteBook(book1);
        assertEquals(manager.getBook("12345"), null);
    }
    
    /**
     * 
     */
    @Test
    public void getAllBooksTest() {
        Book book1 = new Book("Autor1", "12345", "Test title1");
        Book book2 = new Book("Autor", "54321", "Test title2");        
        
        manager.insertBook(book1);
        manager.insertBook(book2);
        
        assertTrue(manager.getAllBooks().contains(book1));
        assertTrue(manager.getAllBooks().contains(book2));
        
        manager.deleteBook(book1);
        manager.deleteBook(book2);
    }
    
    /**
     * 
     */
    @Test
    public void updateBookTest() {
        
        manager.insertBook(new Book("Autor", "12345", "Test title"));  
        
        Book book1 = new Book("Autor", "12345", "new Title");
        manager.updateBook(book1);
        assertEquals(manager.getBook("12345"), book1);
        manager.deleteBook(book1);
    }
    
    /**
     * 
     * @exception Exception 
     */
    @Test
    public void testDisc() throws Exception {
        final int fsk = 12;
        Disc disc1 = new Disc("III", "Markus", fsk, "Wrong Turn");
        manager.insertDisc(disc1);  
        assertEquals(manager.getDisc("III"), disc1);
        manager.deleteDisc(disc1);
        assertEquals(manager.getDisc("III"), null);
    }
    
    /**
     * 
     */
    @Test
    public void getAllDiscsTest() {
        
        final int fsk = 12;
        Disc disc1 = new Disc("III", "Markus", fsk, "Wrong Turn1");
        Disc disc2 = new Disc("IIII", "Markus", fsk, "Wrong Turn2");      
        
        manager.insertDisc(disc1);
        manager.insertDisc(disc2);
        
        assertTrue(manager.getAllDiscs().contains(disc1));
        assertTrue(manager.getAllDiscs().contains(disc2));
        
        manager.deleteDisc(disc1);
        manager.deleteDisc(disc2);
    }
    
    /**
     * 
     */
    @Test
    public void updateDiscTest() {
        final int fsk = 12;
        manager.insertDisc(new Disc("III", "Markus", fsk, "Wrong Turn1"));  
        
        Disc disc1 = new Disc("III", "Markus2", fsk, "Wrong Turn2");
        manager.updateDisc(disc1);
        assertEquals(manager.getDisc("III"), disc1);
        manager.deleteDisc(disc1);
    }

}
