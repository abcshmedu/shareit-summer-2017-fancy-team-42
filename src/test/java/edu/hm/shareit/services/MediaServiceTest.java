package edu.hm.shareit.services;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import edu.hm.shareit.Services.MediaService;
import edu.hm.shareit.Services.MediaServiceResult;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;
import edu.hm.shareit.persistence.DatabaseManager;
import edu.hm.shareit.resources.MediaResource;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import java.util.LinkedList;
import java.util.List;

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
            bind(DatabaseManager.class).toInstance(mock(DatabaseManager.class));
        }
    });

    @Inject
    private MediaService medServ;
    @Inject
    private DatabaseManager dbMock;

    @Before
    public void setUp() {
        injector.injectMembers(this);
    }

    @Test
    public void TestGetBooks () throws Exception {
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

    @Test
    public void  TestGetDiscs () throws Exception {
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


}
