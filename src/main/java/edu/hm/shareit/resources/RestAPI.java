package edu.hm.shareit.resources;



import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by markus on 21.04.17.
 */
@Path("/media")
public class RestAPI {

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

}
