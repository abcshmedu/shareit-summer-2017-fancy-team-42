package edu.hm.shareit.resources;



import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import edu.hm.shareit.Services.MediaService;
import edu.hm.shareit.models.Medium;
import org.junit.*;

import edu.hm.shareit.Services.MediaServiceResult;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;


/**
 * Tests for the MediaRessource-class.
 * @author Thomas Murschall
 *
 */
public class MediaResourceTest {
    private final Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(MediaService.class).toInstance(mock(MediaService.class));
        }
    });
    @Inject
    private MediaResource medRes;
    private final String CookieName = "Token";
    private Cookie cookie = new Cookie(CookieName,"token","","/");
    @Inject
    private MediaService serviceMock;

    /**
     * Setting up for the tests.
     */
    @Before
    public void setUp(){
        injector.injectMembers(this);
        when(serviceMock.getJWTCookie("token")).thenReturn("{\"sample-jwt\":\"user-id\"}");
        when(serviceMock.resetDatabse()).thenReturn(MediaServiceResult.SUCCESS);
    }
    
    /**
     * Clear resources.
     */
    @After
    public void clearUp() {
        medRes.delAll(cookie);
    }


    /**
     * Insert a valid book.
     * @throws Exception 
     */
    @Test
    public void  insertBook() throws Exception {
        Book b = new Book("Mustermann", "9783065210201", "Title");
        when(serviceMock.addBook(b)).thenReturn(MediaServiceResult.SUCCESS);
        Response res = medRes.createBook(cookie, b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        b = new Book("Mustermann", "0198526636", "Title");
        when(serviceMock.addBook(b)).thenReturn(MediaServiceResult.SUCCESS);
        res = medRes.createBook(cookie, b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
    }
    
    /**
     * Insert valid discs.
     * @throws Exception 
     */
    @Test
    public void insertDiscTest() throws Exception {
        final int fsk = 6;
        Disc d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        when(serviceMock.addDisc(d)).thenReturn(MediaServiceResult.SUCCESS);
        Response res = medRes.createDisc(cookie, d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());      
    }
    
    /**
     * Valid book update.
     * @throws Exception 
     */
    @Test
    public void updateBookTest() throws Exception {
        Book b = new Book("Mustermann", "9783065210201", "Title");
        when(serviceMock.addBook(b)).thenReturn(MediaServiceResult.SUCCESS);
        Response res = medRes.createBook(cookie, b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Musterfrau", "9783065210201", "Title");
        when(serviceMock.updateBook(b)).thenReturn(MediaServiceResult.SUCCESS);
        res = medRes.changeBook(cookie, "9783065210201", b);
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
        when(serviceMock.addDisc(d)).thenReturn(MediaServiceResult.SUCCESS);
        Response res = medRes.createDisc(cookie, d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("5449000096241", "Musterfrau", fsk, "Musterfilm");
        when(serviceMock.updateDisc(d)).thenReturn(MediaServiceResult.SUCCESS);
        res = medRes.changeDisc(cookie, "5449000096241", d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
    }
    
    /**
     * Duplicate insert of book.
     * @throws Exception 
     */
    @Test
    public void duplicateBookTest() throws Exception {
        Book b = new Book("Mustermann", "9783065210201", "Title");
        when(serviceMock.addBook(b)).thenReturn(MediaServiceResult.SUCCESS);
        Response res = medRes.createBook(cookie, b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Mustermann", "9783065210201", "Title");
        when(serviceMock.addBook(b)).thenReturn(MediaServiceResult.DUPLICATE);
        res = medRes.createBook(cookie, b);
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
        when(serviceMock.addDisc(d)).thenReturn(MediaServiceResult.SUCCESS);
        Response res = medRes.createDisc(cookie, d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        when(serviceMock.addDisc(d)).thenReturn(MediaServiceResult.DUPLICATE);
        res = medRes.createDisc(cookie, d);
        assertTrue(MediaServiceResult.DUPLICATE.getStatus() == res.getEntity());
    }
    
    /**
     * Invalid book update.
     * @throws Exception 
     */
    @Test
    public void invalidUpdateBookTest() throws Exception {
        Book b = new Book("Mustermann", "9783065210201", "Title");
        when(serviceMock.addBook(b)).thenReturn(MediaServiceResult.SUCCESS);
        Response res = medRes.createBook(cookie, b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Musterfrau", "9783065210201", "Title");
        res = medRes.changeBook(cookie, "9783127323207", b);
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
        when(serviceMock.addDisc(d)).thenReturn(MediaServiceResult.SUCCESS);
        Response res = medRes.createDisc(cookie, d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("5449000096241", "Musterfrau", fsk, "Musterfilm");
        res = medRes.changeDisc(cookie, "5030917105081", d);
        assertTrue(MediaServiceResult.BADREQUEST.getStatus() == res.getEntity());
    }
    
    /**
     *  Test to find a book with the isbn.
     * @throws Exception 
     */
    @Test
    public void getBookIsbnTest() throws Exception {
        String isbn = "9783127323207";
        Book b = new Book("Ian Flemming", isbn, "Casino Royal");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(b);
        when(serviceMock.addBook(b)).thenReturn(MediaServiceResult.SUCCESS);
        Response res = medRes.createBook(cookie, b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        Medium[] books = new Medium[1];
        books[0] = b;
        when(serviceMock.getBooks()).thenReturn(books);
        res = medRes.getISBNBook(cookie, isbn);
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
        when(serviceMock.addDisc(d)).thenReturn(MediaServiceResult.SUCCESS);
        Response res = medRes.createDisc(cookie, d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        Medium[] discs = new Medium[1];
        discs[0] = d;
        when(serviceMock.getDiscs()).thenReturn(discs);
        res = medRes.getBarDisc(cookie, "5449000096241");
        assertTrue(json.equals(res.getEntity()));
    }
    
    /**
     * Check if the books were returned correctly.
     * @throws Exception 
     */
    @Test
    public void getBooksTest() throws Exception {
        String start = "[]";
        when(serviceMock.getBooks()).thenReturn(new Medium[0]);
        Response res = medRes.getBooks(cookie);
        assertTrue(res.getEntity().equals(start));
        Book b1 = new Book("Ian Flemming", "9783127323207", "Casino Royal");
        Book b2 = new Book("Ian Flemming", "9783065210201", "Goldeneye");
        when(serviceMock.addBook(b1)).thenReturn(MediaServiceResult.SUCCESS);
        when(serviceMock.addBook(b2)).thenReturn(MediaServiceResult.SUCCESS);
        medRes.createBook(cookie, b1);
        medRes.createBook(cookie, b2);
        Medium[] books = new Medium[2];
        books[0] = b1;
        books[1] = b2;
        ObjectMapper mapper = new ObjectMapper();
        String json = "[" + mapper.writeValueAsString(b1) + "," + mapper.writeValueAsString(b2) + "]";
        when(serviceMock.getBooks()).thenReturn(books);
        res = medRes.getBooks(cookie);
        assertTrue(res.getEntity().equals(json));
    }

    /**
     * Check if the discs were returned correctly.
     * @throws Exception 
     */
    @Test
    public void getDiscsTest() throws Exception {
        String start = "[]";
        final int fsk = 12;
        when(serviceMock.getDiscs()).thenReturn(new Medium[0]);
        Response res = medRes.getDiscs(cookie);
        assertTrue(res.getEntity().equals(start));
        Disc d1 = new Disc("5449000096241", "Ian Flemming", fsk, "Casino Royal");
        Disc d2 = new Disc("5030917105081", "Ian Flemming", fsk, "Goldeneye");
        when(serviceMock.addDisc(d1)).thenReturn(MediaServiceResult.SUCCESS);
        when(serviceMock.addDisc(d2)).thenReturn(MediaServiceResult.SUCCESS);
        medRes.createDisc(cookie, d1);
        medRes.createDisc(cookie, d2);
        Medium[] discs = new Medium[2];
        discs[0] = d1;
        discs[1] = d2;
        ObjectMapper mapper = new ObjectMapper();
        String json = "[" + mapper.writeValueAsString(d1) + "," + mapper.writeValueAsString(d2) + "]";
        when(serviceMock.getDiscs()).thenReturn(discs);
        res = medRes.getDiscs(cookie);
        assertTrue(res.getEntity().equals(json));
    }

    /**
     * Test for delete method.
     * @throws Exception 
     */
    @Test
    public void delAllTest() throws Exception {
        final int fsk = 12;
        Disc d = new Disc("5449000096241", "Steven Spielberg", fsk, "Indiana Jones");
        Book b = new Book("Ian Flemming", "9783065210201", "Casino Royal");
        when(serviceMock.addDisc(d)).thenReturn(MediaServiceResult.SUCCESS);
        when(serviceMock.addBook(b)).thenReturn(MediaServiceResult.SUCCESS);
        medRes.createBook(cookie, b);
        medRes.createDisc(cookie, d);
        when(serviceMock.resetDatabse()).thenReturn(MediaServiceResult.SUCCESS);
        Response res = medRes.delAll(cookie);
        Response res1 = medRes.createBook(cookie, b);
        Response res2 = medRes.createDisc(cookie, d);

        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity() && MediaServiceResult.SUCCESS.getStatus() == res1.getEntity() && MediaServiceResult.SUCCESS.getStatus() == res2.getEntity());
    }
}
