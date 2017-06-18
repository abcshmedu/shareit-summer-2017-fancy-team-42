package edu.hm.shareit.persistence;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;


import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.persistence.DatabaseManagerImpl.DuplicateException;
import edu.hm.shareit.persistence.DatabaseManagerImpl.MediaNotFoundException;

/**
 * Testclass for DatabaseManagerImpl.
 * @author Thomas Murschall
 *
 */
public class DatabaseManagerImplTest {

    private static DatabaseManagerImpl manager;
    /**
     * Initiates the database;
     */
    @BeforeClass
    public static void inti() {
        manager = new DatabaseManagerImpl();
    }
    

    
    /**
     * Cleans up the database.
     */
    @After
    public void cleanUp() {
        List<Book> books = manager.getAllBooks();
        List<Disc> discs = manager.getAllDiscs();
        if (!books.isEmpty()) {
            for (Iterator<Book> it = books.iterator(); it.hasNext();) {
                manager.deleteBook(it.next());
            }
        }
        if (!discs.isEmpty()) {
            for (Iterator<Disc> it = discs.iterator(); it.hasNext();) {
                manager.deleteDisc(it.next());
            }
        }
    }
    /**
     * Test to add a book.
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
     * Test to fetch all added books;
     * @throws DuplicateException
     */
    @Test
    public void getAllBooksTest() throws DuplicateException {
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
     * Test to update a book located on the database.
     * @throws DuplicateException 
     * @throws MediaNotFoundException
     */
    @Test
    public void updateBookTest() throws DuplicateException, MediaNotFoundException {

        manager.insertBook(new Book("Autor", "12345", "Test title"));  

        Book book1 = new Book("Autor", "12345", "new Title");
        manager.updateBook(book1);
        assertEquals(manager.getBook("12345"), book1);
        manager.deleteBook(book1);
    }

    /**
     * Test to add a disc.
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
     * Test to fetch all discs.
     * @throws DuplicateException
     */
    @Test
    public void getAllDiscsTest() throws DuplicateException {

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
     * Test to update a disc located on the database;
     * @throws DuplicateException
     * @throws MediaNotFoundException
     */
    @Test
    public void updateDiscTest() throws DuplicateException, MediaNotFoundException {
        final int fsk = 12;
        manager.insertDisc(new Disc("III", "Markus", fsk, "Wrong Turn1"));  

        Disc disc1 = new Disc("III", "Markus2", fsk, "Wrong Turn2");
        manager.updateDisc(disc1);
        assertEquals(manager.getDisc("III"), disc1);
        manager.deleteDisc(disc1);
    }

    /**
     * Invalid insert operation of a duplicate book.
     * @throws DuplicateException
     */
    @Test (expected = DuplicateException.class)
    public void insertDuplicateBookTest() throws DuplicateException {
        Book book1 = new Book("Autor", "12345", "Test titile");
        manager.insertBook(book1);
        
        manager.insertBook(book1); 
    }

    /**
     * Invalid insert operation of a duplicate disc.
     * @throws DuplicateException
     */
    @Test (expected = DuplicateException.class)
    public void insertDuplicateDiscTest() throws DuplicateException {
        final int fsk = 12;
        Disc disc1 = new Disc("III", "Markus2", fsk, "Wrong Turn2");
        manager.insertDisc(disc1);  
        manager.insertDisc(disc1);     
    }
    
    /**
     * Invalid update of a book.
     * @throws MediaNotFoundException
     */
    @Test (expected = MediaNotFoundException.class)
    public void wrongUpdateBookTest() throws MediaNotFoundException {
        Book book1 = new Book("Autor", "12345", "Test titile");
        manager.updateBook(book1);
    }
    
    /**
     * Invalid update of a disc.
     * @throws MediaNotFoundException
     */
    @Test (expected = MediaNotFoundException.class)
    public void wrongUpdateDiscTest() throws MediaNotFoundException {
        final int fsk = 12;
        Disc disc1 = new Disc("III", "Markus2", fsk, "Wrong Turn2");
        manager.updateDisc(disc1);
    }
    
    /**
     * Test to clear the database;
     * @throws DuplicateException
     */
    @Test
    public void deleteAllTest() throws DuplicateException {
        final int fsk = 12;
        Disc disc1 = new Disc("III", "Markus2", fsk, "Wrong Turn2");
        manager.insertDisc(disc1); 
        Book book1 = new Book("Autor", "12345", "Test titile");
        manager.insertBook(book1);
        
        manager.deleteAll();
        
        assertTrue(manager.getAllBooks().isEmpty());
        assertTrue(manager.getAllDiscs().isEmpty());
    }
}
