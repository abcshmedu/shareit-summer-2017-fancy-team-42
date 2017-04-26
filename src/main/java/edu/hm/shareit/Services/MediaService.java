package edu.hm.shareit.Services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;

/**
 * @author Markus Krahl
 */
public interface MediaService {

    /**
     * 
     * @param b Book
     * @return MediaServiceResult
     */
    MediaServiceResult addBook(Book b);
    /**
     * 
     * @param d Disc
     * @return MediaServiceResult
     */
    MediaServiceResult addDisc(Disc d);
    /**
     * 
     * @param b Book
     * @return MediaServiceResult
     */
    MediaServiceResult updateBook(Book b);
    /**
     * 
     * @param d Disc
     * @return MediaServiceResult
     */
    MediaServiceResult updateDisc(Disc d);
    /**
     * 
     * @return Medium[]
     */
    Medium[] getBooks();
    /**
     * 
     * @return Medium[]
     */
    Medium[] getDiscs();

    /**
     *
     * @return MediaServiceResult
     */
    MediaServiceResult deleteLists();


}
