package edu.hm.shareit.Services;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Created by markus on 21.04.17.
 */
public enum MediaServiceResult {
    Succes(200,Status.ACCEPTED);
    private int code;
    private Status state;

    MediaServiceResult (int code, Status state){
        this.code = code;
        this.state = state;
    }

    int getCode(){
        return code;
    }

    Status getStatus(){
        return state;
    }

    /*
    MediaServiceResult valueOf(String ans){
        switch (ans){
            case "Succes":
                return Succes;
            case "BadParameter":
                return BadParameter;
            default:
                return BadParameter;
        }
    }

    MediaServiceResult[] values(){
        return new MediaServiceResult[0];
    }
    */
}
