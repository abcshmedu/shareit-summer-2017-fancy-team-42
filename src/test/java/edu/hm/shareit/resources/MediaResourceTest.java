package edu.hm.shareit.resources;


import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.hm.shareit.Services.MediaServiceResult;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

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

    @After
    public void clearUp(){
        medRes.delAll();
    }


    /**
     * Insert a valid book.
     * @throws Exception 
     */
    @Test
    public void insertBookTest() throws Exception {
        Book b = new Book("Mustermann", "9783065210201", "Title");
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
        Disc d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        Response res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
    }
    
    /**
     * Valid book update.
     * @throws Exception 
     */
    @Test
    public void updateBookTest() throws Exception {
        Book b = new Book("Mustermann", "9783065210201", "Title");
        Response res = medRes.createBook(b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Musterfrau", "9783065210201", "Title");
        
        res = medRes.changeBook("9783065210201", b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
    }
    
    /**
     * Valid disc update.
     * @throws Exception 
     */
    @Test
    public void updateDiscTest() throws Exception {
        final int fsk = 6;
        Disc d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        Response res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("5449000096241", "Musterfrau", fsk, "Musterfilm");
        res = medRes.changeDisc("5449000096241", d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
    }
    
    /**
     * Duplicate insert of book.
     * @throws Exception 
     */
    @Test
    public void duplicateBookTest() throws Exception {
        Book b = new Book("Mustermann", "9783065210201", "Title");
        Response res = medRes.createBook(b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Mustermann", "9783065210201", "Title");
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
        Disc d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        Response res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.DUPLICATE.getStatus() == res.getEntity());
    }
    
    /**
     * Invalid book update.
     * @throws Exception 
     */
    @Test
    public void invalidUpdateBookTest() throws Exception {
        Book b = new Book("Mustermann", "9783065210201", "Title");
        Response res = medRes.createBook(b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Musterfrau", "9783065210201", "Title");
        
        res = medRes.changeBook("9783127323207", b);
        assertTrue(MediaServiceResult.BADREQUEST.getStatus() == res.getEntity());
    }
    
    /**
     * Invalid disc update.
     * @throws Exception 
     */
    @Test
    public void invalidUpdateDiscTest() throws Exception {
        final int fsk = 6;
        Disc d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        Response res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("5030917105081", "Musterfrau", fsk, "Musterfilm");
        res = medRes.changeDisc("5030917105081", d);
        assertTrue(MediaServiceResult.NOTFOUND.getStatus() == res.getEntity());
    }
    
    /**
     *  Test to find a book with the isbn.
     * @throws Exception 
     */
    @Test
    public void getBookIsbnTest() throws Exception {
        String isbn = "9783127323207";
        Book b = new Book("Ian Flemming",isbn,"Casino Royal");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(b);
        Response res = medRes.createBook(b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());

        res = medRes.getISBNBook(isbn);
        assertTrue(json.equals(res.getEntity()));
    }
    /**
     * Test to find a disc with the barcode.
     * @throws Exception 
     */
    @Test
    public void getBarDiscTest() throws Exception {
        final int fsk = 6;
        Disc d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(d);
        Response res = medRes.createDisc(d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        res = medRes.getBarDisc("5449000096241");
        assertTrue(json.equals(res.getEntity()));
    }
    
    /**
     * Check if the books were returned correctly.
     * @throws Exception 
     */
    @Test
    public void getBooksTest() throws Exception {
        String start = "[]";
        Response res = medRes.getBooks();
        assertTrue(res.getEntity().equals(start));
        Book b1 = new Book("Ian Flemming","9783127323207","Casino Royal");
        Book b2 = new Book("Ian Flemming","9783065210201","Goldeneye");
        medRes.createBook(b1);
        medRes.createBook(b2);
        ObjectMapper mapper = new ObjectMapper();
        String json ="["+ mapper.writeValueAsString(b1)+","+mapper.writeValueAsString(b2)+"]";
        res = medRes.getBooks();
        assertTrue(res.getEntity().equals(json));
    }

    /**
     * Check if the discs were returned correctly.
     * @throws Exception 
     */
    @Test
    public void getDiscsTest() throws Exception {
        String start = "[]";
        Response res = medRes.getDiscs();
        assertTrue(res.getEntity().equals(start));
        Disc d1 = new Disc("5449000096241","Ian Flemming",12,"Casino Royal");
        Disc d2 = new Disc("5030917105081","Ian Flemming",12,"Goldeneye");
        Response res1 = medRes.createDisc(d1);
        Response res2 = medRes.createDisc(d2);
        ObjectMapper mapper = new ObjectMapper();
        String json ="["+ mapper.writeValueAsString(d1)+","+mapper.writeValueAsString(d2)+"]";
        res = medRes.getDiscs();
        assertTrue(res.getEntity().equals(json));
    }

    @Test
    public void delAllTest() throws Exception{
        Disc d = new Disc("5449000096241","Steven Spielberg",12,"Indiana Jones");
        Book b = new Book("Ian Flemming","9783065210201","Casino Royal");

        medRes.createBook(b);
        medRes.createDisc(d);
        Response res = medRes.delAll();
        Response res1 = medRes.createBook(b);
        Response res2 = medRes.createDisc(d);

        assertTrue(MediaServiceResult.SUCCESS.getStatus()==res.getEntity() && MediaServiceResult.SUCCESS.getStatus() == res1.getEntity() && MediaServiceResult.SUCCESS.getStatus()==res2.getEntity());
    }
}
