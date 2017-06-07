package edu.hm.shareit.resources;


import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.hm.shareit.Services.MediaServiceResult;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Tests for the MediaRessource-class.
 * @author Thomas Murschall
 *
 */
public class MediaResourceTest {
    private static MediaResource medRes;
    private static Cookie cookie;
    private static final String CookieName = "Token";

    /**
     * Setting up for the tests.
     */
    @BeforeClass
    public static void setUp() throws ParseException {
        String authLink = "localhost:8083/authenticate/auth/user";

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(UriBuilder.fromUri(authLink).build());
        JSONObject body = new JSONObject();
        body.put("name", "peter");
        body.put("password", "12345");
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, body.toString());
        List<NewCookie> cookies = response.getCookies();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("1900-01-01");
        NewCookie ncookie = null;

        for(NewCookie c:cookies){
            if(c.getExpiry().compareTo(date) > 0 && c.getName().equals(CookieName)) {
                date = c.getExpiry();
                ncookie = c;
            }
        }

        if(ncookie != null){
            cookie = ncookie.toCookie();
        }


        medRes = new MediaResource();
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
    public void insertBookTest() throws Exception {
        Book b = new Book("Mustermann", "9783065210201", "Title");
        Response res = medRes.createBook(cookie, b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Mustermann", "0198526636", "Title");
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
        Response res = medRes.createBook(cookie, b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Musterfrau", "9783065210201", "Title");
        
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
        Response res = medRes.createDisc(cookie, d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("5449000096241", "Musterfrau", fsk, "Musterfilm");
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
        Response res = medRes.createBook(cookie, b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        b = new Book("Mustermann", "9783065210201", "Title");
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
        Response res = medRes.createDisc(cookie, d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
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
        Response res = medRes.createDisc(cookie, d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
        d = new Disc("5030917105081", "Musterfrau", fsk, "Musterfilm");
        res = medRes.changeDisc(cookie, "5030917105081", d);
        assertTrue(MediaServiceResult.NOTFOUND.getStatus() == res.getEntity());
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
        Response res = medRes.createBook(cookie, b);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());

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
        Response res = medRes.createDisc(cookie, d);
        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity());
        
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
        Response res = medRes.getBooks(cookie);
        assertTrue(res.getEntity().equals(start));
        Book b1 = new Book("Ian Flemming", "9783127323207", "Casino Royal");
        Book b2 = new Book("Ian Flemming", "9783065210201", "Goldeneye");
        medRes.createBook(cookie, b1);
        medRes.createBook(cookie, b2);
        ObjectMapper mapper = new ObjectMapper();
        String json = "[" + mapper.writeValueAsString(b1) + "," + mapper.writeValueAsString(b2) + "]";
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
        Response res = medRes.getDiscs(cookie);
        assertTrue(res.getEntity().equals(start));
        Disc d1 = new Disc("5449000096241", "Ian Flemming", fsk, "Casino Royal");
        Disc d2 = new Disc("5030917105081", "Ian Flemming", fsk, "Goldeneye");
        medRes.createDisc(cookie, d1);
        medRes.createDisc(cookie, d2);
        ObjectMapper mapper = new ObjectMapper();
        String json = "[" + mapper.writeValueAsString(d1) + "," + mapper.writeValueAsString(d2) + "]";
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

        medRes.createBook(cookie, b);
        medRes.createDisc(cookie, d);
        Response res = medRes.delAll(cookie);
        Response res1 = medRes.createBook(cookie, b);
        Response res2 = medRes.createDisc(cookie, d);

        assertTrue(MediaServiceResult.SUCCESS.getStatus() == res.getEntity() && MediaServiceResult.SUCCESS.getStatus() == res1.getEntity() && MediaServiceResult.SUCCESS.getStatus() == res2.getEntity());
    }
}
