package edu.hm.shareit.Services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by markus on 21.04.17.
 */
public class MediaServiceImpl implements MediaService {

    private static List<Book> bookList = new LinkedList<>();
    private static List<Disc> disclist = new LinkedList<>();
    
    /**
     * Constructor.
     */
    public MediaServiceImpl() {

        /*
        bookList.add(new Book("Peter Lustig", "1111", "Das Leben im Bauwagen"));
        bookList.add(new Book("Hans Müller","1112","Wie man als Müller lebt"));
        bookList.add(new Book("Astrid Lindgren", "1113", "Kalle Blomquist"));

        disclist.add(new Disc("0001","Steven Spielberg",12,"Indiana Jones"));
        disclist.add(new Disc("0002","George Lucas",6,"Star Wars Episode 4"));
        disclist.add(new Disc("007","secret",12,"Tomorrow never dies"));
        */
    }

    @Override
    public MediaServiceResult addBook(Book b) {
        MediaServiceResult res = MediaServiceResult.SUCCESS;
        if (bookList.contains(b)) {
            //TODO change status 
            res = MediaServiceResult.Duplicate;
            // Error code, already in List
            // Book not inserted,
        } else {
            bookList.add(b);
            res = MediaServiceResult.SUCCESS;
        }       
        return res;
    }

    @Override
    public MediaServiceResult addDisc(Disc d) {
        MediaServiceResult res = MediaServiceResult.SUCCESS;
        if (disclist.contains(d)) {
            //TODO change status 
            res = MediaServiceResult.Duplicate;
            // Error code, already in List
            // Book not inserted,
        } else {
            disclist.add(d);
            res = MediaServiceResult.SUCCESS;
        }

        return res;
    }

    @Override
    public MediaServiceResult updateBook(Book b) {
        MediaServiceResult res = MediaServiceResult.SUCCESS;
        int index = 0;
        for (int i = 0; i <= bookList.size() && !bookList.get(i).equals(b); i++) {
            index = i;
        }
        try {
            bookList.remove(index);
            bookList.add(b);
            res = MediaServiceResult.SUCCESS;
        } catch (ArrayIndexOutOfBoundsException e) {
            //could not update the book b, not in List
            //TODO change error code
            res = MediaServiceResult.SUCCESS;
        }      
        return res;
    }

    @Override
    public MediaServiceResult updateDisc(Disc d) {
        MediaServiceResult res = MediaServiceResult.SUCCESS;
        int index = 0;
        for (int i = 0; i <= bookList.size() && !disclist.get(i).equals(d); i++) {
            index = i;
        }
        try {
            disclist.remove(index);
            disclist.add(d);
            res = MediaServiceResult.SUCCESS;
        } catch (ArrayIndexOutOfBoundsException e) {
            //could not update the book b, not in List
            //TODO change error code
            res = MediaServiceResult.SUCCESS;
        }      
        return res;
    }

    @Override
    public Medium[] getBooks() {
        return this.bookList.toArray(new Medium[bookList.size()]);
    }

    @Override
    public Medium[] getDiscs() {
        return this.disclist.toArray(new Medium[disclist.size()]);
    }
}
