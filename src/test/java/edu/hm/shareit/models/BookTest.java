package edu.hm.shareit.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for class Book.
 * @author Thomas Murschall
 *
 */
public class BookTest {
    private Book book;
    
    /**
     * Setup for the tests.
     */
    @Before
    public void setUp() {
        book = new Book("Mustermann", "1234", "Test");
    }
    
    /**
     * Check returned author.
     * @throws Exception 
     */
    @Test
    public void getAuthorTest() throws Exception {
        assertTrue("Mustermann" == book.getAuthor());
    }
    
    /**
     * Check returned isbn.
     * @throws Exception 
     */
    @Test
    public void getIsbnTest() throws Exception {
        assertTrue("1234" == book.getIsbn());
    }
    
    /**
     * Check returned title.
     * @throws Exception 
     */
    @Test
    public void getTitleTest() throws Exception {
        assertTrue("Test" == book.getTitle());
    }
    
    /**
     * All possible tests for equals.
     * @throws Exception 
     */
    @Test
    public void equalTest() throws Exception {
        assertTrue(book.equals(book));
        assertFalse(book.equals(new Book("other", "4321", "other Title")));
        final int fsk = 6;
        assertFalse(book.equals(new Disc("a", "b", fsk, "other")));
    }
    
    /**
     * Check Hashfunction.
     * @throws Exception 
     */
    @Test
    public void hashcodeTest() throws Exception {
        assertTrue("Test".hashCode() + "1234".hashCode() + "Mustermann".hashCode() == book.hashCode());
    }
}
