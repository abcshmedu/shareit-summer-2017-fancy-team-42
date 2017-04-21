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

    public MediaServiceImpl(){
        bookList = new LinkedList<>();
        disclist = new LinkedList<>();
    }

    @Override
    public MediaServiceResult addBook(Book b) {
        return MediaServiceResult.Succes;
    }

    @Override
    public MediaServiceResult addDisc(Disc d) {
        return MediaServiceResult.Succes;
    }

    @Override
    public MediaServiceResult updateBook(Book b) {
        return MediaServiceResult.Succes;
    }

    @Override
    public MediaServiceResult updateDisc(Disc d) {
        return MediaServiceResult.Succes;
    }

    @Override
    public Medium[] getBooks() {
        return this.bookList.toArray(new Medium[0]);
    }

    @Override
    public Medium[] getDiscs() {
        return this.disclist.toArray(new Medium[0]);
    }
}
