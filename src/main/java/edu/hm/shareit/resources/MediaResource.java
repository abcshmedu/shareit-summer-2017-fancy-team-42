package edu.hm.shareit.resources;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import edu.hm.shareit.Services.MediaService;
import edu.hm.shareit.Services.MediaServiceResult;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Markus Krahl on 21.04.17.
 * That class is responsible for all requests.
 * Important is the URI-pattern-matching.
 * Each annotation can be related to a part of an URI-request
 */
@Path("/media")
public class MediaResource {

    private final MediaService ms;
    private static final String CookieName = "Token";
    private final String LogInPage = "https://a4-auth-server-fancy-team-42.herokuapp.com/authenticate/auth/user";

    /**
     * Constructor of class.
     * 
     * @param ms 
     */
    @Inject
    public MediaResource(MediaService ms) {
        this.ms = ms;
    }


    /**
     * Returns all saved books.
     * @param token Cookie, token is saved in cookie
     * @return Response with status
     */
    @GET
    @Path("/books")
    @Produces("application/json")
    public Response getBooks(@CookieParam(CookieName) Cookie token) {
        String jwt = "";
        if (token != null) {
            jwt = ms.getJWTCookie(token.getValue());
        }
        if (jwt.equals("")) {
            return Response
                    .status(MediaServiceResult.NOTAUTHORIZED.getCode())
                    .entity(MediaServiceResult.NOTAUTHORIZED.getStatus())
                    .build();
        }

        Medium[] books = ms.getBooks();
        String json = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            json = mapper.writeValueAsString(books);
        } catch (JsonProcessingException e) {
            json = MediaServiceResult.ERROR.getStatus();
        }

