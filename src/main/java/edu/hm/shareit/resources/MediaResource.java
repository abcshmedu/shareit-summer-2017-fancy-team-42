package edu.hm.shareit.resources;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.shareit.Services.MediaService;
import edu.hm.shareit.Services.MediaServiceImpl;
import edu.hm.shareit.Services.MediaServiceResult;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Markus Krahl on 21.04.17.
 */
@Path("/media")
public class MediaResource {

    private MediaService ms;

    public MediaResource() {
        ms = new MediaServiceImpl();
    }


    @GET
    @Path("/books")
    @Produces("application/json")
    public Response getBooks() {
        Medium[] books = ms.getBooks();
        String json = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            json = mapper.writeValueAsString(books);
        } catch (JsonProcessingException e) {
            json = "{\"Message\":\"Error in showing list\"}";
        }

        return Response
                .status(Response.Status.OK)
                .entity(json)
                .build();
    }

    @GET
    @Path("/books/{isbn}")
    @Produces("application/json")
    public Response getISBNBook(@PathParam("isbn")String isbn) {

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
                json = "{\"Message\":\"Book with that ISBN does not exist.\"}";
            }
        } catch (JsonProcessingException e) {
            json = "{\"Message\":\"Error in showing list\"}";
        }


        return Response
                .status(Response.Status.OK)
                .entity(json)
                .build();
    }

    @GET
    @Path("/discs")
    @Produces("application/json")
    public Response getDiscs() {
        Medium[] discs = ms.getDiscs();
        String json = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            json = mapper.writeValueAsString(discs);
        } catch (JsonProcessingException e) {
            json = "{\"Message\":\"Error in showing list\"}";
        }

        return Response
                .status(Response.Status.OK)
                .entity(json)
                .build();
    }

    @GET
    @Path("/discs/{barcode}")
    @Produces("application/json")
    public Response getBarDisc(@PathParam("barcode")String code) {
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
                json = "{\"Message\":\"Disc with that barcode does not exist.\"}";
            }
        } catch (JsonProcessingException e) {
            json = "{\"Message\":\"Error in showing list\"}";
        }
        return Response
                .status(Response.Status.OK)
                .entity(json)
                .build();
    }

    @POST
    @Path("/books")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createBook(Book b) {
        MediaServiceResult res = ms.addBook(b);
        ObjectMapper mapper = new ObjectMapper();
        String json = "";

        try {
            json = mapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            json = "{\"Message\":\"Error in state result\"}";
        }

        return Response
                .status(res.getCode())
                .entity(res.getStatus())
                .build();
    }

    @POST
    @Path("/discs")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createDisc(Disc d) {
        MediaServiceResult res = ms.addDisc(d);
        ObjectMapper mapper = new ObjectMapper();
        String json = "";

        try {
            json = mapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            json = "{\"Message\":\"Error in state result\"}";
        }

        return Response
                .status(res.getCode())
                .entity(res.getStatus())
                .build();
    }

    @PUT
    @Path("/discs/{barcode}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeDisc(@PathParam("barcode")String code, Disc d) {
        MediaServiceResult res;
        if (code.equals(d.getBarcode())) {
            res = ms.updateDisc(d);
        }
        else {
            res = MediaServiceResult.Duplicate;
        }
        return Response
                .status(res.getCode())
                .entity(res.getStatus())
                .build();
    }

    @PUT
    @Path("/books/{isbn}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeBook(@PathParam("isbn")String isbn, Book b) {
        MediaServiceResult res;
        if (isbn.equals(b.getIsbn())) {
            res = ms.updateBook(b);
        }
        else {
            res = MediaServiceResult.Duplicate;
        }
        return Response
                .status(res.getCode())
                .entity(res.getStatus())
                .build();
    }

}
