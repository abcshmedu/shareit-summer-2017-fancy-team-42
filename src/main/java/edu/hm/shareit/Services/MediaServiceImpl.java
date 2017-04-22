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

    private List<Book> bookList;
    private List<Disc> disclist;
    
    /**
     * Constructor.
     */
    public MediaServiceImpl() {
        bookList = new LinkedList<>();
        disclist = new LinkedList<>();
    }

    @Override
    public MediaServiceResult addBook(Book b) {
        MediaServiceResult res = MediaServiceResult.Succes;
        if (bookList.contains(b)) {
            //TODO change status 
            res = MediaServiceResult.Succes;
            // Error code, already in List
            // Book not inserted,
        } else {
            bookList.add(b);
            res = MediaServiceResult.Succes;
        }       
        return res;
    }

    @Override
    public MediaServiceResult addDisc(Disc d) {
        MediaServiceResult res = MediaServiceResult.Succes;
        if (disclist.contains(d)) {
            //TODO change status 
            res = MediaServiceResult.Succes;
            // Error code, already in List
            // Book not inserted,
        } else {
            disclist.add(d);
            res = MediaServiceResult.Succes;
        }       
        return res;
    }

    @Override
    public MediaServiceResult updateBook(Book b) {
        MediaServiceResult res = MediaServiceResult.Succes;
        int index = -1;
        for (int i = 0; i <= bookList.size() && !bookList.get(i).equals(b); i++) {
            index = i;
        }
        try {
            bookList.remove(index);
            bookList.add(b);
            res = MediaServiceResult.Succes;
        } catch (ArrayIndexOutOfBoundsException e) {
            //could not update the book b, not in List
            //TODO change error code
            res = MediaServiceResult.Succes;
        }      
        return res;
    }

    @Override
    public MediaServiceResult updateDisc(Disc d) {
        MediaServiceResult res = MediaServiceResult.Succes;
        int index = -1;
        for (int i = 0; i <= bookList.size() && !disclist.get(i).equals(d); i++) {
            index = i;
        }
        try {
            disclist.remove(index);
            disclist.add(d);
            res = MediaServiceResult.Succes;
        } catch (ArrayIndexOutOfBoundsException e) {
            //could not update the book b, not in List
            //TODO change error code
            res = MediaServiceResult.Succes;
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
