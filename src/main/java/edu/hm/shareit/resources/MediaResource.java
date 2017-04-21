package edu.hm.shareit.resources;



import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Markus Krahl on 21.04.17.
 */
@Path("/media")
public class MediaResource {

    @GET
    @Path("/books")
    @Produces("application/json")
    public Response getBooks(){
        JSONObject jsObj = new JSONObject();
        jsObj.put("Die magische Zahl ist",42);
        return Response
                .status(Response.Status.OK)
                .entity(jsObj.toString())
                .build();
    }

    @GET
    @Path("/books/{isbn}")
    @Produces("application/json")
    public Response getISBNBook(@PathParam("isbn")String isbn){
        JSONObject jsObj = new JSONObject();
        jsObj.put("Die angeforderte ISBN",isbn);
        return Response
                .status(Response.Status.OK)
                .entity(jsObj.toString())
                .build();
    }

    @GET
    @Path("/discs")
    @Produces("application/json")
    public Response getDiscs(){
        JSONObject jsObj = new JSONObject();
        jsObj.put("Das sind die ganzen DVDs",42);
        return Response
                .status(Response.Status.OK)
                .entity(jsObj.toString())
                .build();
    }

    @GET
    @Path("/discs/{barcode}")
    @Produces("application/json")
    public Response getBarDisc(@PathParam("barcode")String code){
        JSONObject jsObj = new JSONObject();
        jsObj.put("Die angeforderte Disc mit Barcode:",code);
        return Response
                .status(Response.Status.OK)
                .entity(jsObj.toString())
                .build();
    }

    @POST
    @Path("/books")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createBook(){
        JSONObject jsObj = new JSONObject();
        jsObj.put("Buch erstellt:",42);
        return Response
                .status(Response.Status.OK)
                .entity(jsObj.toString())
                .build();
    }

    @POST
    @Path("/discs")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createDisc(){
        JSONObject jsObj = new JSONObject();
        jsObj.put("Disc erstellt:",42);
        return Response
                .status(Response.Status.OK)
                .entity(jsObj.toString())
                .build();
    }

    @PUT
    @Path("/discs/{barcode}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeDisc(@PathParam("barcode")String code){
        JSONObject jsObj = new JSONObject();
        jsObj.put("Disc verändert:", code);
        return Response
                .status(Response.Status.OK)
                .entity(jsObj.toString())
                .build();
    }

    @PUT
    @Path("/books/{isbn}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response changeBook(@PathParam("isbn")String isbn){
        JSONObject jsObj = new JSONObject();
        jsObj.put("Buch verändert:", isbn);
        return Response
                .status(Response.Status.OK)
                .entity(jsObj.toString())
                .build();
    }

}
