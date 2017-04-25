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

    }

    @Override
    public MediaServiceResult addBook(Book b) {
        MediaServiceResult res;
        if (b.getAuthor().equals("") || b.getTitle().equals("")) {
            return MediaServiceResult.BADINFORMATION;
        }
        if (!this.checkISBN(b.getIsbn())) {
            return MediaServiceResult.BADCODE;
        }
        if (bookList.contains(b)) {
            res = MediaServiceResult.DUPLICATE;
        } else {
            bookList.add(b);
            res = MediaServiceResult.SUCCESS;
        }       
        return res;
    }

    @Override
    public MediaServiceResult addDisc(Disc d) {
        MediaServiceResult res;
        if (d.getTitle().equals("") || d.getDirector().equals("")) {
            return MediaServiceResult.BADINFORMATION;
        }
        if (!this.checkBarcode(d.getBarcode())) {
            return MediaServiceResult.BADCODE;
        }
        if (disclist.contains(d)) {
            res = MediaServiceResult.DUPLICATE;
        } else {
            disclist.add(d);
            res = MediaServiceResult.SUCCESS;
        }

        return res;
    }

    @Override
    public MediaServiceResult updateBook(Book b) {
        MediaServiceResult res;
        if (b.getAuthor().equals("") || b.getTitle().equals("")) {
            return MediaServiceResult.BADINFORMATION;
        }
        if (!this.checkISBN(b.getIsbn())) {
            return MediaServiceResult.BADCODE;
        }
        int index = -1;
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).equals(b)) {
                index = i;
                i = bookList.size();
            }
        }
        if (index > -1) {
            bookList.remove(index);
            bookList.add(b);
            res = MediaServiceResult.SUCCESS;
        }
        else {
            res = MediaServiceResult.NOTFOUND;
        }
        return res;
    }

    @Override
    public MediaServiceResult updateDisc(Disc d) {
        MediaServiceResult res;
        if (d.getTitle().equals("") || d.getDirector().equals("")) {
            return MediaServiceResult.BADINFORMATION;
        }
        if (!this.checkBarcode(d.getBarcode())) {
            return MediaServiceResult.BADCODE;
        }
        int index = -1;
        for (int i = 0; i < disclist.size(); i++) {
            if (disclist.get(i).equals(d)) {
                index = i;
                i = disclist.size();
            }
        }
        if (index > -1) {
            disclist.remove(index);
            disclist.add(d);
            res = MediaServiceResult.SUCCESS;
        }
        else {
            res = MediaServiceResult.NOTFOUND;
        }
        return res;
    }

    @Override
    public Medium[] getBooks() {
        return MediaServiceImpl.bookList.toArray(new Medium[bookList.size()]);
    }

    @Override
    public Medium[] getDiscs() {
        return MediaServiceImpl.disclist.toArray(new Medium[disclist.size()]);
    }

    /**
     * Private method, that checks if the isbn is correct.
     * @param isbn String
     * @return boolean, result of ISBN check
     */
    private boolean checkISBN(String isbn) {
        return true;
    }

    /**
     * Private method that checks if the code is correct.
     * @param code String
     * @return boolean, result of Barcode check
     */
    private boolean checkBarcode(String code) {
        return true;
    }
}
