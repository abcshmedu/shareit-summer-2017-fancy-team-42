package edu.hm.shareit.resource;


import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.hm.shareit.Services.MediaServiceResult;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.resources.MediaResource;

/**
 * Tests for the MediaRessource-class.
 * @author Thomas Murschall
 *
 */
public class MediaResourceTest {
    private MediaResource medRes;

    /**
     * Setting up for the tests.
     */
    @Before
    public void setUp() {
        medRes = new MediaResource();
    }
    /**
     * Insert a valid book.
     * @throws Exception 
     */
    @Test
    public void insertBookTest() throws Exception {
        Book b = new Book("Mustermann", "1", "Title");
        Response res = medRes.createBook(b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
    }
    
    /**
     * Insert a valid disc.
     * @throws Exception 
     */
    @Test
    public void insertDiscTest() throws Exception {
        final int fsk = 6;
        Disc d = new Disc("I", "Mustermann", fsk, "Musterfilm");
        Response res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
    }
    
    /**
     * Valid book update.
     * @throws Exception 
     */
    @Test
    public void updateBookTest() throws Exception {
        Book b = new Book("Mustermann", "2", "Title");
        Response res = medRes.createBook(b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Musterfrau", "2", "Title");
        
        res = medRes.changeBook("2", b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
    }
    
    /**
     * Valid disc update.
     * @throws Exception 
     */
    @Test
    public void updateDiscTest() throws Exception {
        final int fsk = 6;
        Disc d = new Disc("II", "Mustermann", fsk, "Musterfilm");
        Response res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("II", "Musterfrau", fsk, "Musterfilm");
        res = medRes.changeDisc("II", d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
    }
    
    /**
     * Duplicate insert of book.
     * @throws Exception 
     */
    @Test
    public void duplicateBookTest() throws Exception {
        Book b = new Book("Mustermann", "3", "Title");
        Response res = medRes.createBook(b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Mustermann", "3", "Title");
        res = medRes.createBook(b);
        assertTrue(MediaServiceResult.DUPLICATE.getStatus() == res.getEntity());
    }
    
    /**
     * Duplicate insert of disc.
     * @throws Exception 
     */
    @Test
    public void duplicateDiscTest() throws Exception {
        final int fsk = 6;
        Disc d = new Disc("III", "Mustermann", fsk, "Musterfilm");
        Response res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("III", "Mustermann", fsk, "Musterfilm");
        res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.DUPLICATE.getStatus() == res.getEntity());
    }
    
    /**
     * Invalid book update.
     * @throws Exception 
     */
    @Test
    public void invalidUpdateBookTest() throws Exception {
        Book b = new Book("Mustermann", "4", "Title");
        Response res = medRes.createBook(b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Musterfrau", "4", "Title");
        
        res = medRes.changeBook("3", b);
        assertTrue(MediaServiceResult.ERROR.getStatus() == res.getEntity());
    }
    
    /**
     * Invalid disc update.
     * @throws Exception 
     */
    @Test
    public void invalidUpdateDiscTest() throws Exception {
        final int fsk = 6;
        Disc d = new Disc("IIII", "Mustermann", fsk, "Musterfilm");
        Response res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("III", "Musterfrau", fsk, "Musterfilm");
        res = medRes.changeDisc("III", d);
        System.out.println(res.getEntity());
        assertTrue(MediaServiceResult.NOTFOUND.getStatus() == res.getEntity());
    }
    
    /**
     *  Test to find a book with the isbn.
     * @throws Exception 
     */
    @Test
    public void getBookIsbnTest() throws Exception {
        
    }
    /**
     * Test to find a disc with the barcode.
     * @throws Exception 
     */
    @Test
    public void getBarDiscTest() throws Exception {
        final int fsk = 6;
        Disc d = new Disc("IIIIII", "Mustermann", fsk, "Musterfilm");
        Response res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        res = medRes.getBarDisc("IIIIII");
        System.err.println(res.getEntity());
        System.err.println(d.hashCode());
        assertTrue(d.equals(res.getEntity()));
    }
    
  //TODO need to implement, problem because of missing delete possibility.
    /**
     * Check if the books were returned correctly.
     * @throws Exception 
     */
    @Ignore
    @Test
    public void getBooksTest() throws Exception {
        
    }
    
    //TODO need to implement, problem because of missing delete possibility.
    /**
     * Check if the discs were returned correctly.
     * @throws Exception 
     */
    @Ignore
    @Test
    public void getDiscsTest() throws Exception {
        
    }
}
