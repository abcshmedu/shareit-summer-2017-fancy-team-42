package edu.hm.shareit.Services;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;
import org.json.JSONObject;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by markus on 21.04.17.
 */
public class MediaServiceImpl implements MediaService {

    private static List<Book> bookList = new LinkedList<>();
    private static List<Disc> disclist = new LinkedList<>();

    private static final String authServiceLink = "http://localhost:8083/authenticate/auth/valid";
    
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
     * Help-method only provided for test-cases. Do not use the method in the program.
     * @return Status whether list clearing was successful
     */
    public MediaServiceResult deleteLists() {
        disclist.clear();
        bookList.clear();
        return MediaServiceResult.SUCCESS;
    }

    /**
     * Private method, that checks if the isbn is correct.
     * @param isbn String
     * @return boolean, result of ISBN check
     */
    private boolean checkISBN(String isbn) {
        final int length1 = 10;
        final int length2 = 13;
        final int xNumber = 10;
        final int modNumber = 10;
        final int multStart = 3;
        if (isbn.length() == length1) {
            int sum = 0;
            for (int i = 1; i < isbn.length(); i++) {
                sum += Character.getNumericValue(isbn.charAt(i - 1)) * i;
            }
            final int mod = 11;
            int check = sum % mod;
            char lastNumber = isbn.charAt(isbn.length() - 1);

            if (lastNumber == 'X') {
                return check == xNumber;
            }
            else {
                return check == Character.getNumericValue(lastNumber);
            }

        }
        else if (isbn.length() == length2) {
            int sum = 0;
            int multiplier = multStart;

            for (int i = isbn.length() - 2; i > -1; i--) {
                sum += Character.getNumericValue(isbn.charAt(i)) * multiplier;
                if (multiplier == multStart) {
                    multiplier = 1;
                }
                else {
                    multiplier = multStart;
                }
            }
            int check = modNumber - (sum % modNumber);
            int lastNumber = Character.getNumericValue(isbn.charAt(isbn.length() - 1));
            return check == lastNumber;
        }
        else {
            return false;
        }
    }

    /**
     * Private method that checks if the code is correct.
     * @param code String
     * @return boolean, result of Barcode check
     */
    private boolean checkBarcode(String code) {
        final int length1 = 8;
        final int length2 = 13;
        final int multStart = 3;
        final int modNumb = 10;

        if (code.length() != length1 && code.length() != length2) {
            return false;
        }

        int sum = 0;
        int multiplier = multStart;
        for (int i = code.length() - 2; i > -1; i--) {
            sum += (Character.getNumericValue(code.charAt(i))) * multiplier;
            if (multiplier == multStart) {
                multiplier = 1;
            }
            else {
                multiplier = multStart;
            }
        }
        int check = modNumb - (sum % modNumb);
        int lastNumber = Character.getNumericValue(code.charAt(code.length() - 1));
        return check == lastNumber;
    }

    public String getJWTCookie(String token) {
        System.out.println(token);
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(UriBuilder.fromUri(authServiceLink).build());
        JSONObject body = new JSONObject();
        body.put("token", token);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, body.toString());
        String result = response.getEntity(String.class);



        return result;
    }
}