        return Response
                .status(Response.Status.OK)
                .entity(json)
                .build();
    }

    /**
     * Search for a book.
     * @return Response with status
     * @param isbn String, isbn to search for
     * @param token 
     */
    @GET
    @Path("/books/{isbn}")
    @Produces("application/json")
    public Response getISBNBook(@CookieParam(CookieName) Cookie token, @PathParam("isbn")String isbn) {
        String jwt = "";
        if (token != null) {
            jwt = ms.getJWTCookie(token.getValue());
        }
        if (jwt.equals("")) {
            return Response
                    .status(MediaServiceResult.NOTAUTHORIZED.getCode())
                    .entity(MediaServiceResult.NOTAUTHORIZED.getStatus())
                    .build();
        }

        Medium[] books = ms.getBooks();
        Book res = null;
        for (int i = 0; i < books.length; i++) {
            Book b = (Book) books[i];
            if (b.getIsbn().equals(isbn)) {
                i = books.length;
                res = b;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = "";

        try {
            if (res != null) {
                json = mapper.writeValueAsString(res);
            }
            else {
                json = MediaServiceResult.NOTFOUND.getStatus();
            }
        } catch (JsonProcessingException e) {
            json = MediaServiceResult.ERROR.getStatus();
        }


        return Response
                .status(Response.Status.OK)
                .entity(json)
                .build();
    }

    /**
     * Returns all saved Discs.
     * @param token 
     * @return Response with status
     */
    @GET
    @Path("/discs")
    @Produces("application/json")
    public Response getDiscs(@CookieParam(CookieName) Cookie token) {
        String jwt = "";
        if (token != null) {
            jwt = ms.getJWTCookie(token.getValue());
        }
        if (jwt.equals("")) {
            return Response
                    .status(MediaServiceResult.NOTAUTHORIZED.getCode())
                    .entity(MediaServiceResult.NOTAUTHORIZED.getStatus())
                    .build();
        }

        Medium[] discs = ms.getDiscs();
        String json = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            json = mapper.writeValueAsString(discs);
        } catch (JsonProcessingException e) {
            json = MediaServiceResult.ERROR.getStatus();
        }

        return Response
                .status(Response.Status.OK)
                .entity(json)
                .build();
    }

    /**
     * Search for a Disc.
     * @return Response with status
     * @param code String, code to search for
     * @param token 
     */
    @GET
    @Path("/discs/{barcode}")
    @Produces("application/json")
    public Response getBarDisc(@CookieParam(CookieName) Cookie token, @PathParam("barcode")String code) {
        String jwt = "";
        if (token != null) {
            jwt = ms.getJWTCookie(token.getValue());
        }
        if (jwt.equals("")) {
            return Response
                    .status(MediaServiceResult.NOTAUTHORIZED.getCode())
                    .entity(MediaServiceResult.NOTAUTHORIZED.getStatus())
                    .build();
        }

        Medium[] discs = ms.getDiscs();
        Disc res = null;
        for (int i = 0; i < discs.length; i++) {
            Disc d = (Disc) discs[i];
            if (d.getBarcode().equals(code)) {
                i = discs.length;
                res = d;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = "";

        try {
            if (res != null) {
                json = mapper.writeValueAsString(res);
            }
            else {
                json = MediaServiceResult.NOTFOUND.getStatus();
            }
        } catch (JsonProcessingException e) {
            json = MediaServiceResult.ERROR.getStatus();
        }
        return Response
                .status(Response.Status.OK)
                .entity(json)
                .build();
    }

    /**
     * Insert a new book to the list.
     * @param b the new book
     * @param token 
     * @return Response with status
     */
    @POST
    @Path("/books")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createBook(@CookieParam(CookieName) Cookie token, Book b) {
        String jwt = "";
        if (token != null) {
            jwt = ms.getJWTCookie(token.getValue());
        }
        if (jwt.equals("")) {
            return Response
                    .status(MediaServiceResult.NOTAUTHORIZED.getCode())
                    .entity(MediaServiceResult.NOTAUTHORIZED.getStatus())
                    .build();
        }

        MediaServiceResult res = ms.addBook(b);
        return Response
                .status(res.getCode())
                .entity(res.getStatus())
                .build();
    }
    
    /**
     * Insert a new disc to the list.
     * @param d the new disc
     * @param token 
     * @return Response with status
     */
    @POST
    @Path("/discs")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createDisc(@CookieParam(CookieName) Cookie token, Disc d) {
        String jwt = "";
        if (token != null) {
            jwt = ms.getJWTCookie(token.getValue());
        }
        if (jwt.equals("")) {
            return Response
                    .status(MediaServiceResult.NOTAUTHORIZED.getCode())
                    .entity(MediaServiceResult.NOTAUTHORIZED.getStatus())
                    .build();
        }

        MediaServiceResult res = ms.addDisc(d);

        return Response
                .status(res.getCode())
                .entity(res.getStatus())
                .build();
    }

    /**
     * Change a disc.
     * @param code The code of the disc we want to change
     * @param d the changed disc
     * @param token 
     * @return Response with status
     */
    @PUT
    @Path("/discs/{barcode}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeDisc(@CookieParam(CookieName) Cookie token, @PathParam("barcode")String code, Disc d) {
        String jwt = "";
        if (token != null) {
            jwt = ms.getJWTCookie(token.getValue());
        }
        if (jwt.equals("")) {
            return Response
                    .status(MediaServiceResult.NOTAUTHORIZED.getCode())
                    .entity(MediaServiceResult.NOTAUTHORIZED.getStatus())
                    .build();
        }

        MediaServiceResult res;
        if (code.equals(d.getBarcode())) {
            res = ms.updateDisc(d);
        }
        else {
            res = MediaServiceResult.BADREQUEST;
        }
        return Response
                .status(res.getCode())
                .entity(res.getStatus())
                .build();
    }

    /**
     * Change a book.
     * @param isbn The isbn of the book we want to change
     * @param b the changed book
     * @param token 
     * @return Response with status
     */
    @PUT
    @Path("/books/{isbn}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeBook(@CookieParam(CookieName) Cookie token, @PathParam("isbn")String isbn, Book b) {
        String jwt = "";
        if (token != null) {
            jwt = ms.getJWTCookie(token.getValue());
        }
        if (jwt.equals("")) {
            return Response
                    .status(MediaServiceResult.NOTAUTHORIZED.getCode())
                    .entity(MediaServiceResult.NOTAUTHORIZED.getStatus())
                    .build();
        }

        MediaServiceResult res;
        if (isbn.equals(b.getIsbn())) {
            res = ms.updateBook(b);
        }
        else {
            res = MediaServiceResult.BADREQUEST;
        }
        return Response
                .status(res.getCode())
                .entity(res.getStatus())
                .build();
    }

    /**
     * Deletes all lists.
     * @param token 
     * @return Response with status.
     */
    @GET
    @Path("/remove")
    @Produces("application/json")
    public Response delAll(@CookieParam(CookieName) Cookie token) {
        String jwt = "";
        if (token != null) {
            jwt = ms.getJWTCookie(token.getValue());
        }
        if (jwt.equals("")) {
            return Response
                    .status(MediaServiceResult.NOTAUTHORIZED.getCode())
                    .entity(MediaServiceResult.NOTAUTHORIZED.getStatus())
                    .build();
        }

        MediaServiceResult res = ms.resetDatabase();
        return Response
                .status(res.getCode())
                .entity(res.getStatus())
                .build();
    }

    /**
     * Help-method only for heroku to share the cookie between the apps.
     * @param user String with user information
     * @return Response with cookie and status.
     */
    @GET
    @Path("/auth/{user}")
    @Produces("application/json")
    public Response getCookie(@PathParam("user") String user) {
        String name = "";
        String password = "";
        try {
            name = user.split("&")[0];
            password = user.split("&")[1];
        }
        catch (Exception e) {
            return Response
                    .status(MediaServiceResult.BADREQUEST.getCode())
                    .entity(MediaServiceResult.BADREQUEST.getStatus())
                    .build();
        }

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(UriBuilder.fromUri(LogInPage).build());
        JSONObject body = new JSONObject();
        body.put("name", name);
        body.put("password", password);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, body.toString());
        List<NewCookie> cookies = response.getCookies();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse("1900-01-01");
        } catch (ParseException e) {
            return Response
                    .status(MediaServiceResult.BADREQUEST.getCode())
                    .entity(MediaServiceResult.BADREQUEST.getStatus())
                    .build();
        }

        NewCookie ncookie = null;

        for (NewCookie c:cookies) {
                if (c.getExpiry().compareTo(date) > 0 && c.getName().equals(CookieName)) {
                    date = c.getExpiry();
                    ncookie = c;
            }
        }

        if (ncookie != null) {
            return Response
                    .status(MediaServiceResult.SUCCESS.getCode())
                    .entity(MediaServiceResult.SUCCESS.getStatus())
                    .cookie(ncookie)
                    .build();
        }

        return Response
                .status(MediaServiceResult.BADREQUEST.getCode())
                .entity(MediaServiceResult.BADREQUEST.getStatus())
                .build();

    }

}
