package edu.hm.shareit.services;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import edu.hm.shareit.Services.MediaService;
import edu.hm.shareit.Services.MediaServiceImpl;
import edu.hm.shareit.Services.MediaServiceResult;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;
import edu.hm.shareit.persistence.DatabaseManager;
import org.junit.Before;
import org.junit.Test;


import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by markus on 12.06.17.
 */
public class MediaServiceTest {
    private final Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(MediaService.class).to(MediaServiceImpl.class);
            bind(DatabaseManager.class).toInstance(mock(DatabaseManager.class));
        }
    });

    @Inject
    private MediaService medServ;
    @Inject
    private DatabaseManager dbMock;

    /**
     * 
     */
    @Before
    public void setUp() {
        injector.injectMembers(this);
    }

    /**
     * 
     * @throws Exception 
     */
    @Test
    public void getBooksTest() throws Exception {
        Book b = new Book("Mustermann", "9783065210201", "Title");
        //insert hat void, dehsalb muss nichts getan werden für den mock beim Einfügen.
        MediaServiceResult res =  medServ.addBook(b);
        assertTrue(res.getStatus().equals(MediaServiceResult.SUCCESS.getStatus()));
        //getBook Ausgabe des Mock-Objekts vorgeben.
        List<Book> books = new LinkedList<>();
        books.add(b);
        when(dbMock.getAllBooks()).thenReturn(books);
        Medium[] book = medServ.getBooks();
        assertTrue(book[0].equals(b));
    }
    
    /**
     * Invalid insertion test for books.
     */
    @Test
    public void addBookInvalidTest() {
        Book b = new Book("", "9783065210201", "Title");
        //insert hat void, dehsalb muss nichts getan werden für den mock beim Einfügen.
        MediaServiceResult res =  medServ.addBook(b);
        assertTrue(res.getStatus().equals(MediaServiceResult.BADINFORMATION.getStatus()));
        
        b = new Book("Mustermann", "978306521", "Title");
        //insert hat void, dehsalb muss nichts getan werden für den mock beim Einfügen.
        res =  medServ.addBook(b);
        assertTrue(res.getStatus().equals(MediaServiceResult.BADCODE.getStatus()));
    }

    /**
     * 
     * @throws Exception 
     */
    @Test
    public void  getDiscsTest() throws Exception {
        final int fsk = 6;
        Disc d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        MediaServiceResult res = medServ.addDisc(d);
        assertTrue(res.getStatus().equals(MediaServiceResult.SUCCESS.getStatus()));
        List<Disc> discs = new LinkedList<>();
        discs.add(d);
        when(dbMock.getAllDiscs()).thenReturn(discs);
        Medium[] disc = medServ.getDiscs();
        assertTrue(disc[0].equals(d));
    }
    
    /**
     * INvalid insertion test for discs.
     */
    @Test
    public void addDiscInvalidTest() {
        final int fsk = 6;
        Disc d = new Disc("54490000", "Mustermann", fsk, "Musterfilm");
        MediaServiceResult res = medServ.addDisc(d);
        assertTrue(res.getStatus().equals(MediaServiceResult.BADCODE.getStatus()));
        
        d = new Disc("5449000096241", "", fsk, "Musterfilm");
        res = medServ.addDisc(d);
        assertTrue(res.getStatus().equals(MediaServiceResult.BADINFORMATION.getStatus()));
    }
    
    /**
     * 
     */
    @Test
    public void updateBookTest() {
        Book b = new Book("Mustermann", "3866801920", "Title");
        assertEquals(medServ.addBook(b), MediaServiceResult.SUCCESS);
        
        b = new Book("Musterfrau", "3866801920", "Title2");
        assertEquals(medServ.updateBook(b), MediaServiceResult.SUCCESS);
        
        List<Book> books = new LinkedList<>();
        books.add(b);
        when(dbMock.getAllBooks()).thenReturn(books);
        Medium[] book = medServ.getBooks();
        assertTrue(book[0].equals(b));
    }
    
    /**
     * Invalid update Test for books.
     */
    @Test
    public void updateBookInvalidTest() {
        Book b = new Book("Mustermann", "3866801920", "Title");
        assertEquals(medServ.addBook(b), MediaServiceResult.SUCCESS);
        
        b = new Book("", "3866801920", "Title2");
        assertEquals(medServ.updateBook(b), MediaServiceResult.BADINFORMATION);
        
        b = new Book("Musterfrau", "3861920", "Title2");
        assertEquals(medServ.updateBook(b), MediaServiceResult.BADCODE);
    }

    /**
     * 
     */
    @Test
    public void updateDiscTest() {
        final int fsk = 6;
        Disc d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        assertEquals(medServ.addDisc(d), MediaServiceResult.SUCCESS);
        
        d = new Disc("5449000096241", "Musterfrau", fsk, "Musterfilm2");
        assertEquals(medServ.updateDisc(d), MediaServiceResult.SUCCESS);
        
        List<Disc> discs = new LinkedList<>();
        discs.add(d);
        when(dbMock.getAllDiscs()).thenReturn(discs);
        Medium[] disc = medServ.getDiscs();
        assertTrue(disc[0].equals(d));
    }
    
    /**
     * Invalid update test for discs.
     */
    @Test
    public void updateDiscInvalidTest() {
        final int fsk = 6;
        Disc d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        assertEquals(medServ.addDisc(d), MediaServiceResult.SUCCESS);
        
        d = new Disc("5449000096241", "", fsk, "Musterfilm2");
        assertEquals(medServ.updateDisc(d), MediaServiceResult.BADINFORMATION);
        
        d = new Disc("544096241", "Musterfrau", fsk, "Musterfilm2");
        assertEquals(medServ.updateDisc(d), MediaServiceResult.BADCODE);
    }
    
    /**
     * 
     */
    @Test
    public void resetDatabaseTest() {
        final int fsk = 6;
        Disc d = new Disc("5449000096241", "Mustermann", fsk, "Musterfilm");
        assertEquals(medServ.addDisc(d), MediaServiceResult.SUCCESS);
        Book b = new Book("Mustermann", "9783065210201", "Title");
        assertEquals(medServ.addBook(b), MediaServiceResult.SUCCESS);
        assertEquals(medServ.resetDatabase(), MediaServiceResult.SUCCESS);
       
    }

}
