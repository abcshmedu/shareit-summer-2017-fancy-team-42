package edu.hm.shareit.Services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;

/**
 * Created by markus on 21.04.17.
 */
public interface MediaService {


    MediaServiceResult addBook(Book b);
    MediaServiceResult addDisc(Disc d);
    MediaServiceResult updateBook(Book b);
    MediaServiceResult updateDisc(Disc d);

    Medium[] getBooks();
    Medium[] getDiscs();


}
